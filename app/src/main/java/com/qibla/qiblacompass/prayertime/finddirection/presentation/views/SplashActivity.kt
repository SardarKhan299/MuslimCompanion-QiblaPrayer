package com.qibla.qiblacompass.prayertime.finddirection.presentation.views

import android.content.Intent
import android.os.Bundle
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseActivity
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.SPLASH_VISIT_TIME
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding.OnboardingActivity
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        FirebaseCrashlytics.getInstance().log("Test Log. Splash..")
        Timer("splash", false).schedule(SPLASH_VISIT_TIME) {
            if(SharedPreferences.isFirstTimeLogin(this@SplashActivity)){
                startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }else{
                //startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

        }
    }

}