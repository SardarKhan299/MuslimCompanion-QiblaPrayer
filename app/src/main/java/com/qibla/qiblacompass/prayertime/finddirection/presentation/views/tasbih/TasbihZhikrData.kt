package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.graphics.drawable.Drawable

data class TasbihZhikrData(
    val tvZhikr:String,
    val imgZhikrResId: Int? = null, // Resource ID for the image
    val imgZhikrDrawable: Drawable? = null // Drawable object for the image
)