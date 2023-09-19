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
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashboardBinding


class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DashboardFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dashboardFragment = this@DashboardFragment
        }
   val toolbar = binding.toolbarBoard.groupToolbar
        toolbar.visibility = View.VISIBLE
        binding.toolbarBoard.imgToolbar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.sideMenuFragment)
        }
    }

}