package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.livestreaming

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PopUpDialog
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.MAKKAH_LIVE_URL1
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentMakkahLiveBinding


class MakkahLiveFragment : BaseFragment<FragmentMakkahLiveBinding>(R.layout.fragment_makkah_live) {
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    // [START get_remote_config_instance]
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    var makkahLiveUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            makkahLiveFragment = this@MakkahLiveFragment
        }
        callFirebaseRemoteConfig()
        binding.toolbarMakkahLive.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarMakkahLive.tvToolbarSubScreen.text = getString(R.string.makkah_live)
        webView = binding.webView
        progressBar = binding.progressBar
        binding.toolbarMakkahLive.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }

        binding.viewMainFirst.setOnClickListener {
            configureWebViewForAutoPlay(
                webView,
                makkahLiveUrl
            )
        }
        binding.viewMainTwo.setOnClickListener {
            configureWebViewForAutoPlay(
                webView,
                makkahLiveUrl
            )
        }
        binding.viewMainThree.setOnClickListener {
            configureWebViewForAutoPlay(
                webView,
                makkahLiveUrl
            )

        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                PopUpDialog(
                    getString(R.string.warning),
                    getString(R.string.exit),
                    ok_btn_callback(),
                    R.drawable.ic_warning,
                    true
                ).show(requireActivity().supportFragmentManager, "")
            }
        })
    }

    private fun callFirebaseRemoteConfig() {
        Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ")
        // [START fetch_config_with_callback]
        remoteConfig.fetchAndActivate().addOnFailureListener {
            Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ${it.message}")
            FirebaseCrashlytics.getInstance().log(it.message.toString())
        }.addOnCompleteListener {
            if(it.isSuccessful){
                Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: ")
                fetchUrl()
            }else{
                Log.d(MakkahLiveFragment::class.simpleName, "callFirebaseRemoteConfig: Not Fetched any value")
                FirebaseCrashlytics.getInstance().log(it.toString())
            }

        }
        // [END fetch_config_with_callback]
    }

    private fun fetchUrl() {
        Log.d(MakkahLiveFragment::class.simpleName, "fetchUrl: ")
        val remoteConfig = Firebase.remoteConfig

        // [START get_config_values]
         makkahLiveUrl = remoteConfig[MAKKAH_LIVE_URL1].asString()
        Log.d(MakkahLiveFragment::class.simpleName, "fetchUrl: $makkahLiveUrl")
        if(makkahLiveUrl!="") {
            configureWebViewForAutoPlay(
                webView,
                makkahLiveUrl
            )
        }else{
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

    fun ok_btn_callback(): (String) -> Unit {
        return {
            Log.d("MakkahLiveFragment"::class.simpleName, "ok_btn_callback: ")
            findNavController().closeCurrentScreen()
        }
    }

}


//        webView.settings.javaScriptEnabled = true
//
//        webView.webChromeClient = object : WebChromeClient() {
//            // Handle full-screen video playback here if needed
//        }
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                view?.loadUrl("https://www.youtube-nocookie.com/embed/xZtG7Bn2B5c?autoplay=1&playsinline=1")
//                return true
//            }
//        }
//
//        // Load the HTML content with an embedded video
//        val videoHtml =
//            "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/VIDEO_ID\" frameborder=\"0\" allowfullscreen></iframe>"
//        webView.loadData(videoHtml, "text/html", "UTF-8")
//
//

//         //Set up the WebChromeClient to enable video playback and track loading progress
//        webView.webChromeClient = object : WebChromeClient() {
//            override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                super.onProgressChanged(view, newProgress)
//                if (newProgress == 100) {
//                    // Hide the progress bar when loading is complete
//                    progressBar.visibility = View.GONE
//                } else {
//                    // Show the progress bar and update its progress
//                    progressBar.visibility = View.VISIBLE
//                    progressBar.progress = newProgress
//                }
//            }
//        }
//        // Set up the WebViewClient to handle page navigation
//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                // Hide the progress bar when the page is fully loaded
//                progressBar.visibility = View.GONE
//            }
//        }
//        initializeWebView()
//    }
//
//    private fun loadUrl(url: String) {
//        // Assuming you have a customized WebView with the id "customWebView" in your layout
//
//        webView.settings.javaScriptEnabled = true
//        // Add any other customizations to your WebView as needed
//        webView.loadUrl(url)
//    }
//

//
//    private fun initializeWebView() {
//
//        webView.settings.apply {
//            javaScriptEnabled = true
//            domStorageEnabled = true
//            mediaPlaybackRequiresUserGesture = false // Allow autoplay
//            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
//        }
//        //  val webSettings: WebSettings = webView.settings
//        //  webSettings.javaScriptEnabled = true
//        webView.webChromeClient = WebChromeClient()
//
//        val videoUrl = "https://www.youtube.com/embed/xZtG7Bn2B5c"
//
//        val html =
//            "<html><body><iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
//
//        webView.loadData(html, "text/html", "utf-8")
//    }

//}