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
    const val COUNTDOWN_TIME_KEY = "countdown_end_time"
}
