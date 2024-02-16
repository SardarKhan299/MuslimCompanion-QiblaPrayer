package com.qibla.qiblacompass.prayertime.finddirection.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import com.qibla.qiblacompass.prayertime.finddirection.R

fun AppCompatActivity.hideActionBar() {
    supportActionBar?.hide()
}

fun NavController.closeCurrentScreen() {
    this.popBackStack()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


//// Create an extension function for changing the view's appearance based on the prayer type
fun View.changeAppearanceForPrayerType(
    mainViewBackground: ConstraintLayout,
    viewBackground: View,
    PrayerText: TextView,
    PrayerTime: TextView,
    toolbarCloseIcon: View,
    toolbarBellIcon: View,
    prayerType: PrayerType,
    bellIcon: ImageView,
    layoutView: ConstraintLayout

) {
    val resources = resources // Assuming this is accessible in your context

    when (prayerType) {
        PrayerType.Fajr -> {
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.fajr_prayer_screen, null)
            viewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.selected_prayer_time_bg, null)
            layoutView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.next_prayer_fajr_bg, null)
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_next_prayer_bg, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_next_prayer_bg, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)


        }
        PrayerType.Zuhar -> {
            setBackgroundResource(R.drawable.selected_zuhar_prayer_time_bg)
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.zuhr_prayer_screen, null)
            viewBackground.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.selected_zuhar_prayer_time_bg,
                null
            )
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_zuhar_background, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_zuhar_background, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)
            layoutView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.prayer_zuhar_drawable_bg, null)
        }
        PrayerType.Asr -> {
            setBackgroundResource(R.drawable.selected_asr_prayer_time_bg)
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.asr_prayer_screen, null)
            viewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.selected_asr_prayer_time_bg, null)
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_asr_background, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_asr_background, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)
            layoutView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.background_main_view_asr, null)
        }
        PrayerType.Maghrib -> {
            setBackgroundResource(R.drawable.selected_maghrib_prayer_time_bg)
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.maghrib_prayer_screen, null)
            viewBackground.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.selected_maghrib_prayer_time_bg,
                null
            )
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_maghrib_background, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_maghrib_background, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)
            layoutView.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.background_main_view_maghrib,
                null
            )
        }
        PrayerType.Isha -> {
            setBackgroundResource(R.drawable.selected_isha_prayer_time_bg)
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.tahajjud_screen, null)
            viewBackground.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.selected_isha_prayer_time_bg,
                null
            )
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_isha_background, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_isha_background, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)
            layoutView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.background_main_view_isha, null)
        }
        PrayerType.Tahajjud -> {
            setBackgroundResource(R.drawable.selected_tahajjud_prayer_time_bg)
            mainViewBackground.background =
                ResourcesCompat.getDrawable(resources, R.drawable.tahajjud_screen, null)
            viewBackground.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.selected_tahajjud_prayer_time_bg,
                null
            )
            PrayerTime.setTextAppearance(R.style.next_prayer_main_heading_style)
            PrayerText.setTextAppearance(R.style.next_prayer_main_heading_style)
            toolbarCloseIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_tahajjud_background, null)
            toolbarBellIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.toolbar_tahajjud_background, null)
            bellIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_on_bell, null)
            layoutView.background =
                ResourcesCompat.getDrawable(resources, R.drawable.background_main_view_isha, null)
        }
        // Add more cases for other prayer types as needed
        else -> {}
    }
}

// Create an extension function for changing text appearance
fun TextView.changeTextAppearance(textAppearanceResId: Int) {
    setTextAppearance(textAppearanceResId)
}

// Create an extension function for changing the bell icon
fun ImageView.changeBellIcon(bellIconResId: Int) {
    setImageResource(bellIconResId)
}

fun View.changeBackgroundViewColor(backgroundColor: Int) {
    setBackgroundColor(backgroundColor)
}

fun View.changeDrawable(drawableChange: Int) {
    setBackgroundResource(drawableChange)
}


enum class PrayerType {
    Fajr,
    Zuhar,
    Asr,
    Maghrib,
    Isha,
    Tahajjud
}
