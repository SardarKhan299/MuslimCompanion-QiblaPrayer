package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.nameDetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.clearFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.allahNamesTranslations
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.getSelectedPlayerPosition
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.isAllahNamesSelected
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNameDetailBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names.NamesData


class NameDetailFragment : BaseFragment<FragmentNameDetailBinding>(R.layout.fragment_name_detail) {
    private val PREFS_KEY = "selected_data"
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var allahTranslations: ArrayList<Pair<String, String>>
    private lateinit var rasoolTranslations: ArrayList<Pair<String, String>>
    private var currentPosition: Int = 0
    private lateinit var imageResource: List<NamesData>
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

        //  val urduTranslation = sharedPreferences.getString("selected_translation_urdu", "")
        //val englishTranslation = sharedPreferences.getString("selected_translation_english", "")
// Retrieve the translation pair corresponding to the selected position
        val selectedPosition = SharedPreferences.getSelectedPlayerPosition(requireContext())

        val selectedTranslationPair = allahNamesTranslations[selectedPosition]

// Set the translations to the TextViews
        binding.tvNameTranslationUrdu.text = selectedTranslationPair.first
        binding.tvNameTranslationEnglish.text = selectedTranslationPair.second
        // Set the translations to the TextViews
//        binding.tvNameTranslationUrdu.text = urduTranslation
//        binding.tvNameTranslationEnglish.text = englishTranslation

        val isAllahNamesSelected =
            SharedPreferences.getSelectionFromSharedPreferencesDetail(mContext)

        // Set the text based on the selection
        if (isAllahNamesSelected) {
            // Set text for Allah Names
            toolbar.titleCounter.text = getString(R.string.allah_names)
        } else {
            // Set text for Rasool Names
            toolbar.titleCounter.text = getString(R.string.rasool_names)
        }
        // Retrieve the stored position from SharedPreferences
        //val selectedPosition = getSelectedPlayerPosition(requireContext())

        // Define an array of audio file resource IDs
        val audioResources = intArrayOf(
            R.raw.audio_allah_name_1,
            R.raw.audio_allah_name_2,
            R.raw.audio_allah_name_3,
            R.raw.audio_allah_name_4,
            R.raw.audio_allah_name_5
        )
        val rasoolAudioResources = intArrayOf(
            R.raw.audio_allah_name_5,
            R.raw.audio_allah_name_4,

            )

        val audioResourceId = if (isAllahNamesSelected) {
            audioResources[selectedPosition]
        } else {
            rasoolAudioResources[selectedPosition]
        }
        // Initialize MediaPlayer with the audio file
        mediaPlayer = MediaPlayer.create(requireContext(), audioResourceId)

        // Set OnClickListener on the button to play the audio
        binding.imgPlayer.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        // Retrieve the stored position from SharedPreferences
        //    val selectedPosition = SharedPreferences.getSelectedPlayerPosition(requireContext())

        // Initialize data from SharedPreferences
        // imageResource = loadFromSharedPreferences()
        // Update UI based on the selected position
       // updateUI(selectedPosition)
        binding.imgBackward.setOnClickListener {
            Log.d(NameDetailFragment::class.simpleName, "onViewCreated: Button Backward Clicked.")
            // Decrement currentPosition to move to the previous position
            val previousPosition =
                if (selectedPosition > 0) selectedPosition - 1 else selectedPosition
            // Save the updated position in SharedPreferences
            SharedPreferences.saveSelectedPlayerPosition(requireContext(), previousPosition)
            // Update the UI with data at the new position
           // updateUI(previousPosition)
        }
    }


    private fun navigateToPreviousPosition() {
        val currentPosition = getSelectedPlayerPosition(requireContext())
        val previousPosition = if (currentPosition > 0) currentPosition - 1 else currentPosition
        SharedPreferences.saveSelectedPlayerPosition(requireContext(), previousPosition)
     //   updateUI(previousPosition)
    }

//    private fun updateUI(position: Int) {
//        // Set data based on currentPosition
//        binding.imgMainName.setImageResource(imageResource[currentPosition].nameImage)
//        binding.imgNameNumber.setImageResource(imageResource[currentPosition].nameNumberImage)
//        // Update other views accordingly
//        // For example, update translations
//        if (isAllahNamesSelected) {
//            binding.tvNameTranslationUrdu.text = allahTranslations[currentPosition].first
//            binding.tvNameTranslationEnglish.text = allahTranslations[currentPosition].second
//        } else {
//            binding.tvNameTranslationUrdu.text = rasoolTranslations[currentPosition].first
//            binding.tvNameTranslationEnglish.text = rasoolTranslations[currentPosition].second
//        }
//        val audioResources = intArrayOf(
//            R.raw.audio_allah_name_1,
//            R.raw.audio_allah_name_2,
//            R.raw.audio_allah_name_3,
//            R.raw.audio_allah_name_4,
//            R.raw.audio_allah_name_5
//        )
//        val rasoolAudioResources = intArrayOf(
//            R.raw.audio_allah_name_5,
//            R.raw.audio_allah_name_4
//        )
//        val audioResourceId = if (isAllahNamesSelected) {
//            audioResources[position]
//        } else {
//            rasoolAudioResources[position]
//        }
//        mediaPlayer = MediaPlayer.create(requireContext(), audioResourceId)
//        Log.d(NameDetailFragment::class.java.simpleName, "updateUI: $position")
//        binding.imgPlayer.setOnClickListener {
//            if (!mediaPlayer.isPlaying) {
//                mediaPlayer.start()
//            }
//        }
//    }
//// Function to load data from SharedPreferences
//private fun loadFromSharedPreferences(): List<NamesData> {
//    // Retrieve the stored list from SharedPreferences
//    val gson = Gson()
//    val json = SharedPreferences.getNamesDataList(requireContext())
//    val type = object : TypeToken<List<NamesData>>() {}.type
//    return gson.fromJson(json, type) ?: emptyList()
//}

    // Function to update the UI based on the position
//    private fun updateUI(position: Int) {
//        // Set data based on position
//        binding.imgMainName.setImageResource(imageResource[position].nameImage)
//        binding.imgNameNumber.setImageResource(imageResource[position].nameNumberImage)
//        // Update translations
//        val sharedPreferences =
//            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
//        val urduTranslation = sharedPreferences.getString("selected_translation_urdu", "")
//        val englishTranslation = sharedPreferences.getString("selected_translation_english", "")
////        val urduTranslation = SharedPreferences.getSelectedTranslationUrdu(requireContext(), position)
////        val englishTranslation = SharedPreferences.getSelectedTranslationEnglish(requireContext(), position)
//        binding.tvNameTranslationUrdu.text = urduTranslation
//        binding.tvNameTranslationEnglish.text = englishTranslation
//        // Other UI updates...
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release the MediaPlayer resources when the fragment is destroyed
        mediaPlayer.release()
    }


}
