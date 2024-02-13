package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        fun saveTimerEndTime(context: Context,endTime:Long){
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putLong(PrayerConstants.COUNTDOWN_TIME_KEY, endTime)
                apply()
            }
        }

        fun saveUserVisit(context: Context,isFirstTimeLogin:Boolean){
            val msharedPreferences: SharedPreferences? = initShardPreference(context)
            msharedPreferences!!.edit().apply {
                putBoolean(PrayerConstants.FIRST_TIME_LOGIN, isFirstTimeLogin)
                apply()
            }
        }

        fun saveUserCity(context: Context,userCity:String){
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
    }


    fun getSelectionFromSharedPreferencesDetail(context: Context): Boolean {
        val msharedPreferences: SharedPreferences? =
            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.initShardPreference(
                context
            )
        return msharedPreferences!!.getBoolean(
            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_SELECTED_KEY,
            true
        )
    }

    //translation data
    fun saveTranslationToSharedPreferences(
        context: Context,
        urduTranslation: String,
        englishTranslation: String
    ) {
        val msharedPreferences: SharedPreferences? =
            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.initShardPreference(
                context
            )
        msharedPreferences!!.edit().apply {
            putString("urdu_translation", urduTranslation)
            putString("english_translation", englishTranslation)
            apply()
        }
    }

    fun saveSelectedPlayerPosition(context: Context, position: Int) {
        val msharedPreferences: SharedPreferences? =
            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.initShardPreference(
                context
            )
        msharedPreferences?.edit()?.putInt("selectedPlayerPosition", position)?.apply()
    }

    fun getSelectedPlayerPosition(context: Context): Int {
        val msharedPreferences: SharedPreferences? =
            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.initShardPreference(
                context
            )
        if (msharedPreferences != null) {
            return msharedPreferences.getInt("selectedPlayerPosition", 0)
        }
        return 0
    }




}
//
//   fun saveTranslations(
//        context: Context,
//        allahTranslations: ArrayList<Pair<String, String>>,
//        rasoolTranslations: ArrayList<Pair<String, String>>
//    ) {
//        val gson = Gson()
//        val allahJson = gson.toJson(allahTranslations)
//        val rasoolJson = gson.toJson(rasoolTranslations)
//
//        val sharedPreferences = context.getSharedPreferences(
//            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_KEY,
//            Context.MODE_PRIVATE
//        )
//        val editor = sharedPreferences.edit()
//        editor.putString("allah_translations", allahJson)
//        editor.putString("rasool_translations", rasoolJson)
//        editor.apply()
//    }
//   fun getAllahTranslations(context: Context): ArrayList<Pair<String, String>> {
//        val sharedPreferences = context.getSharedPreferences(
//            com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_KEY,
//            Context.MODE_PRIVATE
//        )
//        val allahJson = sharedPreferences.getString("allah_translations", "")
//        return if (allahJson.isNullOrEmpty()) {
//            ArrayList()
//        } else {
//            val gson = Gson()
//            val type = object : TypeToken<ArrayList<Pair<String, String>>>() {}.type
//            gson.fromJson(allahJson, type)
//        }
//    }
//fun getRasoolTranslations(context: Context): ArrayList<Pair<String, String>> {
//    val sharedPreferences = context.getSharedPreferences(com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_KEY, Context.MODE_PRIVATE)
//    val rasoolJson = sharedPreferences.getString("rasool_translations", "")
//    return if (rasoolJson.isNullOrEmpty()) {
//        ArrayList()
//    } else {
//        val gson = Gson()
//        val type = object : TypeToken<ArrayList<Pair<String, String>>>() {}.type
//        gson.fromJson(rasoolJson, type)
//    }
//}
//
//private fun saveSelectedDataToSharedPreferences(
//    context: Context,
//    namesData: NamesData,
//    translation: Pair<String, String>
//) {
//    val msharedPreferences: SharedPreferences? =
//        com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.initShardPreference(
//            context
//        )
//    msharedPreferences!!.edit().apply {
//        // Access the integer values from namesData:
//        putInt("selected_name_image", namesData.nameImage)  // Use namesData.nameImage
//        putInt(
//            "selected_name_number_image",
//            namesData.nameNumberImage
//        )  // Use namesData.nameNumberImage
//        putString("selected_translation_urdu", translation.first)
//        putString("selected_translation_english", translation.second)
//        apply()
//    }
//
//}
//
//
//fun saveSelectionToSharedPreferences(context: Context, isAllahSelected: Boolean) {
//    val sharedPreferences = context.getSharedPreferences(com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_NAME, Context.MODE_PRIVATE)
//    val editor = sharedPreferences.edit()
//    editor.putBoolean(com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_SELECTED_KEY_ALLAH, isAllahSelected)
//    editor.putBoolean(com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.PREFS_SELECTED_KEY_RASOOL, !isAllahSelected)
//    editor.apply()
//}
