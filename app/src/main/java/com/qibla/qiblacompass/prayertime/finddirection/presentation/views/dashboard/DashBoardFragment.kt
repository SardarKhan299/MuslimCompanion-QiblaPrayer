package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PrayerConstants
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashBoardBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding.OnboardingActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection.CompassDirectionActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu.SideMenuFragment
import kotlin.math.log


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(R.layout.fragment_dash_board) {
    private lateinit var rView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DashBoardFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            boardFragment = this@DashBoardFragment
        }
        sharedPreferences = SharedPreferences()
        binding.toolbarBoard.groupToolbarProfile.visibility = View.GONE
        binding.toolbarBoard.groupToolbar.visibility = View.VISIBLE
        rView = binding.layoutBoardFragment.findViewById(R.id.rv_qibla)
        binding.tvAds.setOnClickListener {
            //Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
            startActivity(Intent(mContext, CompassDirectionActivity::class.java))

        }
        binding.viewAutoDetect.setOnClickListener {
            if (binding.includeAutoDetect.layoutDialog.visibility == View.VISIBLE) {
                binding.includeAutoDetect.layoutDialog.visibility = View.INVISIBLE
            } else {
                binding.includeAutoDetect.layoutDialog.visibility = View.VISIBLE
            }
        }
        binding.toolbarBoard.imgToolbar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.sideMenuFragment)
        }

        val dashBoardFajrScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_fajr_screen)
        val dashBoardZuhrScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_zuhr_screen)
        val dashBoardAsarScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_asr_screen)
        val dashBoardMaghribScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_maghrib_screen)
        val dashBoardIshaScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_isha_screen)


        dashBoardFajrScreen.setOnClickListener {
            onViewOneClick(dashBoardFajrScreen)
        }
        dashBoardZuhrScreen.setOnClickListener {
            onViewTwoClick(dashBoardZuhrScreen)
        }
        dashBoardAsarScreen.setOnClickListener {
            onViewThreeClick(dashBoardAsarScreen)
        }
        dashBoardMaghribScreen.setOnClickListener {
            onViewFourClick(dashBoardMaghribScreen)
        }
        dashBoardIshaScreen.setOnClickListener {
            onViewFiveClick(dashBoardIshaScreen)
        }
        rView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        setUpQibla()
    }

    private fun onViewOneClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 1)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewTwoClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 2)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewThreeClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 3)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFourClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 4)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun onViewFiveClick(view: View) {

        // Save the selected prayer position in SharedPreferences
        SharedPreferences.saveSelectedPrayerPosition(mContext, 5)
        Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
    }

    private fun setUpQibla() {
        val data = ArrayList<QiblaData>()
        data.add(QiblaData(R.drawable.qibla_icn, getString(R.string.qibla)))
        data.add(QiblaData(R.drawable.zakat_icn, getString(R.string.zakat)))
        data.add(QiblaData(R.drawable.names_icn, getString(R.string.Names)))
        data.add(QiblaData(R.drawable.tasbeeh_icn, getString(R.string.tasbih_)))
        data.add(QiblaData(R.drawable.prayer_icn, getString(R.string.prayer)))
        data.add(QiblaData(R.drawable.quran_icn, getString(R.string.quran)))
        data.add(QiblaData(R.drawable.makah_live_icn, getString(R.string.makkah_live_)))
        data.add(QiblaData(R.drawable.near_me_icn, getString(R.string.near_me)))
        data.add(QiblaData(R.drawable.hijri_calendar_icn, getString(R.string.hijri_calendar)))
        data.add(QiblaData(R.drawable.hadith_icn, getString(R.string.hadith)))


        val adapter = QiblaAdapter(requireContext(), data) { position ->
            navigateToSpecificScreen(position)
        }
        rView.adapter = adapter

    }

    private fun navigateToSpecificScreen(position: Int) {
        // Navigate to specific screens based on position clicked
        when (position) {
            0 -> {
                // Handle click on position 0
                // Navigate to screen 0
                Navigation.findNavController(requireView()).navigate(R.id.qibalDirectionFragment)

            }
            1 -> {

                Navigation.findNavController(requireView()).navigate(R.id.zakatFragment)
            }
            3 -> {

                Navigation.findNavController(requireView()).navigate(R.id.tasbihFragment)
            }
            4 -> {
                Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)

            }
            6 -> {
                Navigation.findNavController(requireView()).navigate(R.id.makkahLiveFragment)

            }
            // Add more cases for other positions
        }
    }

}