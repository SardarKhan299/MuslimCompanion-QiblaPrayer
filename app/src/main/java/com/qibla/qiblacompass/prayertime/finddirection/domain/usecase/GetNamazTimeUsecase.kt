package com.qibla.qiblacompass.prayertime.finddirection.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.PrayerTimeResponse
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNamazTimeUsecase @Inject constructor(private val repository: MainRepository) {
     operator fun invoke(  year: Int,
                           month: Int,
                           latitude: Double,
                           longitude: Double,
                           method: Int) = flow {
        try {
            emit(NetworkResult.Loading())
            val timings = repository.getNamazTimings(year,month,latitude,longitude,method)
            if (timings.isSuccessful) {
                timings.body()?.let {
                    emit(NetworkResult.Success(timings.body()!!))
                }
            }

        }catch (e:java.lang.Exception){
            emit(NetworkResult.Error("500"))
        }
    }
}