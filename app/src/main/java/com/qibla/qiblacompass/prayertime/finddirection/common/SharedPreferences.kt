package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.BIOMETRIC_ENABLE

class SharedPreferences {
    companion object {
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
    }
}