package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.preference.PreferenceManager
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.BIOMETRIC_ENABLE
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.SELECTED_IMAGE
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names.NamesData
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names.NamesFragment

class SharedPreferences {


    companion object {
        private const val USER_NAME_KEY = "user_name"
        private const val USER_EMAIL_KEY = "user_email"
        private const val USER_PROFILE = "profile"
        private val PREFS_KEY = "selected_data"
        private val PREFS_SELECTED_KEY = "isAllahNamesSelected"


        // Flag to determine which set of data to display
        private val PREFS_SELECTED_KEY_ALLAH = "isAllahNamesSelected"
        private val PREFS_SELECTED_KEY_RASOOL = "isRasoolNamesSelected"

        private var isAllahNamesSelected = false
        private var isRasoolNamesSelected = false
        var mSharedPreferences: SharedPreferences? = null
        private fun initShardPreference(context: Context): SharedPreferences? {
            if (mSharedPreferences == null) {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            }
            return mSharedPreferences
        }

        fun enableBiometricLogin(context: Context, enableBiometric: Boolean) {
            Log.d(SharedPreferences::class.simpleName, "enableBiometricLogin: $enableBiometric")
            val editor: SharedPreferences.Editor = initShardPreference(context)!!.edit()
            editor.putBoolean(BIOMETRIC_ENABLE, enableBiometric)
            editor.apply()
        }

        fun isBiometricEnabled(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            if (msharedPreferences != null) {
                return msharedPreferences.getBoolean(BIOMETRIC_ENABLE, false)
            }
            return false
        }

        fun saveImageValue(context: Context, imageName: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            if (msharedPreferences != null) {

                mSharedPreferences?.edit()?.putString(SELECTED_IMAGE, imageName)?.apply()
            }
        }

        fun retrieveImageValue(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            if (msharedPreferences != null) {
                return mSharedPreferences?.getString(SELECTED_IMAGE, null)
            }
            return ""
        }


        fun saveSelectedPrayerPosition(context: Context, position: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences?.edit()?.putInt("selectedPrayerPosition", position)?.apply()
        }

        fun getSelectedPrayerPosition(context: Context): Int {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            if (msharedPreferences != null) {
                return msharedPreferences.getInt("selectedPrayerPosition", 0)
            }
            return 0
        }

        fun saveMobileNumberAndPassword(context: Context, mobileNumber: String, password: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString("saved_mobile_number", mobileNumber)
                putString("saved_password", password)
                apply()
            }
        }

        fun saveSelectedPositionToSharedPreferences(context: Context, position: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences?.edit()?.putInt("selected_position", position)?.apply()
        }

        fun getSavedMobileNumber(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString("saved_mobile_number", null)
        }

        fun getSavedPassword(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return mSharedPreferences!!.getString("saved_password", null)
        }

        fun saveUserDetails(
            context: Context,
            userName: String,
            userEmail: String,
            userProfile: String
        ) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(USER_NAME_KEY, userName)
                putString(USER_EMAIL_KEY, userEmail)
                putString(USER_PROFILE, userProfile)
                apply()
            }
        }

        fun getUserName(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences?.getString(USER_NAME_KEY, null)
        }

        fun getUserProfile(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            val uriString = msharedPreferences?.getString(USER_PROFILE, null)
            return if (uriString != null) Uri.parse(uriString).toString() else null
        }

        fun getUserEmail(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences?.getString(USER_EMAIL_KEY, null)
        }

        fun saveSelectedPositionToSharedPreferences(
            context: Context,
            namesData: NamesData
        ) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putInt("selected_name_image", namesData.nameImage)
                putInt("selected_name_number_image", namesData.nameNumberImage)
                apply()
            }
        }
        //store whether the user clicked on "viewAllahNames" or "viewRasoolNames" in SharedPreferences
        fun saveSelectionToSharedPreferences(context: Context,isAllahNames: Boolean) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().putBoolean(PREFS_SELECTED_KEY, isAllahNames).apply()
        }

        fun getSelectionFromSharedPreferences(context: Context,key: String): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(key, false)
        }

        fun saveSelectionToSharedPreferences(context: Context,key: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().putBoolean(key, true).apply()
        }

        fun getSelectionFromSharedPreferencesDetail(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(PREFS_SELECTED_KEY, true)
        }

    }
    }