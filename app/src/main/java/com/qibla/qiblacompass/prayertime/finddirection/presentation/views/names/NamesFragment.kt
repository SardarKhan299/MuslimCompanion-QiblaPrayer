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
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNamesBinding


class NamesFragment : BaseFragment<FragmentNamesBinding>(R.layout.fragment_names) {
    lateinit var recyclerView: RecyclerView

    private val imageResource = listOf(
        R.drawable.ic_name_one,
        R.drawable.ic_name_two,
        R.drawable.ic_name_three,


    )
    private val imageResourceRasool = listOf(
        R.drawable.ic_rasool_name_two,
        R.drawable.ic_rasool_name_one,

    )
    private lateinit var adapter: NamesAdapter

    // SharedPreferences key
    private val PREFS_KEY = "selected_data"
    private val PREFS_SELECTED_KEY = "isAllahNamesSelected"

    // Flag to determine which set of data to display
    private var isAllahNamesSelected = true
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
        adapter = NamesAdapter(listOf(), ::onItemClick)

        // Set the initial data based on the stored preference
        isAllahNamesSelected = getSelectionFromSharedPreferences()
        if (isAllahNamesSelected) {
            adapter.setData(imageResource)
        } else {
            adapter.setData(imageResourceRasool)
        }

        // Set up the RecyclerView with a grid layout manager
        val spanCount = 2 // Adjust as needed
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.adapter = adapter

        view.findViewById<View>(R.id.viewAllahNames)

        binding.viewAllahNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated:view Name clicked.")
            isAllahNamesSelected = true
            adapter.setData(imageResource)
            saveSelectionToSharedPreferences()
            // Change the text style for Allah Names
           binding.viewAllahNames.setBackgroundResource(R.drawable.button_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.tvAllahNames.setTextAppearance(requireContext(), R.style.advertisement_text_style)
           binding.tvRasoolNames.setTextAppearance(requireContext(), R.style.forgot_password_text_style)
        }

        binding.viewRasoolNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated: view Rasool Name clicked.")
            isAllahNamesSelected = false
            adapter.setData(imageResourceRasool)
            saveSelectionToSharedPreferences()
            // Change the text style for Rasool Names
            binding.tvAllahNames.setTextAppearance(requireContext(), R.style.forgot_password_text_style)
            binding.tvRasoolNames.setTextAppearance(requireContext(), R.style.advertisement_text_style)
            binding.viewAllahNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.button_bg)
        }


    }

    private fun onItemClick(position: Int) {
        // Handle item click if needed
        saveSelectedPositionToSharedPreferences(position)

        // Navigate to the detail screen
        // You can use your navigation logic here
        // For example, if you are using Navigation Component:
        findNavController().navigate(R.id.nameDetailFragment)
    }

    private fun saveSelectionToSharedPreferences() {
        Log.d( NamesFragment::class.java.simpleName, "saveSelectionToSharedPreferences: saveSelectionToSharedPreferences ")
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREFS_SELECTED_KEY, isAllahNamesSelected).apply()
    }

    private fun getSelectionFromSharedPreferences(): Boolean {
        Log.d(NamesFragment::class.java.simpleName, "getSelectionFromSharedPreferences: getSelectionFromSharedPreferences")
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREFS_SELECTED_KEY, true)
    }
    private fun saveSelectedPositionToSharedPreferences(position: Int) {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("selected_position", position).apply()
    }
}


//        binding.viewRasoolNames.setOnClickListener {
//            if (binding.viewRasoolNames.visibility == View.VISIBLE) {
//                binding.viewRasoolNames.visibility = View.INVISIBLE
//                binding.tvRasoolNames.setTextAppearance(
//                    mContext,
//                    R.style.forgot_password_text_style
//                )
//                //}
//            }
//            binding.viewAllahNames.setOnClickListener {
//                binding.viewAllahNames.visibility = View.GONE
//            }
//            binding.viewRasoolNames.setOnClickListener {
//                if (binding.viewRasoolNames.visibility == View.INVISIBLE) {
//                    binding.viewRasoolNames.visibility = View.VISIBLE
//                    binding.tvRasoolNames.setTextAppearance(
//                        mContext,
//                        R.style.advertisement_text_style
//                    )
//                } else {
//                    binding.viewRasoolNames.visibility = View.INVISIBLE
//                    binding.tvRasoolNames.setTextAppearance(
//                        mContext,
//                        R.style.forgot_password_text_style
//                    )
//
//                }
//            }