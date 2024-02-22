package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.ENTERED_VALUE_KEY
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.KEY_INCREMENTAL_COUNTER
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.KEY_NAVIGATING_BACK_TO_TASBIH_SCREEN
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.BIOMETRIC_ENABLE
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.SELECTED_IMAGE
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names.NamesData

class SharedPreferences {


    companion object {
        private const val USER_NAME_KEY = "user_name"
        private const val USER_EMAIL_KEY = "user_email"
        private const val USER_PROFILE = "profile"
        private val PREFS_KEY = "selected_data"
        private const val PREFS_NAMES_DATA_KEY = "names_data_key"
        private const val KEY_SELECTED_POSITION = "selected_position"

        private val PREFS_SELECTED_KEY = "isAllahNamesSelected"

        private const val PREFS_NAME = "MyPrefs"

        private const val PREFS_SELECTED_NAME = "selectedName"

        // Flag to determine which set of data to display
        val PREFS_SELECTED_KEY_ALLAH = "isAllahNamesSelected"
        val PREFS_SELECTED_KEY_RASOOL = "isRasoolNamesSelected"

        var isAllahNamesSelected = false
        var isRasoolNamesSelected = false
        private const val PREF_IS_ALLAH_SELECTED = "is_allah_selected"
        private const val PREF_IS_Rasool_SELECTED = "is_allah_selected"
        private const val PREF_PRE_ALERT_VALUE = "preAlertValue"
        private const val PREF_PRE_ALERT_STYLE_KEY = "preAlertValueStyle"
        private const val PREF_NOTIFICATION_STYLE_KEY = "preAlertValueNotificationStyle"
        private const val AUDIO_PREF_KEY = "audio_pref_key"
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

        fun saveTimerEndTime(context: Context, endTime: Long) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putLong(PrayerConstants.COUNTDOWN_TIME_KEY, endTime)
                apply()
            }
        }

        fun saveUserVisit(context: Context, isFirstTimeLogin: Boolean) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putBoolean(PrayerConstants.FIRST_TIME_LOGIN, isFirstTimeLogin)
                apply()
            }
        }

        fun saveUserCity(context: Context, userCity: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(PrayerConstants.USER_CITY, userCity)
                apply()
            }
        }

        fun isFirstTimeLogin(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(PrayerConstants.FIRST_TIME_LOGIN, true)
        }

        fun getUserCity(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(PrayerConstants.USER_CITY, "")
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

        fun getCounterEndTime(context: Context): Long {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return mSharedPreferences!!.getLong(PrayerConstants.COUNTDOWN_TIME_KEY, 0L)
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
            return msharedPreferences?.getString(USER_EMAIL_KEY, "")
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
//        fun saveSelectionToSharedPreferences(context: Context, isAllahNames: Boolean) {
//            val msharedPreferences: SharedPreferences? = initShardPreference(context)
//            msharedPreferences!!.edit().putBoolean(PREFS_SELECTED_KEY, isAllahNames).apply()
//        }

        fun getSelectionFromSharedPreferences(context: Context, key: String): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(key, false)
        }

        fun saveSelectionToSharedPreferences(context: Context, key: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().putBoolean(key, true).apply()
        }

        fun saveSelectedName(context: Context, name: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(PREFS_SELECTED_NAME, name)
                apply()
            }
        }

        fun getSelectedName(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(PREFS_SELECTED_NAME, null)
        }


        fun saveSelectedPosition(context: Context, position: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().putInt(KEY_SELECTED_POSITION, position).apply()
        }


        fun getSelectedPosition(context: Context): Int {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getInt(
                KEY_SELECTED_POSITION,
                -1
            ) // Default value -1 if not found
        }

        // Function to set the selection status
        fun setIsAllahSelected(context: Context, isAllahSelected: Boolean) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putBoolean(PREF_IS_ALLAH_SELECTED, isAllahSelected)
                apply()
            }
        }

        fun isAllahSelected(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(
                PREF_IS_ALLAH_SELECTED,
                false
            ) // Default to true if not found
        }

        fun isRasoolSelected(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(
                PREF_IS_Rasool_SELECTED,
                false
            ) // Default to true if not found
        }

        fun logoutUser(mContext: Context) {
            saveUserDetails(mContext, "", "", "")
        }


        fun getSelectionFromSharedPreferencesDetail(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(PREFS_SELECTED_KEY, true)
        }

        //translation data
        fun saveTranslationToSharedPreferences(
            context: Context,
            urduTranslation: String,
            englishTranslation: String
        ) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString("urdu_translation", urduTranslation)
                putString("english_translation", englishTranslation)
                apply()
            }
        }

        fun saveSelectedPlayerPosition(context: Context, position: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences?.edit()?.putInt("selectedPlayerPosition", position)?.apply()
        }

        fun getSelectedPlayerPosition(context: Context): Int {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            if (msharedPreferences != null) {
                return msharedPreferences.getInt("selectedPlayerPosition", 0)
            }
            return 0
        }

        fun saveIncrementalCounter(context: Context, counter: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putInt(KEY_INCREMENTAL_COUNTER, counter)
                apply()
            }
        }

        fun retrieveIncrementalCounter(context: Context): Int {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getInt(KEY_INCREMENTAL_COUNTER, 0)
        }


        fun saveEnteredValue(context: Context, enteredValue: Int) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putInt(ENTERED_VALUE_KEY, enteredValue).apply()
            }
        }

        fun retrieveEnteredValue(context: Context): Int {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getInt(ENTERED_VALUE_KEY, 0)
        }

        fun setNavigatingBackToTasbihScreen(context: Context, navigatingBack: Boolean) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putBoolean(KEY_NAVIGATING_BACK_TO_TASBIH_SCREEN, navigatingBack).apply()
            }
        }

        fun getNavigatingBackToTasbihScreen(context: Context): Boolean {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getBoolean(KEY_NAVIGATING_BACK_TO_TASBIH_SCREEN, false)
        }

        fun savePreAlertValue(context: Context, value: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(PREF_PRE_ALERT_VALUE, value)
                apply()
            }
        }

        fun getPreAlertValue(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(PREF_PRE_ALERT_VALUE, "")
        }

        // Method to set the view style and save to SharedPreferences
        fun setViewStyleAndSaveToPrefs(
            context: Context,
            titleTextView: TextView,
            tickImageView: ImageView,
            value: String
        ) {
            // Set view style
            titleTextView.setTextAppearance(R.style.selected_notification_text_style)
            tickImageView.visible()

            // Save selected value to SharedPreferences
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(PREF_PRE_ALERT_STYLE_KEY, value)
                apply()
            }
        }


        // Method to load pre-alert value from SharedPreferences
        fun loadPreAlertValue(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(PREF_PRE_ALERT_STYLE_KEY, null)
        }

        // Method to set the view style and save to SharedPreferences
        fun setViewStyleAndSaveToPrefs(
            context: Context,
            titleTextView: TextView,
            tickImageView: ImageView,
            speakerTextView: TextView? = null,
            isSpeakerText: Boolean = false,
            value: String
        ) {
            // Set view style
            titleTextView.setTextAppearance(R.style.selected_notification_text_style)
            tickImageView.visible()

            // Check if speaker text exists and the isSpeakerText flag is true
            if (speakerTextView != null && isSpeakerText) {
                speakerTextView.setTextAppearance(R.style.makkah_view_text_text_style)
            }

            // Save selected value to SharedPreferences
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(PREF_NOTIFICATION_STYLE_KEY, value)
                apply()
            }
        }

        // Method to load notification style value from SharedPreferences
        fun loadNotificationStyleValue(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(PREF_NOTIFICATION_STYLE_KEY, null)
        }

        //Save Audio
        fun saveAudioNameInPrefs(context: Context, audioName: String) {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putString(AUDIO_PREF_KEY, audioName)
                apply()
            }
        }

        private fun getStoredAudioName(context: Context): String? {
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            return msharedPreferences!!.getString(AUDIO_PREF_KEY, null)
        }

    }
}
