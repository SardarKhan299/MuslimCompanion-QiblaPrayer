package com.qibla.qiblacompass.prayertime.finddirection.common

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

fun AppCompatActivity.hideActionBar(){
    supportActionBar?.hide()
}
fun NavController.closeCurrentScreen(){
    this.popBackStack()
}