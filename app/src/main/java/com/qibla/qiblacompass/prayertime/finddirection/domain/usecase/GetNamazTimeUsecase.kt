package com.qibla.qiblacompass.prayertime.finddirection.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNamazTimeUsecase @Inject constructor(private val repository: MainRepository) {
    val namazTimeLiveData: LiveData<NetworkResult<ApiDto>>
        get() = _namazTimeLiveData
    private val _namazTimeLiveData = MutableLiveData<NetworkResult<ApiDto>>()
    suspend operator  fun invoke() {
        try {
            _namazTimeLiveData.value  = NetworkResult.Loading()
            val timings = repository.getNamazTimings()
            _namazTimeLiveData.value  = NetworkResult.Success(timings)
        }catch (e:java.lang.Exception){
            _namazTimeLiveData.value = NetworkResult.Error(201)
        }
    }
}