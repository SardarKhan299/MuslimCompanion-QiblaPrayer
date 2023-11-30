package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.zakat

import android.os.Bundle
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
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentZakatBinding


class ZakatFragment : BaseFragment<FragmentZakatBinding>(R.layout.fragment_zakat) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            zakatFragment = this@ZakatFragment
        }
        binding.toolbarZakat.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarZakat.tvToolbarSubScreen.text = "Zakat"
        binding.toolbarZakat.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
    }
}