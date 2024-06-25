package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.qibla.qiblacompass.prayertime.finddirection.R

object AdUtil {

    private const val TAG = "AdUtil"
    private lateinit var adView: AdView
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun initialize(context: Context, adView: AdView) {
        this.adView = adView
        remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // Fetch every hour
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_default_config)

        fetchAndActivateRemoteConfig(context)
    }

    private fun fetchAndActivateRemoteConfig(context: Context) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val adUnitId = remoteConfig.getString("banner_ad_unit_id")
                    Log.d(TAG, "Fetched ad unit ID: $adUnitId")
                    setupAdView(adUnitId)
                } else {
                    Log.e(TAG, "Fetch failed", task.exception)
                    val defaultAdUnitId = context.getString(R.string.banner_ad_unit_id)
                    setupAdView(defaultAdUnitId)
                }
            }
    }

    private fun setupAdView(adUnitId: String) {
        if (adView.adUnitId.isNullOrEmpty()) {
            adView.adUnitId = adUnitId
        }

//        if (adView.adSize == null) {
//            adView.adSize = AdSize.BANNER
//        }

        loadBannerAd()
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Log.d(TAG, "Ad clicked")
            }

            override fun onAdClosed() {
                Log.d(TAG, "Ad closed")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.d(TAG, "Ad failed to load: ${error.message}")
            }

            override fun onAdImpression() {
                Log.d(TAG, "Ad impression")
            }

            override fun onAdLoaded() {
                Log.d(TAG, "Ad loaded successfully")
            }

            override fun onAdOpened() {
                Log.d(TAG, "Ad opened")
            }

            override fun onAdSwipeGestureClicked() {
                Log.d(TAG, "Ad swipe gesture clicked")
            }
        }
    }
}