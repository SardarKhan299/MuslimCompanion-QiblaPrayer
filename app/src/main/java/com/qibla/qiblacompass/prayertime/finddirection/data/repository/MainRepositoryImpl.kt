package com.qibla.qiblacompass.prayertime.finddirection.data.repository

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.ApiInterface
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.PrayerTimeResponse
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class  MainRepositoryImpl @Inject constructor(private val api:ApiInterface) :MainRepository {


    override suspend fun getNamazTimings(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
    ): Response<PrayerTimeResponse> =
        api.getNamazTimings(year, month, latitude, longitude, method)

}