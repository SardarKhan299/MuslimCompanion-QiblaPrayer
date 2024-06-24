package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.AdUtil
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.loadFont
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentQuranBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment


class QuranFragment : BaseFragment<FragmentQuranBinding>(R.layout.fragment_quran) {
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(QuranFragment::class.java.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            quranFragment = this@QuranFragment
        }
        MobileAds.initialize(mContext) {
            Log.d(
                DashBoardFragment::class.java.simpleName,
                "onViewCreated: onInitializationCompleted"
            )
        }
        adView = binding.adsBannerQuran
        // Initialize AdUtil and load the banner ad
        AdUtil.initialize(requireContext(), adView)

        binding.toolbarQuran.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarQuran.tvToolbarSubScreen.text = "Quran"
        binding.toolbarQuran.viewSubScreen.setOnClickListener {
            findNavController().navigate(R.id.boardFragment)
        }
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Sura"))
        tabLayout.addTab(tabLayout.newTab().setText("Juz"))
        tabLayout.addTab(tabLayout.newTab().setText("Playlist"))
        tabLayout.addTab(tabLayout.newTab().setText("My Quran"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        //manages fragments attached to another fragment (child fragments).
        //fragmentManager manages fragments attached directly to the activity.
        val viewPagerAdapter = QuranViewPagerFragment(childFragmentManager, lifecycle)
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

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }

        })
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
        private val TAG = "QuranFragment"
    }
}