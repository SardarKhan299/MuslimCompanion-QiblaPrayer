package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatTimeTo12Hour(): String {
    val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this // Return the original string if parsing fails
    }
}

fun Context.loadFont(fontResId: Int): Typeface? {
    return try {
        ResourcesCompat.getFont(this, fontResId)
    } catch (e: Exception) {
        null
    }
}