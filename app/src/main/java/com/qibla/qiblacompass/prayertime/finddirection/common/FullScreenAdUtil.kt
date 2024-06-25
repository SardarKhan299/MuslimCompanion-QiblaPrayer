package com.qibla.qiblacompass.prayertime.finddirection.common

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.qibla.qiblacompass.prayertime.finddirection.R

object FullScreenAdUtil {
    private const val TAG = "FullScreenAdUtil"
    private var interstitialAd: InterstitialAd? = null
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return

        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default_full_screen_ad)
            .addOnCompleteListener {
                isInitialized = true
                fetchAndLoadInterstitialAd(context)
            }
    }

    private fun fetchAndLoadInterstitialAd(context: Context) {
        if (!isInitialized) return

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                val adUnitId: String = if (task.isSuccessful) {
                    remoteConfig.getString("full_screen_ad")
                } else {
                    Log.e(TAG, "Fetch failed", task.exception)
                    context.getString(R.string.default_interstitial_ad_unit_id)
                }
                Log.d(TAG, "Ad unit ID: $adUnitId")
                loadInterstitialAd(context, adUnitId)
            }
    }

    private fun loadInterstitialAd(context: Context, adUnitId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
                Log.d(TAG, "Interstitial ad loaded")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.d(TAG, "Failed to load interstitial ad: ${error.message}")
                interstitialAd = null
            }
        })
    }

    fun showInterstitialAd(activity: Activity, onAdClosed: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad dismissed")
                    onAdClosed()
                    interstitialAd = null
                    fetchAndLoadInterstitialAd(activity) // Pre-load the next interstitial ad
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(TAG, "Failed to show interstitial ad: ${adError.message}")
                    onAdClosed()
                    interstitialAd = null
                    fetchAndLoadInterstitialAd(activity) // Pre-load the next interstitial ad
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad shown")
                }
            }
            interstitialAd?.show(activity)
        } else {
            onAdClosed()
            fetchAndLoadInterstitialAd(activity) // Pre-load the next interstitial ad
        }
    }
}