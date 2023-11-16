package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.digitaltasbih

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
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDigitalTasbihBinding


class DigitalTasbihFragment :BaseFragment<FragmentDigitalTasbihBinding>(R.layout.fragment_digital_tasbih) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            digitalTasbihCounterFragment = this@DigitalTasbihFragment
        }
        val toolbar = binding.toolbarDigitalTasbih
        val toolbarText = toolbar.titleCounter
        toolbarText.text = "Tasbih Counter"
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
    }

}