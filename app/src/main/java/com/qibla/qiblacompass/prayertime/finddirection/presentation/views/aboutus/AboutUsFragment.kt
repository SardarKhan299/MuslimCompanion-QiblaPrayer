package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.aboutus

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.BuildConfig
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAboutUsBinding


class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>(R.layout.fragment_about_us) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            aboutUsFragment = this@AboutUsFragment
        }
        binding.toolbarAboutUs.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarAboutUs.tvToolbarSubScreen.text = getString(R.string.about_us)
        binding.tvVersion.text = BuildConfig.VERSION_NAME +"_"+BuildConfig.VERSION_CODE
        binding.toolbarAboutUs.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        binding.viewAboutUsFacebook.setOnClickListener {
            loadFacebookUrl()
        }
        binding.viewAboutUsLinkedIn.setOnClickListener {
            loadLinkedInUrl()
        }
        binding.viewAboutUsTwitter.setOnClickListener {
            loadTwitterUrl()
        }
    }

    fun gotoPrivacy() {
        findNavController().navigate(R.id.privacyFragment)

    }

    private fun loadLinkedInUrl() {
        Log.d(AboutUsFragment::class.simpleName, "loadLinkedInUrl: ")
        val bundle = Bundle()
        bundle.putString(ApplicationConstant.URL_TO_LOAD, "https://www.linkedin.com")
        findNavController().navigate(R.id.webViewUrlLoadFragment, bundle)
    }

    private fun loadFacebookUrl() {
        Log.d(AboutUsFragment::class.simpleName, "loadFacebookUrl: ")
        val bundle = Bundle()
        bundle.putString(ApplicationConstant.URL_TO_LOAD,  "https://www.facebook.com/Stellatechnologypk")
        findNavController().navigate(R.id.webViewUrlLoadFragment, bundle)
    }
    private fun loadTwitterUrl() {
        Log.d(AboutUsFragment::class.simpleName, "loadTwitterUrl: ")
        val bundle = Bundle()
        bundle.putString(ApplicationConstant.URL_TO_LOAD, "https://twitter.com/?lang=en")
        findNavController().navigate(R.id.webViewUrlLoadFragment, bundle)
    }
}