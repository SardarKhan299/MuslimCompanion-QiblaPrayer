package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashBoardBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding.OnboardingActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection.CompassDirectionActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu.SideMenuFragment
import kotlin.math.log


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(R.layout.fragment_dash_board) {
    private lateinit var rView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
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
        binding.toolbarBoard.groupToolbarProfile.visibility = View.GONE
        binding.toolbarBoard.groupToolbar.visibility = View.VISIBLE
        rView = binding.layoutBoardFragment.findViewById(R.id.rv_qibla)
        binding.tvAds.setOnClickListener {
            //Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
            startActivity(Intent(mContext, CompassDirectionActivity::class.java))

        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        binding.viewAutoDetect.setOnClickListener {
            getCurrentLocation()
        }
//
//        binding.viewAutoDetect.setOnClickListener {
//
//            getCurrentLocation()
////            if (binding.includeAutoDetect.layoutDialog.visibility == View.VISIBLE) {
////                binding.includeAutoDetect.layoutDialog.visibility = View.INVISIBLE
////            } else {
////                binding.includeAutoDetect.layoutDialog.visibility = View.VISIBLE
////            }
//        }
        binding.toolbarBoard.imgToolbar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.sideMenuFragment)
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

    private fun onViewOneClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 1)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewTwoClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 2)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewThreeClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 3)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFourClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 4)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFiveClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 5)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun setUpQibla() {
        val data = ArrayList<QiblaData>()
        data.add(QiblaData(R.drawable.qibla_icn, getString(R.string.qibla)))
        data.add(QiblaData(R.drawable.zakat_icn, getString(R.string.zakat)))
        data.add(QiblaData(R.drawable.names_icn, getString(R.string.Names)))
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
                Navigation.findNavController(requireView()).navigate(R.id.qibalDirectionFragment)

            }
            1 -> {

                Navigation.findNavController(requireView()).navigate(R.id.zakatFragment)
            }
            3 -> {

                Navigation.findNavController(requireView()).navigate(R.id.tasbihFragment)
            }
            4 -> {
                Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)

            }
            6 -> {
                Navigation.findNavController(requireView()).navigate(R.id.makkahLiveFragment)

            }
            // Add more cases for other positions
        }
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
           // final latitude and longitude here...
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Log.d("MyFragment", "getCurrentLocation: Didn't receive any location")
                    } else {
                        Log.d(
                            "MyFragment",
                            "getCurrentLocation: Get receive location ${"" + location.latitude} + ${"" + location.longitude}"
                        )
//                        textOne.text = "" + location.latitude
//                        textTwo.text = "" + location.longitude
                    }
                }

            } else {
                //settings open here
                Log.d("DashBoardFragment", "getCurrentLocation:...Ture on Location.... ")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            //request permission here
            requestPermission()

        }

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )

    }

    private fun checkPermissions(): Boolean {

        if (ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("DashBoardFragment", "onRequestPermissionsResult:....Granted..... ")
                getCurrentLocation()
            } else {
                Log.d("DashBoardFragment", "onRequestPermissionsResult:....Denied..... ")

            }
        }

    }

}