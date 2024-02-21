package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.digitaltasbih

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.retrieveImageValue
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDigitalTasbihBinding


class DigitalTasbihFragment :
    BaseFragment<FragmentDigitalTasbihBinding>(R.layout.fragment_digital_tasbih) {

    private lateinit var counterTextView: TextView
    private lateinit var decrementButton: ImageView
    private lateinit var mainIncrementButton: ImageView
    private lateinit var resetButton: ImageView
    private lateinit var imageView: ImageView

    private var counterValue = 0
    var selectedImageName = ""
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
        toolbarText.text = getString(R.string.digital_tasbih)
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        counterTextView = binding.tvDigitalCounter
        counterTextView.text = counterValue.toString()


        decrementButton = binding.imgMinusDigitalCounter
        resetButton = binding.imgResetDigitalCounter
        mainIncrementButton = binding.imgIncrementDigitalCounter
        imageView = binding.imgTasbihDigitalCounter


        binding.layoutDigitalCounterType.tvDigitalTasbih.text = getString(R.string.analog_tasbih)
        binding.layoutDigitalCounterType.viewDigitalTasbih.setOnClickListener {
            //  Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
            findNavController().closeCurrentScreen()

        }
        toolbar.imgAddMore.setOnClickListener {
            val view = binding.layoutDigitalCounterType.groupCounterType
            if (view.visibility == View.VISIBLE) {
                binding.layoutDigitalCounterType.groupCounterType.visibility = View.GONE
            } else {
                binding.layoutDigitalCounterType.groupCounterType.visibility = View.VISIBLE
            }
        }


        selectedImageName = retrieveImageValue(requireContext())!!
// Map the image name to the corresponding resource ID using TasbihZhikrUtil
        val imageResource = TasbihZhikrUtil.getImageResource(selectedImageName)

        // Set the image resource to the ImageView
        imageView.setImageResource(imageResource)

        resetButton.setOnClickListener {
            resetCounter()
        }
        mainIncrementButton.setOnClickListener {
            incrementCounter()
        }
        decrementButton.setOnClickListener {
            decrementCounter()
        }

        // Retrieve the saved counter value from SharedPreferences only if it hasn't been initialized yet
        counterValue = SharedPreferences.retrieveIncrementalCounter(requireContext(),selectedImageName)

        // Update the counter TextView with the current counter value
        counterTextView.text = counterValue.toString()

    }

    private fun incrementCounter() {
        // Retrieve the entered value from SharedPreferences
        val enteredValue = SharedPreferences.retrieveEnteredValue(requireContext())

        // Check if the current counter value is less than the entered value
        if (counterValue < enteredValue) {
            // If less, increment the counter
            counterValue++
            updateCounter()
            // Save the updated counter value to SharedPreferences
            SharedPreferences.saveIncrementalCounter(requireContext(), counterValue,selectedImageName)
        } else {
            // If equal or greater, show a message indicating maximum count reached
            Toast.makeText(
                requireContext(),
                "You've reached the maximum count.",
                Toast.LENGTH_SHORT
            ).show()
        }
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
        // Save the updated counter value to SharedPreferences
        SharedPreferences.saveIncrementalCounter(requireContext(), counterValue,selectedImageName)
    }

    object TasbihZhikrUtil {
        fun getImageResource(imageName: String?): Int {
            return when (imageName) {
                ApplicationConstant.SUBHAN_ALLAH -> R.drawable.ic_subhan_allah
                ApplicationConstant.ALHAMDULILLAH -> R.drawable.allhamdulillah
                ApplicationConstant.LA_ILAHA_ILLA_ALLAH -> R.drawable.laillaha
                ApplicationConstant.ALLAHU_AKBAR -> R.drawable.allahoakbar
                else -> R.drawable.ic_hadith // Provide a default image resource ID
            }
        }
    }
}

