package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseActivity
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginActivity

class OnboardingActivity : BaseActivity() {
    var indicator = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        findViewById<TextView>(R.id.tv_skip).setOnClickListener {
            Log.d(OnboardingActivity::class.simpleName, "onCreate: ")
            gotoLoginActivity()
        }
        findViewById<Button>(R.id.btn_next).setOnClickListener {
            if(indicator==3){
                gotoLoginActivity()
            }
            indicator++
            showNextScreenValues(indicator)
        }
    }

    private fun showNextScreenValues(indicator: Int) {
        Log.d(OnboardingActivity::class.simpleName, "showNextScreenValues: $indicator")
        when(indicator){
            2->{
                findViewById<ImageView>(R.id.iv_onboard).setImageDrawable(ContextCompat.getDrawable(this,R.drawable.img_pray))
                findViewById<TextView>(R.id.tv_onboard_title).text = getString(R.string.prayer_timing)
                findViewById<TextView>(R.id.tv_onboard_desc).text = getString(R.string.test)
                findViewById<ImageView>(R.id.iv_onboard_indicator).setImageResource(R.drawable.indicator2)
            }
            3->{
                findViewById<ImageView>(R.id.iv_onboard).setImageDrawable(ContextCompat.getDrawable(this,R.drawable.img_holy_quran))
                findViewById<TextView>(R.id.tv_onboard_title).text = getString(R.string.holy_quran)
                findViewById<TextView>(R.id.tv_onboard_desc).text = getString(R.string.test)
                findViewById<ImageView>(R.id.iv_onboard_indicator).setImageResource(R.drawable.indicator3)
            }
            else->{
            Log.d(OnboardingActivity::class.simpleName, "showNextScreenValues: Value Not Matched...")
            }
        }
    }

    private fun gotoLoginActivity() {
        startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}