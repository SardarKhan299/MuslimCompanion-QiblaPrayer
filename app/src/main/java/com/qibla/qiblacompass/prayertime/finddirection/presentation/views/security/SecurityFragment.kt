package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.security

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.gone
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSecurityBinding


class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            securityFragment = this@SecurityFragment
        }
        // binding.groupSecurityResetPassword.gone()

        binding.viewResetPassword.setOnClickListener {
            if (binding.groupSecurityResetPassword.visibility == View.VISIBLE) {
                binding.groupSecurityResetPassword.gone()
            } else if (binding.groupSecurityResetPassword.visibility == View.GONE) {
                binding.groupSecurityResetPassword.visible()

            }
        }
    }

}