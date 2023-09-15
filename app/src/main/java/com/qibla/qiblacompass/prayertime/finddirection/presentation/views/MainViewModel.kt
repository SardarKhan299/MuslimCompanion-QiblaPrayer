package com.qibla.qiblacompass.prayertime.finddirection.presentation.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.qiblacompass.prayertime.finddirection.domain.usecase.GetNamazTimeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val usecase: GetNamazTimeUsecase):ViewModel() {

    /*private fun getNamazTimings(){
        usecase.namazTimeLiveData
    }*/

    private val countDownFlow = flow<Int> {
         val startValue = 10
        var currentValue = startValue
        emit(startValue)
        while (currentValue>0){
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }
    init {
        collectFlow()
    }

    private fun collectFlow(){
        viewModelScope.launch {
            val count = countDownFlow.filter { time->
                time%2==0
            }.map { time->
                time*time
            }.onEach {
                print(it)
            }
            print(count)
//            countDownFlow.collect{ time->
//                Log.d(MainViewModel::class.simpleName, "collectFlow: $time)
//            }

        }
    }

}