package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.privacy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentPrivacyBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.livestreaming.MakkahLiveFragment


class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>(R.layout.fragment_privacy) {
    lateinit var webView: WebView

    // [START get_remote_config_instance]
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    var privacyUrl = ""
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
    }

    private fun callFirebaseRemoteConfig() {
        Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ")
        // [START fetch_config_with_callback]
        remoteConfig.fetchAndActivate().addOnFailureListener {
            Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ${it.message}")
            FirebaseCrashlytics.getInstance().log(it.message.toString())
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ")
                fetchUrl()
            } else {
                Log.d(
                    MakkahLiveFragment::class.simpleName,
                    "callFirebaseRemoteConfig: Not Fetched any value"
                )
                FirebaseCrashlytics.getInstance().log(it.toString())
            }

        }
        // [END fetch_config_with_callback]
    }

    private fun fetchUrl() {
        Log.d(MakkahLiveFragment::class.simpleName, "fetchUrl: ")
        val remoteConfig = Firebase.remoteConfig

        // [START get_config_values]
        privacyUrl = remoteConfig[PrayerConstants.MAKKAH_LIVE_URL1].asString()
        Log.d(MakkahLiveFragment::class.simpleName, "fetchUrl: $privacyUrl")
        if (privacyUrl != "") {
            configureWebViewForAutoPlay(
                webView,
                privacyUrl
            )
        } else {
            Log.d(MakkahLiveFragment::class.simpleName, "fetchUrl: Url is Empty")
            FirebaseCrashlytics.getInstance().log("Remote Config Url is Empty")
        }

    }

    private fun configureWebViewForAutoPlay(webView: WebView, url: String) {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.pluginState = WebSettings.PluginState.ON

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
            }
        }

        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(url)
    }
}