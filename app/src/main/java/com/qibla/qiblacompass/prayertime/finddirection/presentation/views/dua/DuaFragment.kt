package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dua

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDigitalTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDuaBinding


class DuaFragment : BaseFragment<FragmentDuaBinding>(R.layout.fragment_dua) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DuaFragment::class.java.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(DuaFragment::class.java.simpleName, "onViewCreated: ")
        binding.apply {
            duaFragment = this@DuaFragment
        }
        binding.toolbarDua.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarDua.tvToolbarSubScreen.text = "Dua e Qanoot"
        binding.toolbarDua.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
    }
}