package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.webViewUrlLoad

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentWebViewUrlLoadBinding


class WebViewUrlLoadFragment : BaseFragment<FragmentWebViewUrlLoadBinding>(R.layout.fragment_web_view_url_load) {
    lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            webViewUrlLoadFragment = this@WebViewUrlLoadFragment
        }
        binding.toolbarWebview.groupToolbarSubScreenProfile.visible()
       binding.toolbarWebview.tvToolbarSubScreen.text = getString(R.string.app_name)
        binding.toolbarWebview.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        url = arguments?.getString(ApplicationConstant.URL_TO_LOAD, "https://www.google.com").toString()
        binding.webViewUrlLoad.settings.javaScriptEnabled = true
        binding.webViewUrlLoad.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(WebViewUrlLoadFragment::class.simpleName, "onPageFinished: ")

            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Log.d(WebViewUrlLoadFragment::class.simpleName, "onPageCommitVisible: ")
                //ProgressBar.hideProgressBar()
            }
        }
        binding.webViewUrlLoad.loadUrl(url)
    }
    }

