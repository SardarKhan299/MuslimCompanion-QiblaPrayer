package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.nextprayertime

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.*
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNextPrayerTimeBinding

class NextPrayerTimeFragment :
    BaseFragment<FragmentNextPrayerTimeBinding>(R.layout.fragment_next_prayer_time) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            nextPrayerTimeFragment = this@NextPrayerTimeFragment
        }
        binding.toolbarNextPrayerTiming.groupToolbarNextPrayer.visibility = View.VISIBLE
        binding.toolbarNextPrayerTiming.viewNextPrayerIcon.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        updateBackgroundColor()
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val zuharView = commonTerm.viewZuhar
        val asrView = commonTerm.viewAsr
        val tahajjudView = commonTerm.viewTahajjud
        val maghribView = commonTerm.viewMaghrib
        val ishaView = commonTerm.viewIsha
        val layoutPrayerMainBackground = commonTerm.bgFragment

        binding.layoutNextPrayerBackground.viewFajr.setOnClickListener {
            fajrBg()
        }

        zuharView.setOnClickListener {
            zuhrBg()
        }
        asrView.setOnClickListener {
            asarBg()
        }
        maghribView.setOnClickListener {
            maghribBg()
        }
        ishaView.setOnClickListener {
            ishaBg()
        }
        tahajjudView.setOnClickListener {
            tahajjudBg()
        }
        // Retrieve the selected prayer position from SharedPreferences
        // Change the background color of the corresponding view based on the selected position
        when (SharedPreferences.getSelectedPrayerPosition(mContext)) {
            1 -> fajrBg()
            2 -> zuhrBg()
            3 -> asarBg()
            4 -> maghribBg()
            5 -> ishaBg()
            6 -> tahajjudBg()
        }

    }

    private fun fajrBg() {

        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val layoutPrayerMainBackground = commonTerm.bgFragment

        val fajrView = commonTerm.viewFajr
        val fajrPrayerText = commonTerm.tvFajr
        val fajrPrayerTime = commonTerm.tvTimeFajr
        val fajrNotificationIcon = commonTerm.imgFajrNotification
        resetAllViewsStyle()
        fajrView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            fajrView,
            fajrPrayerText,
            fajrPrayerTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Fajr,
            fajrNotificationIcon,
            layoutPrayerMainBackground
        )
    }

    private fun zuhrBg() {
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val layoutPrayerMainBackground = commonTerm.bgFragment
        val zuharText = commonTerm.tvZuhar
        val zuharTime = commonTerm.tvUnselectedZuharTime
        val zuharView = commonTerm.viewZuhar
        val zuharNotificationIcon = commonTerm.imgUnselectedZuharIcon
        resetAllViewsStyle()
        zuharView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            zuharView,
            zuharText,
            zuharTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Zuhar,
            zuharNotificationIcon,
            layoutPrayerMainBackground
        )
    }

    private fun asarBg() {
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val layoutPrayerMainBackground = commonTerm.bgFragment
        val asrText = commonTerm.tvAsr
        val asrView = commonTerm.viewAsr
        val asrTime = commonTerm.tvUnselectedAsrTime
        val asrNotificationBell = commonTerm.imgUnselectedAsrIcon
        resetAllViewsStyle()
        asrView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            asrView,
            asrText,
            asrTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Asr,
            asrNotificationBell,
            layoutPrayerMainBackground
        )
    }

    private fun maghribBg() {
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val maghribText = commonTerm.tvMaghribPrayer
        val maghribTime = commonTerm.tvUnselectedMaghribTime
        val maghribNotificationBell = commonTerm.imgUnselectedMaghribIcon
        val maghribView = commonTerm.viewMaghrib
        val layoutPrayerMainBackground = commonTerm.bgFragment
        resetAllViewsStyle()
        maghribView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            maghribView,
            maghribText,
            maghribTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Maghrib,
            maghribNotificationBell,
            layoutPrayerMainBackground
        )
    }

    private fun ishaBg() {
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val layoutPrayerMainBackground = commonTerm.bgFragment
        val ishaText = commonTerm.tvIshaPrayer
        val ishaTime = commonTerm.tvUnselectedIshaTime
        val ishaNotificationIcon = commonTerm.imgUnselectedIshaIcon
        val ishaView = commonTerm.viewIsha
        resetAllViewsStyle()
        ishaView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            ishaView,
            ishaText,
            ishaTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Isha,
            ishaNotificationIcon,
            layoutPrayerMainBackground
        )
    }

    private fun tahajjudBg() {
        val toolbarNotificationIcon = binding.toolbarNextPrayerTiming.viewBellNotificationIcon
        val toolbarCloseIcon = binding.toolbarNextPrayerTiming.viewNextPrayerIcon
        val mainLayoutBackground = binding.layoutNextPrayerFragment
        val commonTerm = binding.layoutNextPrayerBackground
        val layoutPrayerMainBackground = commonTerm.bgFragment
        val tahajjudText = commonTerm.tvTahajjudPrayer
        val tahajjudTime = commonTerm.tvUnselectedTahajjudTime
        val tahajjudNotificationIcon = commonTerm.imgUnselectedTahajjudIcon
        val tahajjudView = commonTerm.viewTahajjud

        resetAllViewsStyle()
        tahajjudView.changeAppearanceForPrayerType(
            mainLayoutBackground,
            tahajjudView,
            tahajjudText,
            tahajjudTime,
            toolbarCloseIcon,
            toolbarNotificationIcon,
            PrayerType.Tahajjud,
            tahajjudNotificationIcon,
            layoutPrayerMainBackground
        )

    }

    private fun updateBackgroundColor() {
        val selectedPrayerType = arguments?.getString("selectedPrayerType")

        when (selectedPrayerType) {
            PrayerConstants.FAJR -> fajrBg()
            PrayerConstants.ZUHAR -> zuhrBg()
            PrayerConstants.ASR -> asarBg()
            PrayerConstants.MAGHRIB -> maghribBg()
            PrayerConstants.ISHA -> ishaBg()
            PrayerConstants.TAHAJJUD -> tahajjudBg()
        }

    }

    private fun resetAllViewsStyle() {
        // Reset the style of all views here
        resetViewStyle(binding.layoutNextPrayerBackground.viewFajr)
        resetViewStyle(binding.layoutNextPrayerBackground.viewZuhar)
        resetViewStyle(binding.layoutNextPrayerBackground.viewAsr)
        resetViewStyle(binding.layoutNextPrayerBackground.viewMaghrib)
        resetViewStyle(binding.layoutNextPrayerBackground.viewIsha)
        resetViewStyle(binding.layoutNextPrayerBackground.viewTahajjud)
    }

    private fun resetViewStyle(view: View) {
        // Reset the style of the given view to its original state
        // You can use the code from your original function to do this
        val resources = resources // Assuming this is accessible in your context
        when (view) {
            binding.layoutNextPrayerBackground.viewFajr -> {
                binding.layoutNextPrayerBackground.viewFajr.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.background_transparent, null)
                binding.layoutNextPrayerBackground.tvFajr.setTextAppearance(R.style.next_prayer_unselected_main_heading_style)
                binding.layoutNextPrayerBackground.tvTimeFajr.setTextAppearance(R.style.next_prayer_unselected_main_heading_style)
                binding.layoutNextPrayerBackground.imgFajrNotification.setBackgroundResource(R.drawable.ic_unselected_bell_icon)
            }
            binding.layoutNextPrayerBackground.viewZuhar -> {
                // Reset the style of the Zuhar view
                // Use the code for resetting the Zuhar view style here
                view.viewChangingDone(
                    binding.layoutNextPrayerBackground.viewZuhar,
                    binding.layoutNextPrayerBackground.tvZuhar,
                    binding.layoutNextPrayerBackground.tvUnselectedZuharTime,
                    binding.layoutNextPrayerBackground.imgUnselectedZuharIcon
                )
            }
            binding.layoutNextPrayerBackground.viewAsr -> {
                // Reset the style of the Asr view
                // Use the code for resetting the Asr view style here
                view.viewChangingDone(
                    binding.layoutNextPrayerBackground.viewAsr,
                    binding.layoutNextPrayerBackground.tvAsr,
                    binding.layoutNextPrayerBackground.tvUnselectedAsrTime,
                    binding.layoutNextPrayerBackground.imgUnselectedAsrIcon
                )

            }
            binding.layoutNextPrayerBackground.viewMaghrib -> {
                // Reset the style of the Asr view
                // Use the code for resetting the Asr view style here
                view.viewChangingDone(
                    binding.layoutNextPrayerBackground.viewMaghrib,
                    binding.layoutNextPrayerBackground.tvMaghribPrayer,
                    binding.layoutNextPrayerBackground.tvUnselectedMaghribTime,
                    binding.layoutNextPrayerBackground.imgUnselectedMaghribIcon
                )
            }
            binding.layoutNextPrayerBackground.viewIsha -> {
                // Reset the style of the Asr view
                // Use the code for resetting the Asr view style here
                view.viewChangingDone(
                    binding.layoutNextPrayerBackground.viewIsha,
                    binding.layoutNextPrayerBackground.tvIshaPrayer,
                    binding.layoutNextPrayerBackground.tvUnselectedIshaTime,
                    binding.layoutNextPrayerBackground.imgUnselectedIshaIcon
                )
            }
            binding.layoutNextPrayerBackground.viewTahajjud -> {
                // Reset the style of the Asr view
                // Use the code for resetting the Asr view style here
                view.viewChangingDone(
                    binding.layoutNextPrayerBackground.viewTahajjud,
                    binding.layoutNextPrayerBackground.tvTahajjudPrayer,
                    binding.layoutNextPrayerBackground.tvUnselectedTahajjudTime,
                    binding.layoutNextPrayerBackground.imgUnselectedTahajjudIcon
                )
            }
            else -> {}
        }
    }

    private fun View.viewChangingDone(
        viewbg: View, PrayerText: TextView,
        PrayerTime: TextView, bellIcon: ImageView,
    ) {
        viewbg.background =
            ResourcesCompat.getDrawable(resources, R.drawable.background_transparent, null)
        PrayerTime.setTextAppearance(R.style.next_prayer_unselected_main_heading_style)
        PrayerText.setTextAppearance(R.style.next_prayer_unselected_main_heading_style)
        bellIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_unselected_bell_icon, null)

    }
}

