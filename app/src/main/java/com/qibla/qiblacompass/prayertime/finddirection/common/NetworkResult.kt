package com.qibla.qiblacompass.prayertime.finddirection.common

sealed class NetworkResult<T>(
    val data: T? = null,
    val messageCode: Int? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(messageCode: Int?, data: T? = null) : NetworkResult<T>(data, messageCode)

    class Loading<T> : NetworkResult<T>()

}