package com.qibla.qiblacompass.prayertime.finddirection.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.SplashActivity

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        findViewById<TextView>(R.id.tv_skip).setOnClickListener {
            Log.d(OnboardingActivity::class.simpleName, "onCreate: ")
            gotoLoginActivity()
        }
    }

    private fun gotoLoginActivity() {
        startActivity(Intent(this@OnboardingActivity, SplashActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}