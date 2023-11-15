package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationResult
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentQibalDirectionBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter.TasbihCounterAdapter
import java.util.*


class QibalDirectionFragment :
    BaseFragment<FragmentQibalDirectionBinding>(R.layout.fragment_qibal_direction) {

    lateinit var recyclerView: RecyclerView
    private var qiblatIndicator: ImageView? = null
    private var imageDial: ImageView? = null
    private var imageNeedle: ImageView? = null
    private var bgShade: ImageView? = null
    private lateinit var tvDistance: TextView
    private lateinit var tvAngle: TextView
    private lateinit var tvIndication: TextView
    private lateinit var prefs: SharedPreferences
    private var compass: Compass? = null
    private lateinit var mLocationManager: MyLocationManager
    private var isFragmentAttached: Boolean = false
    private var qiblaAngel: Double = 0.0


    private var currentAzimuth = 0f
    private val RC_Permission = 1221
    private val imageQibalCompass = listOf(
        R.drawable.ic_compass_one, R.drawable.ic_compass_two, R.drawable.compass3,
        R.drawable.compass7, R.drawable.compass8, R.drawable.compass9,
        R.drawable.compass10, R.drawable.compass11, R.drawable.compass12,
        R.drawable.compass13, R.drawable.compass14, R.drawable.compass15, R.drawable.compass16
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbarQibalDirection
        recyclerView = binding.recycleViewQibalDirection
        imageNeedle = binding.needle
        bgShade = binding.bgShade
        tvAngle = binding.tvAngle
        imageDial = binding.dial
        qiblatIndicator = binding.qiblaIndicator
        tvIndication = binding.tvLocationCity
        tvDistance = binding.tvDistanceNumber
        //includeCompass.tvDistance
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        prefs = requireActivity().getSharedPreferences("", Context.MODE_PRIVATE)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mLocationManager = MyLocationManager(requireContext(), locationCallback())
        compass = Compass(requireContext())
       setupCompass()
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        toolbar.titleCounter.text = "Qibla Direction"
        toolbar.imgAddMore.setImageResource(R.drawable.icon_share)

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        val adapter = TasbihCounterAdapter(imageQibalCompass) { selectedImage ->
            (imageDial as AppCompatImageView).setImageResource(selectedImage)
        }
        recyclerView.adapter = adapter
        // Request location permission if not granted


    }

    override fun onStart() {
        super.onStart()
        Log.d(QibalDirectionFragment::class.simpleName, "onStart: ")
        compass?.start(requireContext())
    }

    override fun onPause() {
        super.onPause()
        compass?.stop()
        mLocationManager.stopLocationTracking()

    }

//    override fun onResume() {
//        super.onResume()
//        compass?.start(requireContext())
//    }
@Suppress("DEPRECATION")
override fun onResume() {
    super.onResume()

    if (ContextCompat.checkSelfPermission(
            requireContext(),
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // Location permission granted, start compass updates
        compass?.start(requireContext())
        fetch_GPS()
    } else {
        // Location permission not granted, request permission
        requestPermissions(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ),
            RC_Permission
        )
    }
}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isFragmentAttached = true
    }

    override fun onStop() {
        super.onStop()
        Log.d(QibalDirectionFragment::class.simpleName, "onStop: ")
        mLocationManager.stopLocationTracking()
        compass?.stop()
    }

    override fun onDetach() {
        super.onDetach()
        isFragmentAttached = false
    }
    @Suppress("DEPRECATION")
    private fun setupCompass() {
        Log.d(QibalDirectionFragment::class.simpleName, "setupCompass: ")
        // Initialize compass
        compass = Compass(requireContext())
        val cl: Compass.CompassListener = object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                // Adjust UI elements based on azimuth
                adjustGambarDial(azimuth)
                adjustArrowQiblat(azimuth)
            }
        }
        compass?.setListener(cl)

//        // Check for permissions and start location tracking
//        val permissionGranted = GetBoolean("permission_granted")
//        if (permissionGranted) {
//            getBearing()
//        } else {
//            requestPermissions(
//                arrayOf(
//                    ACCESS_FINE_LOCATION,
//                    ACCESS_COARSE_LOCATION
//                ),
//                RC_Permission
//            )
//        }
    }


    fun adjustGambarDial(azimuth: Float) {
        val an: Animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 60
        an.repeatCount = 0
        an.fillAfter = true
        imageDial!!.startAnimation(an)
        if (isFragmentAttached) {
            val resources = requireContext().resources
            if ((qiblaAngel - 30.0) <= currentAzimuth && (qiblaAngel + 30.0) >= currentAzimuth) {
                tvIndication.visibility =VISIBLE
                if ((qiblaAngel - 3.0) <= currentAzimuth && (qiblaAngel + 3.0) >= currentAzimuth) {
                    Log.d(QibalDirectionFragment::class.simpleName, "adjustGambarDial: Reached")
                    tvIndication.text = "Reached"
                    bgShade?.setImageDrawable(
                        getDrawable(
                            resources,
                            R.drawable.shade_green,
                            null
                        )
                    )
                } else {

                    tvIndication.text = "Almost There"
                    bgShade?.setImageDrawable(
                        getDrawable(
                            resources,
                            R.drawable.shade_yellow,
                            null
                        )
                    )
                }

                Log.d(
                    QibalDirectionFragment::class.simpleName,
                    "adjustGambarDial: Almost There $currentAzimuth"
                )
            } else if ((qiblaAngel - 80.0) <= currentAzimuth && (qiblaAngel + 80.0) >= currentAzimuth) {

                bgShade?.setImageDrawable(getDrawable(resources, R.drawable.shade_orange, null))
                tvIndication.visibility = GONE


            } else {

                bgShade?.setImageDrawable(getDrawable(resources, R.drawable.shade_red, null))
                tvIndication.visibility = GONE
            }

            //imageNeedle!!.startAnimation(an)
        }
    }

    fun adjustArrowQiblat(azimuth: Float) {
        //Log.d(TAG, "will set rotation from $currentAzimuth to $azimuth");
        val kiblat_derajat = GetFloat("kiblat_derajat")
        val an: Animation = RotateAnimation(
            -currentAzimuth + kiblat_derajat, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 60
        an.repeatCount = 0
        an.fillAfter = true
        qiblatIndicator!!.startAnimation(an)
        if (kiblat_derajat > 0) {
            qiblatIndicator!!.visibility = VISIBLE
        } else {
            qiblatIndicator!!.visibility = INVISIBLE
            qiblatIndicator!!.visibility = GONE
        }
    }

    private fun getBearing() {
        Log.d(QibalDirectionFragment::class.simpleName, "getBearing: ")
        fetch_GPS()
    }

    private fun getDirectionString(azimuthDegrees: Float): String {
        var where = "NW"
        if (azimuthDegrees >= 350 || azimuthDegrees <= 10) where = "N"
        if (azimuthDegrees < 350 && azimuthDegrees > 280) where = "NW"
        if (azimuthDegrees <= 280 && azimuthDegrees > 260) where = "W"
        if (azimuthDegrees <= 260 && azimuthDegrees > 190) where = "SW"
        if (azimuthDegrees <= 190 && azimuthDegrees > 170) where = "S"
        if (azimuthDegrees <= 170 && azimuthDegrees > 100) where = "SE"
        if (azimuthDegrees <= 100 && azimuthDegrees > 80) where = "E"
        if (azimuthDegrees <= 80 && azimuthDegrees > 10) where = "NE"
        return where
    }

    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_Permission) {
            // If request is cancelled, the result arrays are empty.
//            if (grantResults.isNotEmpty()
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED
//            )
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
            {
                // permission was granted, yay! Do the
                Log.d(
                    QibalDirectionFragment::class.simpleName,
                    "onRequestPermissionsResult: Granted"
                )
                SaveBoolean("permission_granted", true)
                qiblatIndicator!!.visibility = INVISIBLE
                qiblatIndicator!!.visibility = GONE
             //   setupCompass()
                compass?.start(requireContext())
                fetch_GPS()
            }
            else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.toast_permission_required),
                    Toast.LENGTH_LONG
                ).show()
                //  finish()
            }
        }
    }


    private fun SaveBoolean(Judul: String?, bbb: Boolean?) {
        val edit = prefs!!.edit()
        edit.putBoolean(Judul, bbb!!)
        edit.apply()
    }

    private fun GetBoolean(Judul: String?): Boolean {
        return prefs!!.getBoolean(Judul, false)
    }

    private fun SaveFloat(Judul: String?, bbb: Float?) {
        val edit = prefs!!.edit()
        edit.putFloat(Judul, bbb!!)
        edit.apply()
    }

    private fun GetFloat(Judul: String?): Float {
        return prefs!!.getFloat(Judul, 0f)
    }


    private fun fetch_GPS() {
        Log.d(QibalDirectionFragment::class.simpleName, "fetch_GPS: ")
        if (isLocationEnabled()) {
            Log.d(QibalDirectionFragment::class.simpleName, "fetch_GPS: Location is enabled.")
            mLocationManager.startLocationTracking()
        } else {
            mLocationManager.stopLocationTracking()
            qiblatIndicator!!.visibility = GONE
            Toast.makeText(mContext, "Please turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun locationCallback(): (LocationResult) -> Unit {
        return { location ->
            Log.d(
                QibalDirectionFragment::class.simpleName,
                "locationCallback: ${location.lastLocation}"
            )
            if (location.lastLocation != null) {
                calculateQiblaDir(
                    location.lastLocation!!.latitude,
                    location.lastLocation!!.longitude
                )
            } else {
                Log.d(MyLocationManager::class.simpleName, "onLocationResult: Location is null")
            }
        }
    }

    private fun calculateQiblaDir(myLat: Double, myLng: Double) {
        var result: Double
        val strYourLocation = (resources.getString(R.string.your_location)
                + " " + myLat + ", " + myLng)


        //Toast.makeText(getApplicationContext(), "Lokasi anda: - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        Log.d(QibalDirectionFragment::class.simpleName, "fetch_GPS: $strYourLocation")
        val kaabaLng =
            39.826206 // ka'bah Position
        val kaabaLat =
            Math.toRadians(21.422487) // ka'bah Position

        // Calculate Distance
        val startPoint = Location("locationA")
        startPoint.latitude = kaabaLat
        startPoint.longitude = kaabaLng

        val endPoint = Location("locationB")
        endPoint.latitude = myLat
        endPoint.longitude = myLng

        val distance = startPoint.distanceTo(endPoint).toDouble()
        tvDistance.text = convertMeterToKilometer(distance.toFloat()).toString() + " km"


        val myLatRad = Math.toRadians(myLat)
        val longDiff = Math.toRadians(kaabaLng - myLng)
        val y = Math.sin(longDiff) * Math.cos(kaabaLat)
        val x =
            Math.cos(myLatRad) * Math.sin(kaabaLat) - Math.sin(myLatRad) * Math.cos(kaabaLat) * Math.cos(
                longDiff
            )
        result = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
        Log.d(QibalDirectionFragment::class.simpleName, "fetch_GPS: $result")
        qiblaAngel = result
        SaveFloat("kiblat_derajat", result.toFloat())

        val strKaabaDirection: String =
            (java.lang.String.format(Locale.ENGLISH, "%.0f", result.toFloat())
                    + " " + resources.getString(R.string.degree) + " " + getDirectionString(
                result.toFloat()
            ))

        tvAngle.text = strKaabaDirection
        qiblatIndicator!!.visibility = VISIBLE
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun convertMeterToKilometer(meter: Float): Float {
        return (meter * 0.001).toFloat()
    }


}

