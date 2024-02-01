package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNamesBinding


class NamesFragment : BaseFragment<FragmentNamesBinding>(R.layout.fragment_names) {
    lateinit var recyclerView: RecyclerView

    //    private val imageResource = listOf(
//        R.drawable.ic_name_two,
//        R.drawable.ic_name_three,
//        R.drawable.ic_name_one
//
//    )
    // Lists to store data
    private lateinit var imageResource: ArrayList<NamesData>
    private lateinit var imageResourceRasool: ArrayList<NamesData>

    //    private val imageResourceRasool = listOf(
//        R.drawable.ic_rasool_name_two,
//        R.drawable.ic_rasool_name_one,
//
//    )
    private lateinit var adapter: NamesAdapter

    // SharedPreferences key
    private val PREFS_KEY = "selected_data"
    private val PREFS_SELECTED_KEY = "isAllahNamesSelected"

    // Flag to determine which set of data to display
    private val PREFS_SELECTED_KEY_ALLAH = "isAllahNamesSelected"
    private val PREFS_SELECTED_KEY_RASOOL = "isRasoolNamesSelected"

    private var isAllahNamesSelected = false
    private var isRasoolNamesSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(NamesFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            namesFragment = this@NamesFragment
        }
        val toolbar = binding.toolbarNames
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.names)
        recyclerView = binding.recyclerViewNames
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        adapter = NamesAdapter(ArrayList(), ::onItemClick)
        // Initialize the lists with data
        imageResource = ArrayList()
        imageResource.add(NamesData(R.drawable.ic_one_name, R.drawable.ic_one_number))
        imageResource.add(NamesData(R.drawable.ic_two_name, R.drawable.ic_two_number))
        imageResource.add(NamesData(R.drawable.ic_three_name, R.drawable.ic_three_number))

        imageResourceRasool = ArrayList()
        imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_two, R.drawable.ic_one_number))
        imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_one, R.drawable.ic_two_number))

        isAllahNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
        isRasoolNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_RASOOL)
        // Set the initial data based on the stored preference
        if (isAllahNamesSelected) {
            adapter.setData(imageResource)
            updateViewStyles()
        } else if (isRasoolNamesSelected) {
            adapter.setData(imageResourceRasool)
            updateViewStyles()
        }

        // Set up the RecyclerView with a grid layout manager
        val spanCount = 2 // Adjust as needed
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.adapter = adapter

        view.findViewById<View>(R.id.viewAllahNames)

        binding.viewAllahNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated:view Name clicked.")
            isAllahNamesSelected = true
            isRasoolNamesSelected = false
            adapter.setData(imageResource)
            saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
            updateViewStyles()
//            isAllahNamesSelected = true
//            adapter.setData(imageResource)
//            saveSelectionToSharedPreferences()
//            // Change the text style for Allah Names
//            binding.viewAllahNames.setBackgroundResource(R.drawable.button_bg)
//            binding.viewRasoolNames.setBackgroundResource(R.drawable.name_transparent_bg)
//            binding.tvAllahNames.setTextAppearance(
//                requireContext(),
//                R.style.advertisement_text_style
//            )
//            binding.tvRasoolNames.setTextAppearance(
//                requireContext(),
//                R.style.forgot_password_text_style
//            )
        }

        binding.viewRasoolNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated: view Rasool Name clicked.")
            isAllahNamesSelected = false
            isRasoolNamesSelected = true
            adapter.setData(imageResourceRasool)
            saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_RASOOL)
            updateViewStyles()
            //findNavController().navigate(R.id.rasoolDetailFragment)
//            isAllahNamesSelected = false
//            adapter.setData(imageResourceRasool)
//            saveSelectionToSharedPreferences()
//            // Change the text style for Rasool Names
//            binding.tvAllahNames.setTextAppearance(
//                requireContext(),
//                R.style.forgot_password_text_style
//            )
//            binding.tvRasoolNames.setTextAppearance(
//                requireContext(),
//                R.style.advertisement_text_style
//            )
//            binding.viewAllahNames.setBackgroundResource(R.drawable.name_transparent_bg)
//            binding.viewRasoolNames.setBackgroundResource(R.drawable.button_bg)
        }


    }
    private fun getSelectionFromSharedPreferences(key: String): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    private fun saveSelectionToSharedPreferences(key: String) {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, true).apply()
    }
    private fun onItemClick(namesData: NamesData) {
        // Save selected position to SharedPreferences
        saveSelectedPositionToSharedPreferences(namesData)

        // Navigate to the detail screen
        findNavController().navigate(R.id.nameDetailFragment)
    }

    private fun saveSelectedPositionToSharedPreferences(namesData: NamesData) {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selected_name_image", namesData.nameImage)
        editor.putInt("selected_name_number_image", namesData.nameNumberImage)
        editor.apply()
    }
    private fun saveSelectedPositionToSharedPreferences(position: Int) {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("selected_position", position).apply()
    }

    private fun saveSelectionToSharedPreferences() {
        Log.d(
            NamesFragment::class.java.simpleName,
            "saveSelectionToSharedPreferences: saveSelectionToSharedPreferences "
        )
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREFS_SELECTED_KEY, isAllahNamesSelected).apply()
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




    private fun updateViewStyles() {
        if (isAllahNamesSelected) {
            // Change the text style for Allah Names
            binding.viewAllahNames.setBackgroundResource(R.drawable.button_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.tvAllahNames.setTextAppearance(
                requireContext(),
                R.style.advertisement_text_style
            )
            binding.tvRasoolNames.setTextAppearance(
                requireContext(),
                R.style.forgot_password_text_style
            )
        } else if (isRasoolNamesSelected) {
            // Change the text style for Rasool Names
            binding.tvAllahNames.setTextAppearance(
                requireContext(),
                R.style.forgot_password_text_style
            )
            binding.tvRasoolNames.setTextAppearance(
                requireContext(),
                R.style.advertisement_text_style
            )
            binding.viewAllahNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.button_bg)
        }
    }
}

