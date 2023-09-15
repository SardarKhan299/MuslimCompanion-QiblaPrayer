package com.qibla.qiblacompass.prayertime.finddirection.presentation.views

import com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.ApiDto

data class MainActivityState(val isLoading:Boolean=false,val timings:List<ApiDto> = emptyList(),val error:String="")
