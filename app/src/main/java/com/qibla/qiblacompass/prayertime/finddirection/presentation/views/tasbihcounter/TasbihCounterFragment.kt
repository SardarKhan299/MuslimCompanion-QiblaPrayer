package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALHAMDULILLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALLAHU_AKBAR
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.LA_ILAHA_ILLA_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.SUBHAN_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihCounterBinding

class TasbihCounterFragment :
    BaseFragment<FragmentTasbihCounterBinding>(R.layout.fragment_tasbih_counter) {
    private lateinit var motionLayout: MotionLayout
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var arcView: ArcView
    private lateinit var imgFirst: ImageView
    private lateinit var imgSecond: ImageView
    private lateinit var imgAnimated: ImageView
    private lateinit var imgFirstBottom: ImageView
    private lateinit var counterTextView: TextView
    private var counter = 0
    private val maxCounter = 100
    lateinit var recyclerView: RecyclerView
    private val imageResources = listOf(
        R.drawable.ic_counter_one,
        R.drawable.ic_counter_two,
        R.drawable.ic_counter_three,
        R.drawable.ic_counter_four,
        R.drawable.ic_counter_five,
        R.drawable.ic_counter_six,
        R.drawable.ic_counter_seven,
        R.drawable.ic_counter_eight,
        R.drawable.ic_counter_nine,
        R.drawable.ic_counter_ten,

        )
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TasbihCounterFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tasbihCounterFragment = this@TasbihCounterFragment
        }
        val toolbar = binding.toolbarTasbihCounter
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        //  binding.layoutCounterType.groupCounterType.visibility = View.GONE

        toolbar.imgAddMore.setOnClickListener {
            val view = binding.layoutCounterType.groupCounterType
            if (view.visibility == View.VISIBLE) {
                binding.layoutCounterType.groupCounterType.visibility = View.GONE
            } else {
                binding.layoutCounterType.groupCounterType.visibility = View.VISIBLE

            }

        }

        val includelayout = binding.include
        motionLayout = includelayout.motionLayout
        imageView1 = binding.layoutTasbihCounterFragment.findViewById(R.id.img_top_counter_three)
        imageView2 = binding.layoutTasbihCounterFragment.findViewById(R.id.img_bottom_count_counter)
        arcView = binding.layoutTasbihCounterFragment.findViewById(R.id.arcView)
        imgFirst = binding.layoutTasbihCounterFragment.findViewById(R.id.img_top_counter_one)
        imgSecond = binding.layoutTasbihCounterFragment.findViewById(R.id.img_top_counter_two)
        imgAnimated = binding.layoutTasbihCounterFragment.findViewById(R.id.img_animated_move)
        imgFirstBottom = binding.layoutTasbihCounterFragment.findViewById(R.id.img_bottom_counter)
        counterTextView =
            binding.layoutTasbihCounterFragment.findViewById(R.id.tv_incremental_counter)
        val view1 = motionLayout.findViewById<View>(R.id.view_animated_counter)
        recyclerView = binding.layoutTasbihCounterFragment.findViewById(R.id.recyclerView_tasbih_bg)
        counterTextView.text = counter.toString() // Set initial counter text
        imageView = binding.layoutTasbihCounterFragment.findViewById(R.id.img_tasbih)

        val selectedImageName = SharedPreferences.retrieveImageValue(requireContext())
        Log.d("TasbihCounterFragment :selectedImageName", "Selected image name: $selectedImageName")
           // Map the image name to the corresponding resource ID
        val imageResource = TasbihZhikrUtil.getImageResource(selectedImageName ?: "")
        Log.d("TasbihCounterFragment :imageResource ", "onViewCreated:$imageResource ")
        // Set the image resource to the ImageView
        imageView.setImageResource(imageResource)


        view1.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                Log.d("TasbihCounterFragment", "onViewCreated: motionAction....... ")
                Log.d("TasbihCounterFragment", "onViewCreated: view touch")
                if (counter < maxCounter) {
                    counter++
                    counterTextView.text = counter.toString()
                    Log.d("TasbihCounterFragment", "Counter incremented. New value: $counter")
                } else {
                    Log.d("TasbihCounterFragment", "Maximum count reached.")
                }
            }

            false
        }
        binding.imgReset.setOnClickListener {
            counter = 0
            counterTextView.text = "0"
        }
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                Log.d("TasbihCounterFragment", "onTransitionChange: Transition ")

                // Calculate positions of images based on progress
                val startX = imageView1.x + imageView1.width / 2
                val startY = imageView1.y + imageView1.height / 2
                val endX = imageView2.x + imageView2.width / 2
                val endY = imageView2.y
                // Update the arc view's path
                arcView.updatePath(startX, startY, endX, endY)

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )

        val adapter = TasbihCounterAdapter(imageResources) { selectedImage ->
            // Handle the click event here to set the selected image to another ImageView
            // For example, if you have an ImageView called 'selectedImageView'
            imageView1.setImageResource(selectedImage)
            imgFirst.setImageResource(selectedImage)
            imgSecond.setImageResource(selectedImage)
            imgFirst.setImageResource(selectedImage)
            imgAnimated.setImageResource(selectedImage)
            imgFirstBottom.setImageResource(selectedImage)
            imageView2.setImageResource(selectedImage)
        }
        recyclerView.adapter = adapter
    }

    private fun displayImage(selectedImage: String?) {
        when (selectedImage) {
            "Subhan Allah" -> imageView.setImageResource(R.drawable.ic_subhan_allah)
            "Alhamdulillah" -> imageView.setImageResource(R.drawable.allhamdulillah)
            "la ilaha illa Allah" -> imageView.setImageResource(R.drawable.laillaha)
            "Allahu Akbar" -> imageView.setImageResource(R.drawable.allahoakbar)
            else -> R.drawable.ic_hathid
        }
    }

    //    private fun getImageResource(imageName: String?): Int {
//        return when (imageName) {
//            ApplicationConstant.SUBHAN_ALLAH -> R.drawable.ic_subhan_allah
//            ApplicationConstant.ALHAMDULILLAH -> R.drawable.allhamdulillah
//            ApplicationConstant.LA_ILAHA_ILLA_ALLAH -> R.drawable.laillaha
//            ApplicationConstant.ALLAHU_AKBAR -> R.drawable.allahoakbar
//            else -> R.drawable.ic_hathid
//
//
//        }
//    }
    object TasbihZhikrUtil {
        fun getImageResource(imageName: String?): Int {
            return when (imageName) {
                SUBHAN_ALLAH -> R.drawable.ic_subhan_allah
                ALHAMDULILLAH -> R.drawable.allhamdulillah
                LA_ILAHA_ILLA_ALLAH -> R.drawable.laillaha
                ALLAHU_AKBAR -> R.drawable.allahoakbar
                else -> R.drawable.ic_hathid // Provide a default image resource ID
            }

        }


    }

}
//        val selectedImageName = SharedPreferences.retrieveImageValue()
//
//        when (selectedImageName) {
//            "Subhan Allah" -> selectedImage.setImageResource(R.drawable.ic_subhan_allah)
//            "Alhamdulillah" -> selectedImage.setImageResource(R.drawable.allhamdulillah)
//            "la ilaha illa Allah" -> selectedImage.setImageResource(R.drawable.laillaha)
//            "Allahu Akbar" -> selectedImage.setImageResource(R.drawable.allahoakbar)
//        }
//        val selectedImage = SharedPreferences.retrieveImageValue()
//        if (selectedImage != null) {
//            val imageView = view.findViewById<ImageView>(R.id.img_tasbih)
//            // Set the selected image to the ImageView
//            val imageResource = getImageResource(selectedImage.toString())
//            imageView.setImageResource(imageResource)
//        }
//        val selectedImage = SharedPreferences.retrieveImageValue()
//        if (!selectedImage.isNullOrEmpty()) {
//            val imageResource = getImageResource(selectedImage)
//            imageView.setImageResource(imageResource)
//            Log.d("TasbihCounterFragment", "onViewCreated:$selectedImage ")
//        }

//    companion object {
//        fun newInstance(selectedImage: TasbihZhikrData) = TasbihFragment().apply {
//            val sharedPrefHelper = SharedPreferences
//            sharedPrefHelper.saveImageValue(selectedImage.tvZhikr)
//        }
//    }


//        val selectedImage = SharedPreferences.retrieveImageValue()
//        Log.d("TasbihCounterFragment", "onViewCreated: $selectedImage")
//        displayImage(selectedImage)