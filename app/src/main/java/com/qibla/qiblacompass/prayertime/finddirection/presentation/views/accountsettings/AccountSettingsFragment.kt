package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.accountsettings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAccountSettingsBinding


class AccountSettingsFragment :
    BaseFragment<FragmentAccountSettingsBinding>(R.layout.fragment_account_settings) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            accountSettingsFragment = this@AccountSettingsFragment
        }
        binding.toolbarAccountSettings.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarAccountSettings.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }

    }
    fun gotoSecurityScreen(){
        findNavController().navigate(R.id.securityFragment)
    }
}