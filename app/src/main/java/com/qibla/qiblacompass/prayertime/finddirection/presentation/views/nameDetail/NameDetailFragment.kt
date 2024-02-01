package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.nameDetail

import android.content.Context
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
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNameDetailBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names.NamesFragment


class NameDetailFragment : BaseFragment<FragmentNameDetailBinding>(R.layout.fragment_name_detail) {
    private val PREFS_KEY = "selected_data"
    private val PREFS_SELECTED_KEY = "isAllahNamesSelected"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(NameDetailFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            nameDetailFragment = this@NameDetailFragment
        }
        val toolbar = binding.toolbarNamesDetail
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.names)
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        toolbar.imgAddMore.setImageResource(R.drawable.ic_favourite)
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val selectedNameImage =
            sharedPreferences.getInt("selected_name_image", 0) // Default value 0 if not found
        val selectedNameNumberImage = sharedPreferences.getInt("selected_name_number_image", 0)

// Set the retrieved values to your ImageViews
        binding.imgMainName.setImageResource(selectedNameImage)
        binding.imgNameNumber.setImageResource(selectedNameNumberImage)

        // Retrieve the stored selection type
        val isAllahNamesSelected = getSelectionFromSharedPreferences()

        // Set the text based on the selection
        if (isAllahNamesSelected) {
            // Set text for Allah Names
            toolbar.titleCounter.text = "Allah Name"
        } else {
            // Set text for Rasool Names
            toolbar.titleCounter.text = "Rasool Name"
        }

    }

    private fun getSelectionFromSharedPreferences(): Boolean {
        Log.d(
            NamesFragment::class.java.simpleName,
            "getSelectionFromSharedPreferences: getSelectionFromSharedPreferences"
        )
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREFS_SELECTED_KEY, true)
    }
}