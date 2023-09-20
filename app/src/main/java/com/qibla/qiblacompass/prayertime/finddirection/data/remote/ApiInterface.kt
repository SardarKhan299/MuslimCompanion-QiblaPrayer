package com.qibla.qiblacompass.prayertime.finddirection.data.remote

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/api")
    suspend fun getPrayerTimings():List<ApiDto>
}