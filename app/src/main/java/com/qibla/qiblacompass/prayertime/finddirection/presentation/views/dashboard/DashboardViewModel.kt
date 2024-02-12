package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.formatTime
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.PrayerTimeResponse
import com.qibla.qiblacompass.prayertime.finddirection.domain.usecase.GetNamazTimeUsecase
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.MainActivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val usecase: GetNamazTimeUsecase):ViewModel() {

    //livedata objects
    private val _getPrayerTimeState = MutableLiveData<NetworkResult<PrayerTimeResponse>>()
    val getPrayerTimeState: LiveData<NetworkResult<PrayerTimeResponse>> = _getPrayerTimeState

    //livedata objects
    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    //livedata objects
    private val _prayerTimes = MutableLiveData<List<String>>()
    val prayerTimes: LiveData<List<String>> = _prayerTimes


    //livedata objects Counter
    private val _counter = MutableLiveData<String>()
    val counter: LiveData<String> = _counter

    fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
    ) {
        viewModelScope.launch {

            usecase(year,month,latitude,longitude,method).onEach { result->
                when(result){
                    is NetworkResult.Success->{
                        _getPrayerTimeState.value = NetworkResult.Success(result.data!!)
                    }
                    is NetworkResult.Loading->{
                        _getPrayerTimeState.value = NetworkResult.Loading()
                    }
                    is NetworkResult.Error->{
                        _getPrayerTimeState.value = NetworkResult.Error(result.message,result.data)
                    }
                }
            }.launchIn(viewModelScope)


        }

    }

    fun setIndex(index: Int) {
        _index.postValue(index)
    }

    fun setCounterValue(counter: Long) {
        val formattedTime = formatTime(counter)
        _counter.postValue(formattedTime)
    }

    fun setPrayerTimes(PrayerTimes: List<String>) {
        _prayerTimes.postValue(PrayerTimes)
    }

}