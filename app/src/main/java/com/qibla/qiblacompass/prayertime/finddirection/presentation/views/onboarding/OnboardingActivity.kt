package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseActivity
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginActivity

class OnboardingActivity : BaseActivity() {
    private lateinit var viewPager: ViewPager2
    private val data = ArrayList<OnboardingItem>()
    private var indicator = 0
    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewPager = findViewById(R.id.view_pager)
        btnNext = findViewById(R.id.btn_next)
        findViewById<TextView>(R.id.tv_skip).setOnClickListener {
            Log.d(OnboardingActivity::class.simpleName, "onCreate: ")
            gotoLoginActivity()
        }
        data.add(
            OnboardingItem(
                R.drawable.img_qibla,
                getString(R.string.qibla_compass),
                getString(R.string.test),
                R.drawable.idicator1
            )
        )
        data.add(
            OnboardingItem(
                R.drawable.img_prayer,
                getString(R.string.prayer_timing),
                getString(R.string.prayer_timing_detail),
                R.drawable.indicator2
            )
        )
        data.add(
            OnboardingItem(
                R.drawable.img_holy_quran,
                getString(R.string.holy_quran),
                getString(R.string.detail_text),
                R.drawable.indicator3
            )
        )

        val adapter = OnboardingPagerAdapter(data)
        viewPager.adapter = adapter

        btnNext.setOnClickListener {
            if (indicator < data.size - 1) {
                indicator++
                viewPager.currentItem = indicator
            } else {
                gotoLoginActivity()
            }
        }

    }


    private fun showNextScreenValues(indicator: Int) {
        Log.d(OnboardingActivity::class.simpleName, "showNextScreenValues: $indicator")
        when (indicator) {
            2 -> {
                findViewById<ImageView>(R.id.iv_onboard).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.img_prayer
                    )
                )
                findViewById<TextView>(R.id.tv_onboard_title).text =
                    getString(R.string.prayer_timing)
                findViewById<TextView>(R.id.tv_onboard_desc).text = getString(R.string.test)
                findViewById<ImageView>(R.id.iv_onboard_indicator).setImageResource(R.drawable.indicator2)
            }
            3 -> {
                findViewById<ImageView>(R.id.iv_onboard).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.img_holy_quran
                    )
                )
                findViewById<TextView>(R.id.tv_onboard_title).text = getString(R.string.holy_quran)
                findViewById<TextView>(R.id.tv_onboard_desc).text = getString(R.string.test)
                findViewById<ImageView>(R.id.iv_onboard_indicator).setImageResource(R.drawable.indicator3)
            }
            else -> {
                Log.d(
                    OnboardingActivity::class.simpleName,
                    "showNextScreenValues: Value Not Matched..."
                )
            }
        }
    }

    private fun gotoLoginActivity() {
        startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}

//        val data = ArrayList<OnboardingItem>()
//        data.add(OnboardingItem(R.drawable.img_qibla, "Welcome", "Welcome to the app!",R.drawable.idicator1))
//        data.add(OnboardingItem(R.drawable.img_pray, "Prayer Timing", "Discover prayer timings.",R.drawable.indicator2))
//        data.add(OnboardingItem(R.drawable.img_holy_quran, "Holy Quran", "Explore the Holy Quran.",R.drawable.indicator3))
//        val adapter = OnboardingPagerAdapter(data)
//        viewPager.adapter = adapter
//        // showNextScreenValues(indicator)
//        findViewById<Button>(R.id.btn_next).setOnClickListener {
//            val currentItem = viewPager.currentItem
//            if (currentItem < adapter.itemCount - 1 ) {
//                viewPager.setCurrentItem(currentItem + 1, true)
//
//            } else {
//                gotoLoginActivity()
//            }
//        }

//        findViewById<Button>(R.id.btn_next).setOnClickListener {
//            if (indicator == 3) {
//                gotoLoginActivity()
//            }
//            indicator++
//         //   showNextScreenValues(indicator)
//        }
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                showNextScreenValues(position + 1)
//            }
//        })
