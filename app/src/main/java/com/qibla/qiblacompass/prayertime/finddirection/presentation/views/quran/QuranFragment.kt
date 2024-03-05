package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentQuranBinding


class QuranFragment : BaseFragment<FragmentQuranBinding>(R.layout.fragment_quran) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(QuranFragment::class.java.simpleName, "onCreate: ")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            quranFragment = this@QuranFragment
        }
        binding.toolbarQuran.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarQuran.tvToolbarSubScreen.text = "Quran"
        binding.toolbarQuran.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val viewPagerAdapter = QuranViewPagerFragment(fragmentManager!!, lifecycle)
        viewPager.adapter = viewPagerAdapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab selection
                tab?.let {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselection
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselection
            }
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Sura"
                }
                1 -> {
                    tab.text = "Juz"
                }
                2 -> {
                    tab.text = "Playlist"
                }
                3 -> {
                    tab.text = "My Quran"
                }
            }
        }.attach()
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }

        })
    }
}