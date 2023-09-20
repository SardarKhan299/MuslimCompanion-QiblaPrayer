package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSideMenuBinding


class SideMenuFragment : BaseFragment<FragmentSideMenuBinding>(R.layout.fragment_side_menu) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sideMenuFragment = this@SideMenuFragment
        }
        binding.toolbarSideMenu.groupToolbarProfile.visibility = View.VISIBLE
        binding.toolbarSideMenu.groupToolbar.visibility = View.GONE
    }
}