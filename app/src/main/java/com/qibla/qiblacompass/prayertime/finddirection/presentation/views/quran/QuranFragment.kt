package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

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
    }
}