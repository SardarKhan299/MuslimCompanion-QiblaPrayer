package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
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
        binding.toolbarAboutUs.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }

    }

    fun gotoUpdateAccountSettings() {
        findNavController().navigate(R.id.accountSettingsFragment)

    }
}