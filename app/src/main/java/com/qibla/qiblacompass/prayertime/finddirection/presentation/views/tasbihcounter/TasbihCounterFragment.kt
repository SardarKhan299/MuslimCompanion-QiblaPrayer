package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.Navigation
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
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.saveImageValue
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
    private lateinit var imageView: ImageView
    private val imageResources = listOf(
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m13,
        R.drawable.m14,
        )

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
        saveImageValue(requireContext(), selectedImageName.toString())




        val digitalCounter = binding.layoutCounterType
        digitalCounter.viewDigitalTasbih.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.digitalTasbihFragment)

        }

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
