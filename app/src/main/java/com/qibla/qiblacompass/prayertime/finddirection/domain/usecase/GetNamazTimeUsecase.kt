package com.qibla.qiblacompass.prayertime.finddirection.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNamazTimeUsecase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() = flow<NetworkResult<List<ApiDto>>>{
        try {
            emit(NetworkResult.Loading())
            val timings = repository.getNamazTimings()
            emit(NetworkResult.Success(timings))
        }catch (e:java.lang.Exception){
            emit(NetworkResult.Error(500))
        }
    }
}