package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.privacy

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.qibla.qiblacompass.prayertime.finddirection.BuildConfig
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants
import com.qibla.qiblacompass.prayertime.finddirection.common.ProgressBar
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentPrivacyBinding


class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>(R.layout.fragment_privacy) {
    lateinit var webView: WebView
    var privacyUrl = ""
    private lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            privacyFragment = this@PrivacyFragment
        }
        callFirebaseRemoteConfig()
        binding.toolbarPrivacyPolicy.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarPrivacyPolicy.tvToolbarSubScreen.text = getString(R.string.privacy)
        webView = binding.webViewPrivacy

        binding.toolbarPrivacyPolicy.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }

        com.qibla.qiblacompass.prayertime.finddirection.common.ProgressBar.showProgressBar(mContext,getString(R.string.please_wait))
    }

    private fun callFirebaseRemoteConfig() {
        Log.d(PrivacyFragment::class.simpleName, "callFirebaseRemoteConfig: ")

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0 // fetch every time in debug mode
            } else {
                300
            }
        }

        // Initialize Remote Config default values.
        val defaults = mutableMapOf<String, Any>(
            PrayerConstants.MAKKAH_LIVE_URL1 to "https://www.youtube-nocookie.com/embed/xZtG7Bn2B5c?autoplay=1&playsinline=1",
            PrayerConstants.PRIVACY_URL to "https://www.stellatechnology.com/"
        )
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(defaults)
        }

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d(PrivacyFragment::class.simpleName, "Updated keys: " + configUpdate.updatedKeys)

                if (configUpdate.updatedKeys.contains(PrayerConstants.PRIVACY_URL)) {
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d(PrivacyFragment::class.simpleName, "onUpdate: Url Found...")
                        fetchUrl()
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(PrivacyFragment::class.simpleName, "Config update error with code: " + error.code, error)
            }
        })

        fetchUrl()
    }

    private fun fetchUrl() {
        Log.d(PrivacyFragment::class.simpleName, "fetchUrl: ")

        remoteConfig.fetchAndActivate().addOnFailureListener {
            Log.d(PrivacyFragment::class.simpleName, "callFirebaseRemoteConfig: ${it.message}")
            FirebaseCrashlytics.getInstance().log(it.message.toString())
        }.addOnCompleteListener {
            if(it.isSuccessful){
                Log.d(PrivacyFragment::class.simpleName, "callFirebaseRemoteConfig: ")
                loadUrlInWebview()
            }else{
                Log.d(PrivacyFragment::class.simpleName, "callFirebaseRemoteConfig: Not Fetched any value")
                FirebaseCrashlytics.getInstance().log(it.toString())
            }

        }

    }

    private fun loadUrlInWebview() {
        Log.d(PrivacyFragment::class.simpleName, "loadUrlInWebview: ")
        privacyUrl = remoteConfig[PrayerConstants.PRIVACY_URL].asString()
        Log.d(PrivacyFragment::class.simpleName, "loadUrlInWebview: $privacyUrl")
        if(privacyUrl!="") {
            configureWebViewForAutoPlay(
                webView,
                privacyUrl
            )
        }else{
            Log.d(PrivacyFragment::class.simpleName, "loadUrlInWebview: Url is Empty")
            FirebaseCrashlytics.getInstance().log("Remote Config Url is Empty")
        }
    }

    private fun configureWebViewForAutoPlay(webView: WebView, url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.pluginState = WebSettings.PluginState.ON

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(PrivacyFragment::class.simpleName, "onPageFinished: ")
                ProgressBar.hideProgressBar()
            //webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d(PrivacyFragment::class.simpleName, "onPageStarted: ")
                ProgressBar.hideProgressBar()
            }
        }

        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(url)
    }
}