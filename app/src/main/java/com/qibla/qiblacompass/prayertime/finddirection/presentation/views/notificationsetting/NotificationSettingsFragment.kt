package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.notificationsetting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.AdUtil
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNotificationSettingsBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu.SideMenuFragment


class NotificationSettingsFragment :
    BaseFragment<FragmentNotificationSettingsBinding>(R.layout.fragment_notification_settings) {
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(NotificationSettingsFragment::class.java.simpleName, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(NotificationSettingsFragment::class.java.simpleName, "onViewCreated: ")
        binding.apply {
            notificationSettingsFragment = this@NotificationSettingsFragment
        }
        binding.toolbarNotificationSettings.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarNotificationSettings.tvToolbarSubScreen.text =
            getString(R.string.notifications)
        binding.toolbarNotificationSettings.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        MobileAds.initialize(mContext) {
            Log.d(
                DashBoardFragment::class.java.simpleName,
                "onViewCreated: onInitializationCompleted"
            )
        }
        adView = binding.adsBannerNotification
        // Initialize AdUtil and load the banner ad
        AdUtil.initialize(requireContext(), adView)
    }
    override fun onResume() {
        adView.resume()
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
companion object{
    private val TAG = "NotificationSettingsFragment"
}
}