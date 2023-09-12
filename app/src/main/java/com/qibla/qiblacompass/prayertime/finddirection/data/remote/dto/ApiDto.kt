package com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto

import com.qibla.qiblacompass.prayertime.finddirection.domain.model.UiModel

data class ApiDto(val data:String)

fun ApiDto.toUiModel():UiModel{
    return UiModel(data = data)
}