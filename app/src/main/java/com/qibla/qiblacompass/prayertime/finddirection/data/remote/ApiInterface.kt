package com.qibla.qiblacompass.prayertime.finddirection.data.remote

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.PrayerTimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("calendar/{year}/{month}")
    suspend fun getNamazTimings(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
    ): Response<PrayerTimeResponse>
}