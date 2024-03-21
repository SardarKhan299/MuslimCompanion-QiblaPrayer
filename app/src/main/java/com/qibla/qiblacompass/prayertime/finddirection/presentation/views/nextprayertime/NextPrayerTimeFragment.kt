package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.nextprayertime

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.*
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.AUDIO_ADHAN_FOUR
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.AUDIO_ADHAN_ONE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.AUDIO_ADHAN_THREE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.AUDIO_ADHAN_TWO
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.AUDIO_BEEP
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_ADHAN_FOUR
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_ADHAN_ONE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_ADHAN_THREE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_ADHAN_TWO
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_BEEP
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_NONE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.NOTIFICATION_SILENT
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.PRAYER_ASR
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.PRAYER_FAJR
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.PRAYER_ISHA
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.PRAYER_MAGHRIB
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.PRAYER_ZUHR
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.REMINDER_FIFTEEN_MINT_BEFORE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.REMINDER_FIVE_MINT_BEFORE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.REMINDER_NONE
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants.REMINDER_TEN_MINT_BEFORE
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.getPreAlertValue
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.loadNotificationStyleValue
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.loadPreAlertValue
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.saveNotificationOption
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.saveSelectedPreAlertOption
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.setViewStyleAndSaveToPrefs
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNextPrayerTimeBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NextPrayerTimeFragment :
    BaseFragment<FragmentNextPrayerTimeBinding>(R.layout.fragment_next_prayer_time) {

    private val viewModel: DashboardViewModel by activityViewModels()
    lateinit var preAlertNone: TextView
    lateinit var preAlertNoneTick: ImageView
    lateinit var preAlertFiveMins: TextView
    lateinit var preAlertFiveMinsTick: ImageView
    lateinit var preAlertTenMins: TextView
    lateinit var preAlertTenTick: ImageView
    lateinit var preAlertFifteenMins: TextView
    lateinit var preAlertFifteenTick: ImageView
    lateinit var preAdhanReminderText: TextView
    lateinit var imgFajrNotification: ImageView
    lateinit var imgZuhrFajrNotification: ImageView
    lateinit var titleBottomSheet: TextView
    private var mediaPlayer: MediaPlayer? = null
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
        binding.toolbarNextPrayerTiming.viewBellNotificationIcon.setOnClickListener {
            //showBottomSheetNotificationAlertSound()
        }
        initObserver()
        updateBackgroundColor()
        binding.tvDateToday.text = CommonMethods.getCurrentDateFormatted()
        setUserCityFromStorage()
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
        imgFajrNotification = binding.layoutNextPrayerBackground.imgFajrNotification
        imgZuhrFajrNotification = binding.layoutNextPrayerBackground.imgUnselectedZuharIcon



        binding.layoutNextPrayerBackground.viewFajr.setOnClickListener {
            showBottomSheetNotificationAlertSound(PRAYER_FAJR)
            // Update the bottom sheet title immediately
            titleBottomSheet.text = PRAYER_FAJR
            SharedPreferences.savePrayerInPrefs(mContext, PRAYER_FAJR)
            fajrBg()
        }

        zuharView.setOnClickListener {
            showBottomSheetNotificationAlertSound(PRAYER_ZUHR)
            // Update the bottom sheet title immediately
            titleBottomSheet.text = PRAYER_ZUHR
            //Save prayer name in shared preference
            SharedPreferences.savePrayerInPrefs(mContext, PRAYER_ZUHR)
            zuhrBg()
        }
        asrView.setOnClickListener {
            showBottomSheetNotificationAlertSound(PRAYER_ASR)
            // Update the bottom sheet title immediately
            titleBottomSheet.text = PRAYER_ASR
            SharedPreferences.savePrayerInPrefs(mContext, PRAYER_ASR)
            asarBg()
        }
        maghribView.setOnClickListener {
            showBottomSheetNotificationAlertSound(PRAYER_MAGHRIB)
            // Update the bottom sheet title immediately
            titleBottomSheet.text = PRAYER_MAGHRIB
            SharedPreferences.savePrayerInPrefs(mContext, PRAYER_MAGHRIB)
            maghribBg()
        }
        ishaView.setOnClickListener {
            showBottomSheetNotificationAlertSound(PRAYER_ISHA)
            // Update the bottom sheet title immediately
            titleBottomSheet.text = PRAYER_ISHA
            SharedPreferences.savePrayerInPrefs(mContext, PRAYER_ISHA)
            ishaBg()
        }
        tahajjudView.setOnClickListener {
            tahajjudBg()
        }
        // Retrieve the selected prayer position from SharedPreferences
        // Change the background color of the corresponding view based on the selected position
        val position = SharedPreferences.getSelectedPrayerPosition(mContext)
        Log.d(NextPrayerTimeFragment::class.simpleName, "onViewCreated: $position")
        when (position) {
            0 -> {
                Log.d(NextPrayerTimeFragment::class.simpleName, "onViewCreated: No option Selected")
            }

            1 -> fajrBg()
            2 -> zuhrBg()
            3 -> asarBg()
            4 -> maghribBg()
            5 -> ishaBg()
            6 -> tahajjudBg()
        }

    }

    private fun showBottomSheetNotificationAlertSound(prayerName: String) {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_sheet_notification_alert_sound, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        //none Notification views
        val noneNotificationView: View = bottomSheetView.findViewById(R.id.view_none_notification)
        val noneNotificationText: TextView = bottomSheetView.findViewById(R.id.tv_notification_none)
        val noneTickImage: ImageView = bottomSheetView.findViewById(R.id.img_none_tick)
        // silent Notification views
        val silentNotificationView: View =
            bottomSheetView.findViewById(R.id.view_notification_silent)
        val silentNotificationText: TextView =
            bottomSheetView.findViewById(R.id.tv_notification_silent)
        val silentTickImage: ImageView = bottomSheetView.findViewById(R.id.img_silent_tick)
        // beep Notification views
        val beepNotificationView: View = bottomSheetView.findViewById(R.id.view_beep_notification)
        val beepNotificationText: TextView = bottomSheetView.findViewById(R.id.tv_beep_notification)
        val beepTickImage: ImageView = bottomSheetView.findViewById(R.id.img_beep_tick)
        //Adhan One View
        val adhanOneNotificationView: View =
            bottomSheetView.findViewById(R.id.view_adhan_one_notification)
        val adhanOneNotificationText: TextView =
            bottomSheetView.findViewById(R.id.tv_title_adhan_one)
        val adhanOneTickImage: ImageView = bottomSheetView.findViewById(R.id.img_adhan_one_tick)
        val adhanOneSpeakerText: TextView = bottomSheetView.findViewById(R.id.tv_adhan_one_speaker)
        //Adhan Two View
        val adhanTwoNotificationView: View =
            bottomSheetView.findViewById(R.id.view_adhan_two_notification)
        val adhanTwoNotificationText: TextView =
            bottomSheetView.findViewById(R.id.tv_title_adhan_two)
        val adhanTwoTickImage: ImageView = bottomSheetView.findViewById(R.id.img_adhan_two_tick)
        val adhanTwoSpeakerText: TextView = bottomSheetView.findViewById(R.id.tv_adhan_two_speaker)
        //Adhan Three View
        val adhanThreeNotificationView: View =
            bottomSheetView.findViewById(R.id.view_adhan_three_notification)
        val adhanThreeNotificationText: TextView =
            bottomSheetView.findViewById(R.id.tv_title_adhan_three)
        val adhanThreeTickImage: ImageView = bottomSheetView.findViewById(R.id.img_adhan_three_tick)
        val adhanThreeSpeakerText: TextView =
            bottomSheetView.findViewById(R.id.tv_adhan_three_speaker)
        //Adhan Three View
        val adhanFourNotificationView: View =
            bottomSheetView.findViewById(R.id.view_adhan_four_notification)
        val adhanFourNotificationText: TextView =
            bottomSheetView.findViewById(R.id.tv_title_adhan_four)
        val adhanFourTickImage: ImageView = bottomSheetView.findViewById(R.id.img_adhan_four_tick)
        val adhanFourSpeakerText: TextView =
            bottomSheetView.findViewById(R.id.tv_adhan_four_speaker)
        val navigateBack: ImageView = bottomSheetView.findViewById(R.id.img_bottom_navigate_back)
        val preAlertView: View = bottomSheetView.findViewById(R.id.view_pre_reminder)
        val alertGroup: Group = bottomSheetView.findViewById(R.id.group_alert_notification)

        preAlertNone = bottomSheetView.findViewById(R.id.tv_none_alert)
        preAlertNoneTick = bottomSheetView.findViewById(R.id.img_pre_alert_none_tick)
        preAlertFiveMins = bottomSheetView.findViewById(R.id.tv_alert_five_min)
        preAlertFiveMinsTick = bottomSheetView.findViewById(R.id.img_pre_alert_five_tick)

        preAlertTenMins = bottomSheetView.findViewById(R.id.tv_alert_ten_min)
        preAlertTenTick = bottomSheetView.findViewById(R.id.img_pre_alert_ten_tick)
        preAlertFifteenMins = bottomSheetView.findViewById(R.id.tv_alert_fifteen_min)
        preAlertFifteenTick = bottomSheetView.findViewById(R.id.img_pre_alert_fifteen_tick)
        titleBottomSheet = bottomSheetView.findViewById(R.id.tv_bottom_sheet_title)


//get prayer name from shared preference
        val getTitleName = SharedPreferences.getStoredPrayerName(mContext) ?: "Default Prayer Name"
        titleBottomSheet.text = getTitleName


        val preAdhanReminderText: TextView = bottomSheetView.findViewById(R.id.tv_notification_no)

        // Retrieve saved settings for this prayer time from SharedPreferences
        val selectedNotificationOption =
            SharedPreferences.getNotificationOption(requireContext(), prayerName)
        val selectedPreAlertOption =
            SharedPreferences.getPreAdhanReminderOption(requireContext(), prayerName)
        when (selectedPreAlertOption) {
            REMINDER_NONE -> {
                preAdhanReminderText.text = getString(R.string.none)

            }

            REMINDER_FIVE_MINT_BEFORE -> {
                preAdhanReminderText.text = getString(R.string._5_mins_before)
            }

            REMINDER_TEN_MINT_BEFORE -> {
                preAdhanReminderText.text = getString(R.string._10_mins_before)
            }

            REMINDER_FIFTEEN_MINT_BEFORE -> {
                preAdhanReminderText.text = getString(R.string._15_mins_before)
            }
        }

        preAlertView.setOnClickListener {

            if (alertGroup.visibility == View.VISIBLE) {
                alertGroup.gone()
                preAlertNoneTick.gone()
                preAlertFiveMinsTick.gone()
                preAlertTenTick.gone()
                preAlertFifteenTick.gone()

            } else {
                alertGroup.visible()
                val lastSelectedValue = loadPreAlertValue(mContext)
                // Apply the last selected value to the appropriate views
                when (selectedPreAlertOption) {
                    REMINDER_NONE -> {
                        setViewStyleAlertReminder(preAlertNone, preAlertNoneTick)
                        preAlertNoneTick.visible()
                        preAdhanReminderText.text = getString(R.string.none)

                    }

                    REMINDER_FIVE_MINT_BEFORE -> {
                        setViewStyleAlertReminder(preAlertFiveMins, preAlertFiveMinsTick)
                        preAlertFiveMins.visible()
                        preAdhanReminderText.text = getString(R.string._5_mins_before)
                    }

                    REMINDER_TEN_MINT_BEFORE -> {
                        setViewStyleAlertReminder(preAlertTenMins, preAlertTenTick)
                        preAlertTenTick.visible()
                        preAdhanReminderText.text = getString(R.string._10_mins_before)
                    }

                    REMINDER_FIFTEEN_MINT_BEFORE -> {
                        setViewStyleAlertReminder(preAlertFifteenMins, preAlertFifteenTick)
                        preAlertFifteenTick.visible()
                        preAdhanReminderText.text = getString(R.string._15_mins_before)
                    }
                }
            }
        }




        if (selectedPreAlertOption.isNullOrEmpty()) {
            preAdhanReminderText.text = getString(R.string.none)
        } else {
            preAdhanReminderText.text = selectedPreAlertOption

        }

        preAlertNone.setOnClickListener {
            setViewStyleAndSaveToPrefs(mContext, preAlertNone, preAlertNoneTick, REMINDER_NONE)
            setViewStyleAlertReminder(preAlertNone, preAlertNoneTick)
            SharedPreferences.savePreAdhanReminderOption(
                requireContext(),
                prayerName,
                REMINDER_NONE
            )

            preAdhanReminderText.text = getString(R.string.none) // Immediately update text
            SharedPreferences.savePreAlertValue(mContext, REMINDER_NONE) // Save preference

            resetViewStyleAlertReminder(preAlertFiveMins, preAlertFiveMinsTick)
            resetViewStyleAlertReminder(preAlertTenMins, preAlertTenTick)
            resetViewStyleAlertReminder(preAlertFifteenMins, preAlertFifteenTick)
        }
        preAlertFiveMins.setOnClickListener {
            SharedPreferences.setViewStyleAndSaveToPrefs(
                mContext,
                preAlertFiveMins,
                preAlertFiveMinsTick,
                REMINDER_FIVE_MINT_BEFORE
            )
            setViewStyleAlertReminder(preAlertFiveMins, preAlertFiveMinsTick)
            SharedPreferences.savePreAdhanReminderOption(
                requireContext(),
                prayerName,
                REMINDER_FIVE_MINT_BEFORE
            )
            preAdhanReminderText.text = getString(R.string._5_mins_before)
            SharedPreferences.savePreAlertValue(mContext, REMINDER_FIVE_MINT_BEFORE)
            resetViewStyleAlertReminder(preAlertNone, preAlertNoneTick)
            resetViewStyleAlertReminder(preAlertTenMins, preAlertTenTick)
            resetViewStyleAlertReminder(preAlertFifteenMins, preAlertFifteenTick)
        }
        preAlertTenMins.setOnClickListener {
            SharedPreferences.savePreAdhanReminderOption(
                requireContext(),
                prayerName,
                REMINDER_TEN_MINT_BEFORE
            )
            SharedPreferences.setViewStyleAndSaveToPrefs(
                mContext,
                preAlertTenMins,
                preAlertTenTick,
                REMINDER_TEN_MINT_BEFORE
            )
            setViewStyleAlertReminder(preAlertTenMins, preAlertTenTick)
            preAdhanReminderText.text = getString(R.string._10_mins_before)

            SharedPreferences.savePreAlertValue(mContext, REMINDER_TEN_MINT_BEFORE)
            resetViewStyleAlertReminder(preAlertNone, preAlertNoneTick)
            resetViewStyleAlertReminder(preAlertFiveMins, preAlertFiveMinsTick)
            resetViewStyleAlertReminder(preAlertFifteenMins, preAlertFifteenTick)
        }
        preAlertFifteenMins.setOnClickListener {
            SharedPreferences.savePreAdhanReminderOption(
                requireContext(),
                prayerName,
                REMINDER_FIFTEEN_MINT_BEFORE
            )
            SharedPreferences.setViewStyleAndSaveToPrefs(
                mContext,
                preAlertFifteenMins,
                preAlertFifteenTick,
                REMINDER_FIFTEEN_MINT_BEFORE
            )
            setViewStyleAlertReminder(preAlertFifteenMins, preAlertFifteenTick)
            preAdhanReminderText.text = getString(R.string._15_mins_before)
            SharedPreferences.savePreAlertValue(mContext, REMINDER_FIFTEEN_MINT_BEFORE)

            resetViewStyleAlertReminder(preAlertNone, preAlertNoneTick)
            resetViewStyleAlertReminder(preAlertFiveMins, preAlertFiveMinsTick)
            resetViewStyleAlertReminder(preAlertTenMins, preAlertTenTick)
        }



        noneNotificationView.setOnClickListener {
            //  SharedPreferences.saveAdhanOption(mContext, prayerName, "none")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_NONE)
            // Save the selected options to SharedPreferences
            SharedPreferences.savePreAdhanReminderOption(
                requireContext(),
                prayerName,
                NOTIFICATION_NONE
            )
            setViewStyleAndSaveToPrefs(
                mContext,
                noneNotificationText,
                noneTickImage,
                null,
                false,
                NOTIFICATION_NONE
            )
            setViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)
            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
        }
        silentNotificationView.setOnClickListener {
            //    SharedPreferences.saveAdhanOption(mContext, prayerName, "silent")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_SILENT)

            Log.d(
                NextPrayerTimeFragment::class.simpleName,
                "showBottomSheetNotificationAlertSound: silentNotificationView clicked.."
            )
            setViewStyleAndSaveToPrefs(
                mContext,
                silentNotificationText,
                silentTickImage,
                null,
                false,
                NOTIFICATION_SILENT
            )
            setViewStyleNotification(silentNotificationText, silentTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
        }
        beepNotificationView.setOnClickListener {
            //   SharedPreferences.saveAdhanOption(mContext, prayerName, "beep")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_BEEP)
            setMediaPlayer(R.raw.beep_sound)
            SharedPreferences.saveAudioNameInPrefs(mContext, AUDIO_BEEP)
            setViewStyleAndSaveToPrefs(
                mContext,
                beepNotificationText,
                beepTickImage,
                null,
                false,
                NOTIFICATION_BEEP
            )
            setViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
        }

        adhanOneNotificationView.setOnClickListener {
            //    SharedPreferences.saveAdhanOption(mContext, prayerName, "adhan one")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_ADHAN_ONE)
            setMediaPlayer(R.raw.one_azan_by_nasir_al_qatami)
            SharedPreferences.saveAudioNameInPrefs(mContext, AUDIO_ADHAN_ONE)
            setViewStyleAndSaveToPrefs(
                mContext,
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true,
                NOTIFICATION_ADHAN_ONE
            )
            setViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)
        }
        adhanTwoNotificationView.setOnClickListener {
            //   SharedPreferences.saveAdhanOption(mContext, prayerName, "adhan two")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_ADHAN_TWO)
            setMediaPlayer(R.raw.two_azan_by_mansour_al_zahrani)
            SharedPreferences.saveAudioNameInPrefs(mContext, AUDIO_ADHAN_TWO)

            setViewStyleAndSaveToPrefs(
                mContext,
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true,
                NOTIFICATION_ADHAN_TWO
            )
            setViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )

            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)
        }
        adhanThreeNotificationView.setOnClickListener {
            //  SharedPreferences.saveAdhanOption(mContext, prayerName, "adhan three")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_ADHAN_THREE)
            setMediaPlayer(R.raw.three_adhan_by_masjid_al_haram)
            SharedPreferences.saveAudioNameInPrefs(mContext, AUDIO_ADHAN_THREE)

            setViewStyleAndSaveToPrefs(
                mContext,
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true,
                NOTIFICATION_ADHAN_THREE
            )
            setViewStyleNotification(
                adhanThreeNotificationText,
                adhanThreeTickImage,
                adhanThreeSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)

        }
        adhanFourNotificationView.setOnClickListener {
            //   SharedPreferences.saveAdhanOption(mContext, prayerName, "adhan four")
            saveNotificationOption(mContext, prayerName, NOTIFICATION_ADHAN_FOUR)
            setMediaPlayer(R.raw.four_adhan_by_mishary_rashid_alafasy)
            SharedPreferences.saveAudioNameInPrefs(mContext, AUDIO_ADHAN_FOUR)

            setViewStyleAndSaveToPrefs(
                mContext,
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true,
                NOTIFICATION_ADHAN_FOUR
            )
            setViewStyleNotification(
                adhanFourNotificationText,
                adhanFourTickImage,
                adhanFourSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanOneNotificationText,
                adhanOneTickImage,
                adhanOneSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanTwoNotificationText,
                adhanTwoTickImage,
                adhanTwoSpeakerText,
                true
            )
            resetViewStyleNotification(
                adhanThreeNotificationText, adhanThreeTickImage, adhanThreeSpeakerText, true
            )
            resetViewStyleNotification(noneNotificationText, noneTickImage)
            resetViewStyleNotification(beepNotificationText, beepTickImage)
            resetViewStyleNotification(silentNotificationText, silentTickImage)


        }
        // Apply styles based on the loaded value
        when (selectedNotificationOption) {
            NOTIFICATION_NONE -> {
                setViewStyleNotification(noneNotificationText, noneTickImage)
            }

            NOTIFICATION_SILENT -> {
                setViewStyleNotification(silentNotificationText, silentTickImage)
            }

            NOTIFICATION_BEEP -> {
                setViewStyleNotification(beepNotificationText, beepTickImage)
            }

            NOTIFICATION_ADHAN_ONE -> {
                setViewStyleNotification(
                    adhanOneNotificationText,
                    adhanOneTickImage,
                    adhanOneSpeakerText,
                    true
                )
            }

            NOTIFICATION_ADHAN_TWO -> {
                setViewStyleNotification(
                    adhanTwoNotificationText,
                    adhanTwoTickImage,
                    adhanTwoSpeakerText,
                    true
                )
            }

            NOTIFICATION_ADHAN_THREE -> {
                setViewStyleNotification(
                    adhanThreeNotificationText,
                    adhanThreeTickImage,
                    adhanThreeSpeakerText,
                    true
                )
            }

            NOTIFICATION_ADHAN_FOUR -> {
                setViewStyleNotification(
                    adhanFourNotificationText,
                    adhanFourTickImage,
                    adhanFourSpeakerText,
                    true
                )
            }
            // Handle other cases as needed
        }
        bottomSheetDialog.show()
        bottomSheetDialog.setOnDismissListener {
            mediaPlayer?.stop() // Stop the audio playback when the bottom sheet is dismissed
        }
        navigateBack.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
            }
            bottomSheetDialog.dismiss()
        }
    }


    private fun setMediaPlayer(audioResource: Int) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), audioResource)
        mediaPlayer?.start()
    }

    // Method to set the view style
    private fun setViewStyleNotification(
        titleTextView: TextView,
        tickImageView: ImageView,
        speakerTextView: TextView? = null, // Nullable TextView for speaker text
        isSpeakerText: Boolean = false // Boolean parameter for speaker text
    ) {
        titleTextView.setTextAppearance(R.style.selected_notification_text_style)
        tickImageView.visible()

        // Check if speaker text exists and the isSpeakerText flag is true
        if (speakerTextView != null && isSpeakerText) {
            speakerTextView.setTextAppearance(R.style.makkah_view_text_text_style)
        }
    }

    // Method to reset the view style
    private fun resetViewStyleNotification(
        titleTextView: TextView,
        tickImageView: ImageView,
        speakerTextView: TextView? = null, // Nullable TextView for speaker text
        isSpeakerText: Boolean = false // Boolean parameter for speaker text
    ) {
        titleTextView.setTextAppearance(R.style.notification_sound_title_text_style)
        tickImageView.invisible()

        // Check if speaker text exists and the isSpeakerText flag is true
        if (speakerTextView != null && isSpeakerText) {
            speakerTextView.setTextAppearance(R.style.adhan_speaker_text_text_style)
        }
    }

    // Method to set the view style
    private fun setViewStyleAlertReminder(
        titleTextView: TextView,
        tickImageView: ImageView,

        ) {
        titleTextView.setTextAppearance(R.style.selected_notification_text_style)
        tickImageView.visible()
    }

    // Method to reset the view style
    private fun resetViewStyleAlertReminder(
        titleTextView: TextView,
        tickImageView: ImageView,

        ) {
        titleTextView.setTextAppearance(R.style.unselected_alert_title_text_style)
        tickImageView.gone()

    }

    private fun initObserver() {
        Log.d(NextPrayerTimeFragment::class.simpleName, "initObserver: ")
        viewModel.prayerTimes.observe(viewLifecycleOwner) { prayerTimesList ->
            Log.d(NextPrayerTimeFragment::class.simpleName, "initObservation: Setting prayer times")
            // set prayer times on Views..//
            if (prayerTimesList != null && prayerTimesList.size == 5) {
                binding.layoutNextPrayerBackground.tvTimeFajr.text = prayerTimesList[0]
                binding.layoutNextPrayerBackground.tvUnselectedZuharTime.text = prayerTimesList[1]
                binding.layoutNextPrayerBackground.tvUnselectedAsrTime.text = prayerTimesList[2]
                binding.layoutNextPrayerBackground.tvUnselectedMaghribTime.text = prayerTimesList[3]
                binding.layoutNextPrayerBackground.tvUnselectedIshaTime.text = prayerTimesList[4]
                if (prayerTimesList != null && prayerTimesList.size == 5) {
                    binding.layoutNextPrayerBackground.tvTimeFajr.text =
                        prayerTimesList.values.elementAt(0)
                    binding.layoutNextPrayerBackground.tvUnselectedZuharTime.text =
                        prayerTimesList.values.elementAt(1)
                    binding.layoutNextPrayerBackground.tvUnselectedAsrTime.text =
                        prayerTimesList.values.elementAt(2)
                    binding.layoutNextPrayerBackground.tvUnselectedMaghribTime.text =
                        prayerTimesList.values.elementAt(3)
                    binding.layoutNextPrayerBackground.tvUnselectedIshaTime.text =
                        prayerTimesList.values.elementAt(4)
                }
            }

            // to handle count down
            viewModel.index.observe(viewLifecycleOwner) { index ->
                Log.d(DashBoardFragment::class.simpleName, "initObserver: next Prayer $index")
                if (QiblaApp.selectedPrayerPos == 0) {
                    when (index) {
                        1 -> fajrBg()
                        2 -> zuhrBg()
                        3 -> asarBg()
                        4 -> maghribBg()
                        5 -> ishaBg()
                    }
                } else {
                    Log.d(
                        NextPrayerTimeFragment::class.simpleName,
                        "initObserver: Background selected by user."
                    )
                }
            }

            // handle count down value
            viewModel.counter.observe(viewLifecycleOwner) {
                binding.tvTime.text = "$it"
            }
        }
    }

        private fun setUserCityFromStorage() {
            Log.d(NextPrayerTimeFragment::class.simpleName, "setUserCityFromStorage: ")
            val city = SharedPreferences.getUserCity(mContext)
            binding.tvLocationCity.text = city
        }

        private fun scrollToEnd() {
            binding.layoutNextPrayerBackground.svPrayerTimes.post {
                binding.layoutNextPrayerBackground.svPrayerTimes.scrollTo(
                    0,
                    binding.layoutNextPrayerBackground.svPrayerTimes.bottom
                )
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
            scrollToEnd()
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
            scrollToEnd()
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
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.background_transparent,
                            null
                        )
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

        override fun onStop() {
            super.onStop()
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }


        override fun onPause() {
            super.onPause()
            // Stop the audio playback
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

