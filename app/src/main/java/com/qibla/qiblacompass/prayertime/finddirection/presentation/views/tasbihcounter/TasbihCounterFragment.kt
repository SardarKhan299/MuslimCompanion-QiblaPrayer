package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihCounterBinding


class TasbihCounterFragment :
    BaseFragment<FragmentTasbihCounterBinding>(R.layout.fragment_tasbih_counter) {
    private lateinit var motionLayout: MotionLayout
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var arcView: ArcView
    private lateinit var counterTextView: TextView
    private var counter = 0
    private val maxCounter = 100
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

        motionLayout = binding.layoutTasbihCounterFragment.findViewById(R.id.motionLayout)
        imageView1 = binding.layoutTasbihCounterFragment.findViewById(R.id.img_top_counter_three)
        imageView2 = binding.layoutTasbihCounterFragment.findViewById(R.id.img_bottom_count_counter)
        arcView = binding.layoutTasbihCounterFragment.findViewById(R.id.arcView)
        counterTextView =
            binding.layoutTasbihCounterFragment.findViewById(R.id.tv_incremental_counter)
        val movingView =
            binding.layoutTasbihCounterFragment.findViewById<View>(R.id.view_animated_counter)
        // Set the initial text for the counterTextView
        counterTextView.text = counter.toString()
//        movingView.setOnClickListener {
//            // Increment the counter and update the TextView
//            counter++
//            counterTextView.text = counter.toString()
//        }
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // Calculate positions of images based on progress
                val startX = imageView1.x + imageView1.width / 2
                val startY = imageView1.y + imageView1.height / 2
                val endX = imageView2.x + imageView2.width / 2
                val endY = imageView2.y
                // Update the arc view's path
                arcView.updatePath(startX, startY, endX, endY)

//                // Increment the counter if it's less than the maximum limit
//                if (counter < maxCounter) {
//                    counter++
//                    counterTextView.text = counter.toString()
//                }
            }


            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {


                // Trigger the transition when needed
                // motionLayout.transitionToState(R.id.end) // Transition to the end state
//                movingView.setOnClickListener {
//                    if (motionLayout!!.currentState == R.id.start) {
//                        motionLayout.transitionToState(R.id.end)
//                    }
//                }
//                movingView.setOnClickListener {
////            // Increment the counter and update the TextView
//                    counter++
//                    counterTextView.text = counter.toString()
////
////                    motionLayout.setTransition(R.id.start) // Set your desired starting state
////                    motionLayout.transitionToEnd()
//                }
            }

        })

    }
}