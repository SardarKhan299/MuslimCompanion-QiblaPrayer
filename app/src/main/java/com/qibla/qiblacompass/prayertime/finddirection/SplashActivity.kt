package com.qibla.qiblacompass.prayertime.finddirection

import android.content.Intent
import android.os.Bundle
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseActivity
import com.qibla.qiblacompass.prayertime.finddirection.views.onboarding.OnboardingActivity
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer("splash", false).schedule(3000) {
            startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

}