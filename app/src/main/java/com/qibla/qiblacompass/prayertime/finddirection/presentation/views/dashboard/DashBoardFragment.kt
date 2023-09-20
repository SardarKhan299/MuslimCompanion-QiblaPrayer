package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashBoardBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu.SideMenuFragment


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(R.layout.fragment_dash_board) {


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
        binding.toolbarBoard.groupToolbarProfile.visibility = View.GONE
        binding.toolbarBoard.groupToolbar.visibility = View.VISIBLE
        binding.toolbarBoard.imgToolbar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.sideMenuFragment)
        }

    }

}