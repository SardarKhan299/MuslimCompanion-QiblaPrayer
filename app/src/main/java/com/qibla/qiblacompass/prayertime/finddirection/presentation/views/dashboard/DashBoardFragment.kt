package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.convertTimeToMilliseconds
import com.qibla.qiblacompass.prayertime.finddirection.common.MyLocationManager
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.formatTimeTo12Hour
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashBoardBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection.CompassDirectionActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection.QibalDirectionFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(R.layout.fragment_dash_board) {

    private lateinit var rView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var locationTextView: TextView
    private lateinit var mLocationManager: MyLocationManager

    private lateinit var prayerTimeList: List<String>

    val c = Calendar.getInstance()

    val currentYear = c.get(Calendar.YEAR)
    val currentMonth = c.get(Calendar.MONTH)+1
    val currentDay = c.get(Calendar.DAY_OF_MONTH)

    private var index = 0
    private val viewModel: DashboardViewModel by viewModels()
    private var firstTime = 0

    lateinit var job:Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DashBoardFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            boardFragment = this@DashBoardFragment
        }
        sharedPreferences = SharedPreferences()
        locationTextView = binding.tvAutoDetect
        binding.toolbarBoard.groupToolbarProfile.visibility = View.GONE
        binding.toolbarBoard.groupToolbar.visibility = View.VISIBLE
        rView = binding.layoutBoardFragment.findViewById(R.id.rv_qibla)
        initObserver()
        binding.tvAds.setOnClickListener {
            //Navigation.findNavController().navigate(R.id.tasbihCounterFragment)
            startActivity(Intent(mContext, CompassDirectionActivity::class.java))

        }
        mLocationManager = MyLocationManager(requireContext(), locationCallback())
        checkLocationPermissions()
        binding.viewAutoDetect.setOnClickListener {
            checkLocationPermissions()
        }

        binding.toolbarBoard.imgToolbar.setOnClickListener {
            findNavController().navigate(R.id.sideMenuFragment)
        }

        val dashBoardFajrScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_fajr_screen)
        val dashBoardZuhrScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_zuhr_screen)
        val dashBoardAsarScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_asr_screen)
        val dashBoardMaghribScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_maghrib_screen)
        val dashBoardIshaScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_isha_screen)


        dashBoardFajrScreen.setOnClickListener {
            onViewOneClick(dashBoardFajrScreen)
        }
        dashBoardZuhrScreen.setOnClickListener {
            onViewTwoClick(dashBoardZuhrScreen)
        }
        dashBoardAsarScreen.setOnClickListener {
            onViewThreeClick(dashBoardAsarScreen)
        }
        dashBoardMaghribScreen.setOnClickListener {
            onViewFourClick(dashBoardMaghribScreen)
        }
        dashBoardIshaScreen.setOnClickListener {
            onViewFiveClick(dashBoardIshaScreen)
        }
        rView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        setUpQibla()
    }

    private fun initObserver() {
        with(viewModel){
            getPrayerTimeState.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            Log.d(DashBoardFragment::class.simpleName, "initObservation: Receive Data ${it.data.size }")
                            val prayTime = it.data[currentDay - 1].timings
                            prayerTimeList = listOf(
                                prayTime.Fajr.formatTimeTo12Hour(),
                                prayTime.Sunrise.formatTimeTo12Hour(),
                                prayTime.Dhuhr.formatTimeTo12Hour(),
                                prayTime.Asr.formatTimeTo12Hour(),
                                prayTime.Maghrib.formatTimeTo12Hour(),
                                prayTime.Isha.formatTimeTo12Hour()
                            )
                            viewModel.setPrayerTimes(prayerTimeList)
//                            updateUI(it)
//                            viewModel.deleteAll()
//                            viewModel.saveAllPrayersTimes(it)

                        }
                    }

                    is NetworkResult.Error -> {
                        Log.d(DashBoardFragment::class.simpleName, "initObserver: Error..")
                    }

                    is NetworkResult.Loading -> {
                        Log.d(DashBoardFragment::class.simpleName, "initObserver: Show Spinner..")
                    }

                }
            }

            viewModel.prayerTimes.observe(viewLifecycleOwner) {
                Log.d(DashBoardFragment::class.simpleName, "initObservation: Setting prayer times")
                if(firstTime==0) {
                    firstTime++
                    //get next prayer time
                    val time = nextPrayer(it)
                    SharedPreferences.saveTimerEndTime(mContext, time)
                    startCountdown(time / 1000)
                }
            }

            // handle count down value
            viewModel.counter.observe(viewLifecycleOwner){
                binding.tvCounterNextPrayerTime.text = "$it"
            }

            // to handle count down
            viewModel.index.observe(viewLifecycleOwner) { index ->
                Log.d(DashBoardFragment::class.simpleName, "initObserver: next Prayer $index")
                when (index) {
                    1 -> {
                        binding.tvPrayerTime.text = "Fajr"
                    }

                    2 -> {
                        binding.tvPrayerTime.text = "Sunrise"
                    }

                    3 -> {
                        binding.tvPrayerTime.text = "Duhr"
                    }

                    4 -> {
                        binding.tvPrayerTime.text = "Asr"
                    }

                    5 -> {
                        binding.tvPrayerTime.text = "Maghrib"
                    }

                    6 -> {
                        binding.tvPrayerTime.text = "Isha"
                    }
                }
            }
        }
    }

    fun startCountdown(totalSeconds: Long) {
        Log.d(DashBoardFragment::class.simpleName, "startCountdown: $totalSeconds")
        job = CoroutineScope(Dispatchers.Main).launch {
            for (seconds in totalSeconds downTo 0) {
                Log.d(DashBoardFragment::class.simpleName, "startCountdown: $seconds")
                SharedPreferences.saveTimerEndTime(mContext,seconds)
                viewModel.setCounterValue(seconds)
                delay(1000)
            }
        }
    }



    private fun nextPrayer(times: List<String>): Long {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = dateFormat.format(currentTime)
        val newTime = convertTimeToMilliseconds(formattedTime)
        for (i in times) {
            val x = convertTimeToMilliseconds(i)
            index++

            if (newTime < x) {
                val result = x - newTime
                viewModel.setIndex(index)
                return result
            }
        }
        return 0
    }



    private fun checkLocationPermissions() {
        if (checkLocationPermission()) {
            fetchLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun onViewOneClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 1)
        findNavController().navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewTwoClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 2)
        findNavController().navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewThreeClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 3)
        findNavController().navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFourClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 4)
        findNavController().navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFiveClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 5)
        findNavController().navigate(R.id.nextPrayerTimeFragment)
    }

    private fun setUpQibla() {
        val data = ArrayList<QiblaData>()
        data.add(QiblaData(R.drawable.qibla_icn, getString(R.string.qibla)))
        data.add(QiblaData(R.drawable.zakat_icn, getString(R.string.zakat)))
        data.add(QiblaData(R.drawable.names_icn, getString(R.string.names)))
        data.add(QiblaData(R.drawable.tasbeeh_icn, getString(R.string.tasbih_)))
        data.add(QiblaData(R.drawable.prayer_icn, getString(R.string.prayer)))
        data.add(QiblaData(R.drawable.quran_icn, getString(R.string.quran)))
        data.add(QiblaData(R.drawable.makah_live_icn, getString(R.string.makkah_live_)))
        data.add(QiblaData(R.drawable.near_me_icn, getString(R.string.near_me)))
        data.add(QiblaData(R.drawable.hijri_calendar_icn, getString(R.string.hijri_calendar)))
        data.add(QiblaData(R.drawable.hadith_icn, getString(R.string.hadith)))


        val adapter = QiblaAdapter(requireContext(), data) { position ->
            navigateToSpecificScreen(position)
        }
        rView.adapter = adapter

    }

    private fun navigateToSpecificScreen(position: Int) {
        // Navigate to specific screens based on position clicked
        when (position) {
            0 -> {
                // Handle click on position 0
                // Navigate to screen 0
                findNavController().navigate(R.id.qibalDirectionFragment)

            }
            1 -> {

                findNavController().navigate(R.id.zakatFragment)
            }
            3 -> {

                findNavController().navigate(R.id.tasbihFragment)
            }
            4 -> {
                findNavController().navigate(R.id.nextPrayerTimeFragment)

            }
            6 -> {
                findNavController().navigate(R.id.makkahLiveFragment)

            }
            // Add more cases for other positions
        }
    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        Log.d(DashBoardFragment::class.simpleName, "requestLocationPermission: ")
        permReqLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    var settingsLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(mLocationManager.isLocationEnabled()){
            fetchLocation()
        }else{
            Log.d(DashBoardFragment::class.simpleName, ": Tell user to why they need location.")
        }
    }

    private val permReqLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                Log.d(DashBoardFragment::class.simpleName, ": Permission Granted")
                fetchLocation()
            }else{
                Log.d(DashBoardFragment::class.simpleName, ": Permission Not Granted")
            }
        }

    private fun fetchLocation() {
        Log.d(DashBoardFragment::class.simpleName, "fetchLocation: ")
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(DashBoardFragment::class.simpleName, "fetchLocation: Permission not granted")
            return
        }
        if(!mLocationManager.isLocationEnabled()){
            Log.d(DashBoardFragment::class.simpleName, "fetchLocation: Location Not enabled..")
            AlertDialog.Builder(mContext)
                .setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(R.string.open_location_settings) { paramDialogInterface, paramInt ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    settingsLauncher.launch(intent)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
            return
        }
        mLocationManager.startLocationTracking()
    }

    private fun locationCallback(): (LocationResult) -> Unit {
        return { location ->
            Log.d(
                QibalDirectionFragment::class.simpleName,
                "locationCallback: ${location.lastLocation}"
            )
            if (location.lastLocation != null) {
                getPrayerTimings(location.lastLocation!!)
                updateLocationText(location.lastLocation!!)
                mLocationManager.stopLocationTracking()
            } else {
                Log.d(MyLocationManager::class.simpleName, "onLocationResult: Location is null")
                locationTextView.text = getString(R.string.location_not_available)
                mLocationManager.stopLocationTracking()
            }
        }
    }

    private fun getPrayerTimings(location: Location) {
        Log.d(DashBoardFragment::class.simpleName, "getPrayerTimings: ${location.latitude} - ${location.longitude}")
        viewModel.getPrayerTimes(currentYear,currentMonth,location.latitude,location.longitude,1)
    }

    private fun updateLocationText(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        val city = getCityFromLocation(latitude, longitude)
        Log.d(DashBoardFragment::class.simpleName, "updateLocationText: $city")
        val locationText = "City: $city"
        locationTextView.text = locationText
    }

    private fun getCityFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(mContext, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude, longitude, 1)!!

            if (addresses.isNotEmpty()) {
                val city = addresses[0].locality
                return city ?: getString(R.string.unknown_city)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return getString(R.string.unknown_city)
    }


    override fun onPause() {
        super.onPause()
        Log.d(DashBoardFragment::class.simpleName, "onPause: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(DashBoardFragment::class.simpleName, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(DashBoardFragment::class.simpleName, "onResume: ")
    }
}
