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
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.app.QiblaApp
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.*
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNameDetailBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment


class NameDetailFragment : BaseFragment<FragmentNameDetailBinding>(R.layout.fragment_name_detail) {
    private var mediaPlayer: MediaPlayer? = null
    lateinit var mainImage: ImageView
    lateinit var numberImageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private lateinit var touchableViews: List<View>
    private lateinit var adView: AdView
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
        MobileAds.initialize(mContext) {
            Log.d(
                DashBoardFragment::class.java.simpleName,
                "onViewCreated: onInitializationCompleted"
            )
        }
        adView = binding.adsBannerNameDetail
        // Initialize AdUtil and load the banner ad
        AdUtil.initialize(requireContext(), adView)
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
    override fun onResume() {
        adView.resume()
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    companion object{
        private val TAG = "NameDetailFragment"
    }
}