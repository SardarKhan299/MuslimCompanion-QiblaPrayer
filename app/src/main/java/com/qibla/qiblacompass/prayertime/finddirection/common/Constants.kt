package com.qibla.qiblacompass.prayertime.finddirection.common

object Constants {
    const val BASE_URL = "http://api.aladhan.com/v1/"
    const val PASS_REG_V2: String = "[!@#\$%^&*()_=+{}/.<>|\\[\\]~-]"
    const val PASS_REG: String = "[^\\s]+\$"
}

object PrayerConstants {
    const val FAJR = "FAJR"
    const val ZUHAR = "ZUHAR"
    const val ASR = "ASR"
    const val MAGHRIB = "MAGHRIB"
    const val ISHA = "ISHA"
    const val TAHAJJUD = "TAHAJJUD"
    const val MAKKAH_LIVE_URL1 = "makkahLiveUrl1"

    const val COUNTDOWN_TIME_KEY = "countdown_end_time"
    const val FIRST_TIME_LOGIN = "first_time_login"
    const val USER_CITY = "user_city"
    const val SPLASH_VISIT_TIME = 4000L
    const val PRIVACY_URL = "privacyUrl"
    const val PRAYER_FAJR = "Fajr"
    const val PRAYER_ZUHR = "Zuhr"
    const val PRAYER_ASR = "Asr"
    const val PRAYER_MAGHRIB = "Maghrib"
    const val PRAYER_ISHA = "Isha"
    const val REMINDER_NONE =  "None"
    const val REMINDER_FIVE_MINT_BEFORE =  "5 mins before"
    const val REMINDER_TEN_MINT_BEFORE =  "10 mins before"
    const val REMINDER_FIFTEEN_MINT_BEFORE =  "15 mins before"
    const val NOTIFICATION_NONE = "none"
    const val NOTIFICATION_SILENT = "silent"
    const val NOTIFICATION_BEEP ="beep"
    const val AUDIO_BEEP = "beep_sound"
    const val NOTIFICATION_ADHAN_ONE = "adhan one"
    const val AUDIO_ADHAN_ONE = "one_azan_by_nasir_al_qatami"
    const val NOTIFICATION_ADHAN_TWO = "adhan two"
    const val AUDIO_ADHAN_TWO = "two_azan_by_mansour_al_zahrani"
    const val NOTIFICATION_ADHAN_THREE = "adhan three"
    const val AUDIO_ADHAN_THREE = "three_adhan_by_masjid_al_haram"
    const val NOTIFICATION_ADHAN_FOUR = "adhan four"
    const val AUDIO_ADHAN_FOUR = "four_adhan_by_mishary_rashid_alafasy"
}
