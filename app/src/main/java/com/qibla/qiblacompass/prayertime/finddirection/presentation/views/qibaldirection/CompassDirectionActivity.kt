package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.LocationResult
import com.qibla.qiblacompass.prayertime.finddirection.R
import java.util.*

class CompassDirectionActivity : AppCompatActivity() {


    private var compass: Compass? = null
    private var qiblatIndicator: ImageView? = null
    private var imageDial: ImageView? = null
    private var imageNeedle: ImageView? = null
    private var bgShade: ImageView? = null
    private lateinit var tvDistance: TextView
    private lateinit var tvAngle: TextView
    private lateinit var tvIndication: TextView

    private var qiblaAngel: Double = 0.0

    private lateinit var mLocationManager: MyLocationManager
    private val TAG = "CompassDirectionActivity"
    private var currentAzimuth = 0f
    private var prefs: SharedPreferences? = null
    private val RC_Permission = 1221

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass_direction)

        /////////////////////////////////////////////////
        /////////////////////////////////////////////////
        prefs = getSharedPreferences("", MODE_PRIVATE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //////////////////////////////////////////
        //////////////////////////////////////////
        qiblatIndicator = findViewById(R.id.qibla_indicator)
        imageDial = findViewById(R.id.dial)
        imageNeedle = findViewById(R.id.needle)
        bgShade = findViewById(R.id.bg_shade)
        tvAngle = findViewById(R.id.tv_angle)
        tvIndication = findViewById(R.id.tv_indication)
        tvDistance = findViewById(R.id.tv_distance)
        mLocationManager = MyLocationManager(this, locationCallback())
        setupCompass()


    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG::class.simpleName, "onStart: ")
        compass?.start(this)
    }

    override fun onPause() {
        super.onPause()
        mLocationManager.stopLocationTracking()
        compass?.stop()
    }

    override fun onResume() {
        super.onResume()
        compass?.start(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG::class.simpleName, "onStop: ")
        mLocationManager.stopLocationTracking()
        compass?.stop()
    }


    private fun setupCompass() {
        Log.d(TAG::class.simpleName, "setupCompass: ")
        val permission_granted = GetBoolean("permission_granted")
        if (permission_granted) {
            getBearing()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                ),
                RC_Permission
            )

        }
        compass = Compass(this)
        val cl: Compass.CompassListener = object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                adjustGambarDial(azimuth)
                adjustArrowQiblat(azimuth)
            }
        }
        compass!!.setListener(cl)

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
        if ((qiblaAngel - 30.0) <= currentAzimuth && (qiblaAngel + 30.0) >= currentAzimuth) {
            tvIndication.visibility = View.VISIBLE
            if ((qiblaAngel - 3.0) <= currentAzimuth && (qiblaAngel + 3.0) >= currentAzimuth) {
                Log.d(TAG::class.simpleName, "adjustGambarDial: Reached")
                tvIndication.text = "Reached"
                bgShade?.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.shade_green,
                        null
                    )
                )
            } else {
                tvIndication.text = "Almost There"
                bgShade?.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.shade_yellow,
                        null
                    )
                )
            }

            Log.d(TAG::class.simpleName, "adjustGambarDial: Almost There $currentAzimuth")
        } else if ((qiblaAngel - 80.0) <= currentAzimuth && (qiblaAngel + 80.0) >= currentAzimuth) {
            bgShade?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.shade_orange,
                    null
                )
            )
            tvIndication.visibility = View.GONE
        } else {
            bgShade?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.shade_red,
                    null
                )
            )
        }
        //imageNeedle!!.startAnimation(an)
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
            qiblatIndicator!!.visibility = View.VISIBLE
        } else {
            qiblatIndicator!!.visibility = INVISIBLE
            qiblatIndicator!!.visibility = View.GONE
        }
    }

    fun getBearing() {
        Log.d(TAG::class.simpleName, "getBearing: ")
        fetch_GPS()
//        // Get the location manager
//        val kaabaDegs = GetFloat("kiblat_derajat")
//        if (kaabaDegs > 0.0001) {
//            qiblatIndicator!!.visibility = View.VISIBLE
//        } else {
//            fetch_GPS()
//        }
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_Permission) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                // permission was granted, yay! Do the
                Log.d(TAG::class.simpleName, "onRequestPermissionsResult: Granted")
                SaveBoolean("permission_granted", true)
                qiblatIndicator!!.visibility = INVISIBLE
                qiblatIndicator!!.visibility = View.GONE
                fetch_GPS()
            } else {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.toast_permission_required),
                    Toast.LENGTH_LONG
                ).show()
                finish()
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


    @SuppressLint("MissingPermission")
    fun fetch_GPS() {
        Log.d(TAG::class.simpleName, "fetch_GPS: ")
        if (isLocationEnabled()) {
            Log.d(TAG::class.simpleName, "fetch_GPS: Location is enabled.")
            mLocationManager.startLocationTracking()
        } else {
            mLocationManager.stopLocationTracking()
            qiblatIndicator!!.visibility = View.GONE
            Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }


    }

    private fun locationCallback(): (LocationResult) -> Unit {
        return { location ->
            Log.d(TAG::class.simpleName, "locationCallback: ${location.lastLocation}")
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
        Log.d(TAG::class.simpleName, "fetch_GPS: $strYourLocation")
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
        Log.d(TAG::class.simpleName, "fetch_GPS: $result")
        qiblaAngel = result
        SaveFloat("kiblat_derajat", result.toFloat())

        val strKaabaDirection: String =
            (java.lang.String.format(Locale.ENGLISH, "%.0f", result.toFloat())
                    + " " + resources.getString(R.string.degree) + " " + getDirectionString(
                result.toFloat()
            ))

        tvAngle.text = strKaabaDirection
        qiblatIndicator!!.visibility = View.VISIBLE
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun convertMeterToKilometer(meter: Float): Float {
        return (meter * 0.001).toFloat()
    }


}