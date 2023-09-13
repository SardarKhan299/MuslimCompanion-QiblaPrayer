package com.qibla.qiblacompass.prayertime.finddirection.common

sealed class NetworkResult<T>(
    val data: Any? = null,
    val messageCode: Int? = null
) {

    class Success<T>(data: Any) : NetworkResult<T>(data)

    class Error<T>(messageCode: Int?, data: Any? = null) : NetworkResult<T>(data, messageCode)

    class Loading<T> : NetworkResult<T>()

}