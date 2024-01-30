package com.qibla.qiblacompass.prayertime.finddirection.domain.repository

interface LoginRepository {
    suspend fun saveMobileNumberAndPassword(mobileNumber: String, password: String)
    suspend fun loginWithSavedCredentials(): Boolean
}