package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.digitaltasbih

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.retrieveImageValue
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDigitalTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter.TasbihCounterFragment


class DigitalTasbihFragment :
    BaseFragment<FragmentDigitalTasbihBinding>(R.layout.fragment_digital_tasbih) {

    private lateinit var counterTextView: TextView
    private lateinit var incrementButton: ImageView
    private lateinit var decrementButton: ImageView
    private lateinit var mainIncrementButton: ImageView
    private lateinit var resetButton: ImageView
    private lateinit var imageView: ImageView


    private var counterValue = 0
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
        toolbarText.text = getString(R.string.tasbih_counter)
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        counterTextView = binding.tvDigitalCounter
        incrementButton = binding.imgAddDigitalCounter
        decrementButton = binding.imgMinusDigitalCounter
        resetButton = binding.imgResetDigitalCounter
        mainIncrementButton = binding.imgIncrementDigitalCounter
        imageView = binding.imgTasbihDigitalCounter
        val retrievedImageName = retrieveImageValue(requireContext())
// Map the image name to the corresponding resource ID using TasbihZhikrUtil
        val imageResource = TasbihZhikrUtil.getImageResource(retrievedImageName)

        // Set the image resource to the ImageView
        imageView.setImageResource(imageResource)

        resetButton.setOnClickListener {
            resetCounter()
        }
        incrementButton.setOnClickListener {
            incrementCounter()
        }
        mainIncrementButton.setOnClickListener {
            incrementCounter()
        }
        decrementButton.setOnClickListener {
            decrementCounter()
        }
    }

    private fun incrementCounter() {
        counterValue++
        updateCounter()
    }

    private fun decrementCounter() {
        if (counterValue > 0) {
            counterValue--
            updateCounter()
        }
    }

    private fun resetCounter() {
        counterValue = 0
        updateCounter()
    }

    private fun updateCounter() {
        counterTextView.text = counterValue.toString()
    }

    object TasbihZhikrUtil {
        fun getImageResource(imageName: String?): Int {
            return when (imageName) {
                ApplicationConstant.SUBHAN_ALLAH -> R.drawable.ic_subhan_allah
                ApplicationConstant.ALHAMDULILLAH -> R.drawable.allhamdulillah
                ApplicationConstant.LA_ILAHA_ILLA_ALLAH -> R.drawable.laillaha
                ApplicationConstant.ALLAHU_AKBAR -> R.drawable.allahoakbar
                else -> R.drawable.ic_hathid // Provide a default image resource ID
            }
        }
    }
}

