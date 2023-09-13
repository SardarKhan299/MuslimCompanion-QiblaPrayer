package com.qibla.qiblacompass.prayertime.finddirection.domain.repository

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto

interface MainRepository {

    suspend fun getNamazTimings():List<ApiDto>

}