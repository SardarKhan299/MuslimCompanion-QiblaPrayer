package com.qibla.qiblacompass.prayertime.finddirection.domain.repository

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.PrayerTimeResponse
import retrofit2.Response

interface MainRepository {
    suspend fun getNamazTimings(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
    ): Response<PrayerTimeResponse>
}