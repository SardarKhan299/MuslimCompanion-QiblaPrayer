package com.qibla.qiblacompass.prayertime.finddirection.data.repository

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.ApiInterface
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import javax.inject.Inject

class  MainRepositoryImpl @Inject constructor(private val api:ApiInterface) :MainRepository {

    override suspend fun getNamazTimings(): List<ApiDto> {
      return  api.getPrayerTimings()
    }
}