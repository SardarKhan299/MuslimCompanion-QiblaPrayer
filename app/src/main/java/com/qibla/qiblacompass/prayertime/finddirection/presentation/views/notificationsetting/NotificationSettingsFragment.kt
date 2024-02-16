package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.notificationsetting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNotificationSettingsBinding


class NotificationSettingsFragment :
    BaseFragment<FragmentNotificationSettingsBinding>(R.layout.fragment_notification_settings) {

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
    }

}