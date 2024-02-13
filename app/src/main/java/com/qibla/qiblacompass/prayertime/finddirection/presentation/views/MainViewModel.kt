package com.qibla.qiblacompass.prayertime.finddirection.presentation.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.qiblacompass.prayertime.finddirection.common.NetworkResult
import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto
import com.qibla.qiblacompass.prayertime.finddirection.domain.usecase.GetNamazTimeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val usecase: GetNamazTimeUsecase):ViewModel() {

    init {
        getNamazTimings()
    }

    val state: LiveData<MainActivityState>
        get() = _state

    private val _state = MutableLiveData<MainActivityState>()

    private fun getNamazTimings(){
//        usecase().onEach { result->
//            when(result){
//                is NetworkResult.Success->{
//                    _state.value = MainActivityState(timings = result.data as List<ApiDto>)
//                }
//                is NetworkResult.Loading->{
//                    _state.value = MainActivityState(isLoading = true)
//                }
//                is NetworkResult.Error->{
//                    _state.value = MainActivityState(error = result.message!!)
//                }
//            }
//        }.launchIn(viewModelScope)
    }


}