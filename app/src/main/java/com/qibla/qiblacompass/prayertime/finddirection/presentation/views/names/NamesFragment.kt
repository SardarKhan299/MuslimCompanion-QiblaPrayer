package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.allahNamesImages
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.numberImages
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp.Companion.rasoolNamesImages
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNamesBinding


class NamesFragment : BaseFragment<FragmentNamesBinding>(R.layout.fragment_names) {
    lateinit var recyclerView: RecyclerView
    val position = Int


    private val allahNamesImages = QiblaApp.allahNamesImages
    private val rasoolNamesImages = QiblaApp.rasoolNamesImages

    // SharedPreferences key
    private val PREFS_KEY = "selected_data"
    private val PREFS_SELECTED_KEY = "isAllahNamesSelected"

    // Flag to determine which set of data to display
    private val PREFS_SELECTED_KEY_ALLAH = "isAllahNamesSelected"
    private val PREFS_SELECTED_KEY_RASOOL = "isRasoolNamesSelected"


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
        //  adapter = NamesAdapter(ArrayList(), numberImages, ::onItemClick)


//        isAllahNamesSelected =
//            SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
//        isRasoolNamesSelected =
//            SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
//        // Set the initial data based on the stored preference




        binding.viewAllahNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated:view Name clicked.")
            QiblaApp.isAllahNamesSelected = true
            setAdapter(allahNamesImages)
            SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
            updateViewStyles()
            SharedPreferences.setIsAllahSelected(mContext,true)
            // Save the selected name to SharedPreferences
            val selectedName = "Allah Name" // Replace with the actual name clicked
            SharedPreferences.saveSelectedName(mContext, selectedName)

        }

        binding.viewRasoolNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated: view Rasool Name clicked.")
            QiblaApp.isAllahNamesSelected = false
            setAdapter(rasoolNamesImages)
            SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
            updateViewStyles()
            SharedPreferences.setIsAllahSelected(mContext,false)
            // Save the selected name to SharedPreferences
            val selectedName = "Rasool Name" // Replace with the actual name clicked
            val nameSavedRasool = SharedPreferences.saveSelectedName(mContext, selectedName)
            Log.d(
                NamesFragment::class.simpleName,
                "onViewCreated: Rasool name saved $nameSavedRasool"
            )
        }
        loadInitialData()

    }

    private fun setAdapter(names: ArrayList<Int>) {
        val adapter = NamesAdapter(names, numberImages) { position ->
            // Handle item click here
            val saved = SharedPreferences.saveSelectedPosition(mContext, position)
            Log.d(NamesFragment::class.simpleName, "setAdapter: $saved")
            //    savePosition(position)
            Log.d(NamesFragment::class.simpleName, "setAdapter: $position")
            navigateToDetailScreen()
        }
        recyclerView.adapter = adapter
        val spanCount = 2 // Adjust as needed
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

    }

    private fun navigateToDetailScreen() {
        // Navigate to detail screen
        findNavController().navigate(R.id.nameDetailFragment)
    }

    fun savePosition(position: Int) {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("selected_position", position).apply()
    }

    private fun loadInitialData() {
        // Retrieve selection from SharedPreferences
//        isAllahNamesSelected = SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
//        isRasoolNamesSelected = SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)

        if (QiblaApp.isAllahNamesSelected) {
            // adapter.setData(allahNamesImages)
            setAdapter(allahNamesImages)
            updateViewStyles()
        } else {
            // adapter.setData(rasoolNamesImages)
            setAdapter(rasoolNamesImages)
            updateViewStyles()
        }
    }


    private fun updateViewStyles() {
        if (QiblaApp.isAllahNamesSelected) {
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
        } else {
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


//
//imageResourceRasool = ArrayList()
//imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_two, R.drawable.ic_one_number))
//imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_one, R.drawable.ic_two_number))
//
//// isAllahNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
////  isRasoolNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_RASOOL)
//
//
//isAllahNamesSelected =
//SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
//isRasoolNamesSelected =
//SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
//// Set the initial data based on the stored preference
//if (isAllahNamesSelected) {
//    //    adapter.setData(imageResource)
//    updateViewStyles()
//} else if (isRasoolNamesSelected) {
//    //   adapter.setData(imageResourceRasool)
//    updateViewStyles()
//}
//
//// Set up the RecyclerView with a grid layout manager
//val spanCount = 2 // Adjust as needed
//recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
//val adapter = NamesAdapter(allahNamesImages, numberImages) { position ->
//    // Handle item click
//    saveClickedPosition(position)
//    Log.d(NamesFragment::class.java.simpleName, "onViewCreated: $position")
//    // findNavController().navigate(R.id.nameDetailFragment)
//}
//recyclerView.adapter = adapter
//// recyclerView.adapter = adapter
//
//
//view.findViewById<View>(R.id.viewAllahNames)
//
//binding.viewAllahNames.setOnClickListener {
//    Log.d(NamesFragment::class.java.simpleName, "onViewCreated:view Name clicked.")
//    isAllahNamesSelected = true
//    isRasoolNamesSelected = false
//    val adapterRasool = NamesAdapter(allahNamesImages,numberImages) { position ->
//        saveClickedPosition(position, true) // Save Rasool name clicked position
//    }
//    recyclerView.adapter = adapterRasool
//    //  adapter.setData(imageResource)
//    // saveSelectionToSharedPreferences(true) // Save the selection type
//    SharedPreferences.saveSelectionToSharedPreferences(mContext, false)
//    SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
//
//    //saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
//    updateViewStyles()
//}
//
//binding.viewRasoolNames.setOnClickListener {
//    Log.d(NamesFragment::class.java.simpleName, "onViewCreated: view Rasool Name clicked.")
//    isAllahNamesSelected = false
//    isRasoolNamesSelected = true
//    val adapterRasool = NamesAdapter(rasoolNamesImages,numberImages) { position ->
//        Log.d(NamesFragment::class.java.simpleName, "onViewCreated:Rasool $position")
//        saveClickedPosition(position, false) // Save Rasool name clicked position
//    }
//    recyclerView.adapter = adapterRasool
//    // adapter.setData(imageResourceRasool)
//    // saveSelectionToSharedPreferences(false) // Save the selection type
//    // SharedPreferences.saveSelectionToSharedPreferences(mContext, false)
//    //  saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_RASOOL)
//    SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
//    updateViewStyles()
//}


//    private fun onItemClick(namesData: NamesData) {
//
//        val position = if (isAllahNamesSelected) {
//          //  imageResource.indexOf(namesData)
//
//        } else {
//            imageResourceRasool.indexOf(namesData)
//        }
//        val translation = if (isAllahNamesSelected) {
//           // allahNamesTranslations[position]
//        } else {
//          //  SharedPreferences.rasoolNamesTranslation[position]
//        }
//        //save player click position.
//        // SharedPreferences.saveSelectedPlayerPosition(requireContext(), position)
//
//        //    saveSelectedDataToSharedPreferences(namesData, translation)
//        // Navigate to the detail screen
//    //    findNavController().navigate(R.id.nameDetailFragment)
//    }

//    private fun saveSelectedDataToSharedPreferences(
//        namesData: NamesData,
//        translation: Pair<String, String>
//    ) {
//        val sharedPreferences =
//            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        // Access the integer values from namesData:
//        editor.putInt("selected_name_image", namesData.nameImage)  // Use namesData.nameImage
//        editor.putInt(
//            "selected_name_number_image",
//            namesData.nameNumberImage
//        )  // Use namesData.nameNumberImage
//        editor.putString("selected_translation_urdu", translation.first)
//        editor.putString("selected_translation_english", translation.second)
//        editor.apply()
//    }
