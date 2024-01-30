package com.qibla.qiblacompass.prayertime.finddirection.domain.usecase

import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.LoginRepository


class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun saveMobileNumberAndPassword(mobileNumber: String, password: String) {
        loginRepository.saveMobileNumberAndPassword(mobileNumber, password)
    }

    suspend fun loginWithSavedCredentials(): Boolean {
        return loginRepository.loginWithSavedCredentials()
    }
}