package com.qibla.qiblacompass.prayertime.finddirection.common

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*


@SuppressLint("MissingPermission")
class MyLocationManager(
    context: Context,
    private val locationCallBack: (LocationResult) -> Unit

) : LocationCallback() {

    private var request: LocationRequest
    private var locationClient: FusedLocationProviderClient
    private var context:Context = context

    init {
        // getting the location client
        locationClient = LocationServices.getFusedLocationProviderClient(context)
        request = createRequest()
    }

    private fun createRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0).apply {
            setMinUpdateDistanceMeters(100f)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setMaxUpdates(1)
            setWaitForAccurateLocation(true)
        }.build()


    fun startLocationTracking() =
        locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())


    fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(location: LocationResult) {
        // TODO: on location change - do something with new location
        locationCallBack.invoke(location)
    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        // TODO: react on the availability change
    }

     fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}