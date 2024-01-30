package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.qiblacompass.prayertime.finddirection.data.repository.LoginRepositoryImpl
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.LoginRepository
import com.qibla.qiblacompass.prayertime.finddirection.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class AuthViewModel(private val loginRepositoryImpl: LoginRepositoryImpl) : ViewModel() {

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean>
        get() = _registrationResult

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registrationResult.value = loginRepositoryImpl.register(email, password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = loginRepositoryImpl.login(email, password)
        }
    }
}