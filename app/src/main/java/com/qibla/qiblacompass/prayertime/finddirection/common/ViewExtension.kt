package com.qibla.qiblacompass.prayertime.finddirection.common

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

fun AppCompatActivity.hideActionBar(){
    supportActionBar?.hide()
}
fun NavController.closeCurrentScreen(){
    this.popBackStack()
}
fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}