package com.qibla.qiblacompass.prayertime.finddirection.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.domain.repository.LoginRepository
import kotlinx.coroutines.tasks.await

//class LoginRepositoryImpl(private val context: Context) : LoginRepository {
//
//    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    override suspend fun saveMobileNumberAndPassword(mobileNumber: String, password: String) {
//        SharedPreferences.saveMobileNumberAndPassword(context, mobileNumber, password)
//    }
//
//    override suspend fun loginWithSavedCredentials(): Boolean {
//        val mobileNumber = SharedPreferences.getSavedMobileNumber(context)
//        val password = SharedPreferences.getSavedPassword(context)
//
//        if (!mobileNumber.isNullOrBlank() && !password.isNullOrBlank()) {
//            try {
//                firebaseAuth.signInWithEmailAndPassword("$mobileNumber", password)
//                return true
//            } catch (e: FirebaseAuthException) {
//                // Handle login failure
//            }
//        }
//        return false
//    }
//}
class LoginRepositoryImpl {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun register(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true // Registration successful
        } catch (e: Exception) {
            false // Registration failed
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true // Login successful
        } catch (e: Exception) {
            false // Login failed
        }
    }
}
