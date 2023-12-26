package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.livestreaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentMakkahLiveBinding


class MakkahLiveFragment : BaseFragment<FragmentMakkahLiveBinding>(R.layout.fragment_makkah_live) {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            makkahLiveFragment = this@MakkahLiveFragment
        }
        binding.toolbarMakkahLive.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarMakkahLive.tvToolbarSubScreen.text = getString(R.string.makkah_live)
        webView = binding.webView
        binding.toolbarMakkahLive.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        binding.webView.loadUrl("https://www.youtube.com/embed/xZtG7Bn2B5c")
        binding.imgMakkahViewOne.setOnClickListener {
            loadUrl("https://www.youtube.com/embed/xZtG7Bn2B5c")
        }
        binding.imgMakkahViewTwo.setOnClickListener {
            loadUrl("https://www.youtube.com/embed/xZtG7Bn2B5c")
        }
        binding.imgMakkahViewThree.setOnClickListener {
            loadUrl("https://youtu.be/PEmRPDJ9I8M")

        }
        // initializeWebView()
    }

    private fun loadUrl(url: String) {
        // Assuming you have a customized WebView with the id "customWebView" in your layout

        webView.settings.javaScriptEnabled = true
        // Add any other customizations to your WebView as needed
        webView.loadUrl(url)
    }

    private fun initializeWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webChromeClient = WebChromeClient()

        val videoUrl = "https://www.youtube.com/embed/xZtG7Bn2B5c"
        // val videoUrl = "https://www.youtube.com/embed/xZtG7Bn2B5c"
        // val videoUrl = "   https://makkahlive.net/tvcamera.aspx"
        //val videoUrl = "https://youtu.be/PEmRPDJ9I8M"

        val html =
            "<html><body><iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

        webView.loadData(html, "text/html", "utf-8")
    }

}