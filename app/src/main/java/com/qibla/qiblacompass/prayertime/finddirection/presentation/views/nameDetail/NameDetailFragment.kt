package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.nameDetail

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.*
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNameDetailBinding


class NameDetailFragment : BaseFragment<FragmentNameDetailBinding>(R.layout.fragment_name_detail) {
    private var mediaPlayer: MediaPlayer? = null
    lateinit var mainImage: ImageView
    lateinit var numberImageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private lateinit var touchableViews: List<View>

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
        mainImage = binding.imgMainName
        numberImageView = binding.imgNameNumber
        val toolbar = binding.toolbarNamesDetail
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.names)
        toolbar.imgAddMore.invisible()
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        toolbar.imgAddMore.setImageResource(R.drawable.ic_favourite)
        updateUI(QiblaApp.isAllahNamesSelected)
        binding.imgBackward.setOnClickListener {
            navigateToPosition(QiblaApp.isAllahNamesSelected, -1)
        }
        binding.imgForward.setOnClickListener {
            navigateToPosition(QiblaApp.isAllahNamesSelected, 1)
        }
//        // Initialize ScaleGestureDetector
//        scaleGestureDetector = ScaleGestureDetector(mContext, ScaleListener())
//
//        // Initialize GestureDetector
//        gestureDetector = GestureDetector(context, GestureListener())
//        // Initialize the list of touchable views
//        touchableViews = listOf<View>(
//            mainImage,
//            numberImageView,
//            binding.tvNameTranslationUrdu,
//            binding.tvNameTranslationEnglish
//        )
//
//        // Set touch listeners for each touchable view
//        for (touchableView in touchableViews) {
//            touchableView.setOnTouchListener { _, event ->
//                gestureDetector.onTouchEvent(event)
//                scaleGestureDetector.onTouchEvent(event)
//                true
//            }
//        }
}

    private fun navigateToPosition(isAllahSelected: Boolean, step: Int) {
        val currentPosition = SharedPreferences.getSelectedPosition(requireContext())
        val newPosition = currentPosition + step

        val maxPosition =
            if (isAllahSelected) QiblaApp.allahNamesImages.size - 1 else QiblaApp.rasoolNamesImages.size - 1
        val newPositionInRange = newPosition.coerceIn(0, maxPosition)

        // Save the updated position in SharedPreferences
        SharedPreferences.saveSelectedPosition(requireContext(), newPositionInRange)

        // Update the UI with data at the new position
        updateUI(isAllahSelected)
    }

    private fun updateUI(isAllahSelected: Boolean) {
        val nameImages =
            if (isAllahSelected) QiblaApp.allahNamesImages else QiblaApp.rasoolNamesImages
        val numberImages = QiblaApp.numberImages
        val namesTranslations =
            if (isAllahSelected) QiblaApp.allahNamesTranslations else QiblaApp.rasoolNamesTranslations
        val audioResources =
            if (isAllahSelected) QiblaApp.audioAllahResources else QiblaApp.audioRasoolResources

        // Retrieve the saved position from SharedPreferences
        val savedPosition = SharedPreferences.getSelectedPosition(requireContext())

        if (savedPosition in nameImages.indices && savedPosition in numberImages.indices &&
            savedPosition in namesTranslations.indices && savedPosition in audioResources.indices
        ) {
            // Set data to the views based on the selected position
            val mainImageResource = nameImages[savedPosition]
            binding.imgMainName.setImageResource(mainImageResource)

            val numberImageResource = numberImages[savedPosition]
            binding.imgNameNumber.setImageResource(numberImageResource)

            val (urduTranslation, englishTranslation) = namesTranslations[savedPosition]
            binding.tvNameTranslationUrdu.text = urduTranslation
            binding.tvNameTranslationEnglish.text = englishTranslation
            binding.imgPlayer.setOnClickListener {
                val audioResource = audioResources[savedPosition]
                setMediaPlayer(audioResource)
            }

        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show()
        }

        val toolbar = binding.toolbarNamesDetail
        toolbar.titleCounter.text =
            if (isAllahSelected) getString(R.string.allah_names) else getString(R.string.rasool_names)
    }

    private fun setMediaPlayer(audioResource: Int) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), audioResource)
        mediaPlayer?.start()
    }
//    // ScaleGestureDetector to handle pinch-to-zoom gesture
//    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            scaleFactor *= detector.scaleFactor
//            scaleFactor = scaleFactor.coerceIn(0.1f, 5.0f) // Prevent scaling too small or too large
//
//            // Apply scale to all touchable views
//            for (touchableView in touchableViews) {
//                touchableView.scaleX = scaleFactor
//                touchableView.scaleY = scaleFactor
//            }
//
//            return true
//        }
//    }
//
//    // GestureDetector to handle double-tap gesture for resetting zoom
//    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
//        override fun onDoubleTap(e: MotionEvent): Boolean {
//            scaleFactor = 1.0f
//            // Reset scale of all touchable views
//            for (touchableView in touchableViews) {
//                touchableView.scaleX = scaleFactor
//                touchableView.scaleY = scaleFactor
//            }
//            return true
//        }
//    }
}


//private fun navigateToNextPosition(isAllahSelected: Boolean) {
//    val currentPosition = SharedPreferences.getSelectedPosition(requireContext())
//    val nextPosition = if (currentPosition < getMaxPosition(isAllahSelected)) currentPosition + 1 else currentPosition
//
//    // Save the updated position in SharedPreferences
//    SharedPreferences.saveSelectedPosition(requireContext(), nextPosition)
//
//    // Update the UI with data at the new position
//    updateUI(isAllahSelected)
//}
//
//private fun getMaxPosition(isAllahSelected: Boolean): Int {
//    return if (isAllahSelected) {
//        QiblaApp.allahNamesImages.size - 1
//    } else {
//        QiblaApp.rasoolNamesImages.size - 1
//    }
//}
//private fun navigateToPreviousPosition(isAllahSelected: Boolean) {
//    val currentPosition = SharedPreferences.getSelectedPosition(requireContext())
//    val previousPosition = if (currentPosition > 0) currentPosition - 1 else currentPosition
//
//    // Save the updated position in SharedPreferences
//    SharedPreferences.saveSelectedPosition(requireContext(), previousPosition)
//
//    // Update the UI with data at the new position
//    updateUI(isAllahSelected)
//}


//        // Retrieve the saved position from SharedPreferences
//        val savedPosition = SharedPreferences.getSelectedPosition(mContext)
//        Log.d(
//            NameDetailFragment::class.java.simpleName,
//            "onViewCreated: selected position$savedPosition "
//        )
//
///// Check if Allah or Rasool is selected
//        val isAllahSelected = SharedPreferences.isAllahSelected(requireContext())
//
//        Log.d(NameDetailFragment::class.java.simpleName, "isAllahSelected: $isAllahSelected")
//        // Retrieve arrays/lists based on the selection
//        val nameImages = if (isAllahSelected) QiblaApp.allahNamesImages else QiblaApp.rasoolNamesImages
//        val numberImages = QiblaApp.numberImages
//        val namesTranslations = if (isAllahSelected) QiblaApp.allahNamesTranslations else QiblaApp.rasoolNamesTranslations
//        val audioResources = if (isAllahSelected) QiblaApp.audioAllahResources else QiblaApp.audioRasoolResources
//
//// Check if the selected position is within the bounds of the arrays/lists
//        if (savedPosition in nameImages.indices && savedPosition in numberImages.indices &&
//            savedPosition in namesTranslations.indices && savedPosition in audioResources.indices
//        ) {
//            // Set data to the ImageView for Allah Name
//            val allahNameImage = nameImages[savedPosition]
//            mainImage.setImageResource(allahNameImage)
//            Log.d(
//                NameDetailFragment::class.simpleName,
//                "onViewCreated:allahNameImage $allahNameImage "
//            )
//
//
//            // Set data to the ImageView for Number Image
//            val numberImage = numberImages[savedPosition]
//            numberImageView.setImageResource(numberImage)
//            Log.d(NameDetailFragment::class.simpleName, "onViewCreated:numberImage $numberImage ")
//
//            // Set data to the TextViews for Urdu and English translations
//            val (urduTranslation, englishTranslation) = namesTranslations[savedPosition]
//            binding.tvNameTranslationUrdu.text = urduTranslation
//            binding.tvNameTranslationEnglish.text = englishTranslation
//
//
//            binding.imgPlayer.setOnClickListener {
//                // Check if the MediaPlayer is already playing
//                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                    // If it's playing, stop and release the MediaPlayer
//                    mediaPlayer!!.stop()
//                    mediaPlayer!!.release()
//                    mediaPlayer = null
//                }
//
//                // Retrieve the audio resource ID from the audioResources list
//                val audioResource = audioResources[savedPosition]
//                Log.d(NameDetailFragment::class.simpleName, "onViewCreated:audio $audioResources ")
//
//                // Initialize the MediaPlayer with the audio resource
//                mediaPlayer = MediaPlayer.create(requireContext(), audioResource)
//
//                // Start playing the audio
//                mediaPlayer?.start()
//            }
//
//
//        } else {
//            // Handle the case when the selected position is out of bounds
//            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show()
//        }
//
//        val selectedName = SharedPreferences.getSelectedName(mContext)
//
//        Log.d(
//            NameDetailFragment::class.java.simpleName,
//            "onViewCreated:selected names $selectedName"
//        )
//        toolbar.titleCounter.text = selectedName

//val sharedPreferences =
//    requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
////        val selectedNameImage =
//            sharedPreferences.getInt("selected_name_image", 0) // Default value 0 if not found
//        val selectedNameNumberImage = sharedPreferences.getInt("selected_name_number_image", 0)
//// Get selected Allah name from shared preferences
//        val selectedPosition = sharedPreferences.getInt("selected_allah_position", -1)
//        val selectedName = if (selectedPosition != -1) PREFS_SELECTED_KEY_ALLAH[selectedPosition] else "No name selected"


// Set the retrieved values to your ImageViews
//   binding.imgMainName.setImageResource(selectedNameImage)
//   binding.imgNameNumber.setImageResource(selectedNameNumberImage)

//  val urduTranslation = sharedPreferences.getString("selected_translation_urdu", "")
//val englishTranslation = sharedPreferences.getString("selected_translation_english", "")
// Retrieve the translation pair corresponding to the selected position
//  val selectedPosition = SharedPreferences.getSelectedPlayerPosition(requireContext())

//     val selectedTranslationPair = allahNamesTranslations[selectedPosition]

// Set the translations to the TextViews
//     binding.tvNameTranslationUrdu.text = selectedTranslationPair.first
//   binding.tvNameTranslationEnglish.text = selectedTranslationPair.second
// Set the translations to the TextViews
//        binding.tvNameTranslationUrdu.text = urduTranslation
//        binding.tvNameTranslationEnglish.text = englishTranslation
//
//        val isAllahNamesSelected =
//            SharedPreferences.getSelectionFromSharedPreferencesDetail(mContext)
//// Retrieve selected name from shared preferences
//        val selectedAllahName = sharedPreferences.getString("selected_allah_name", "")
//        val selectedRasoolName = sharedPreferences.getString("selected_rasool_name", "")
// Set the selected name on the TextView


// Set the name on the TextView
// view.findViewById<TextView>(R.id.textView).text = storedName


// Retrieve the stored position from SharedPreferences
//    val selectedPosition = SharedPreferences.getSelectedPlayerPosition(requireContext())

// Initialize data from SharedPreferences
// imageResource = loadFromSharedPreferences()
// Update UI based on the selected position
// updateUI(selectedPosition)
//binding.imgBackward.setOnClickListener {
//    Log.d(NameDetailFragment::class.simpleName, "onViewCreated: Button Backward Clicked.")
//    // Decrement currentPosition to move to the previous position
//    // val previousPosition =
//    //    if (selectedPosition > 0) selectedPosition - 1 else selectedPosition
//    // Save the updated position in SharedPreferences
//    //  SharedPreferences.saveSelectedPlayerPosition(requireContext(), previousPosition)
//    // Update the UI with data at the new position
//    // updateUI(previousPosition)
//    // }
//}

//
//    private fun navigateToPreviousPosition() {
//        val currentPosition = getSelectedPlayerPosition(requireContext())
//        val previousPosition = if (currentPosition > 0) currentPosition - 1 else currentPosition
//        SharedPreferences.saveSelectedPlayerPosition(requireContext(), previousPosition)
//     //   updateUI(previousPosition)
//    }

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

//        override fun onDestroyView() {
//            super.onDestroyView()
//            // Release the MediaPlayer resources when the fragment is destroyed
//            mediaPlayer.release()
//        }


// Set the selected name on the TextView
//        // Set the text based on the selection
//        if (isAllahNamesSelected) {
//            // Set text for Allah Names
//            toolbar.titleCounter.text = getString(R.string.allah_names)
//        } else {
//            // Set text for Rasool Names
//            toolbar.titleCounter.text = getString(R.string.rasool_names)
//        }
// Retrieve the stored position from SharedPreferences
//val selectedPosition = getSelectedPlayerPosition(requireContext())

// Define an array of audio file resource IDs