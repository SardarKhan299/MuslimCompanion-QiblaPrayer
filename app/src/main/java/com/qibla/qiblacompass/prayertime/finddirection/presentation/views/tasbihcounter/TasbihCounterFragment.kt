package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.graphics.Point
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    private lateinit var imageView2: ImageView
    private lateinit var arcView: ArcView
    private lateinit var imgFirst: ImageView
    private lateinit var imgZero: ImageView
    private lateinit var imgZeroBottom: ImageView
    private lateinit var imgSecond: ImageView
    private lateinit var imgAnimated: ImageView
    private lateinit var imgFirstBottom: ImageView
    private lateinit var counterTextView: TextView
    private var counter = 0
    private var maxCounter = 100
    var x1 = 0.0f
    var x2 = 0.0f
    var MIN_DISTANCE = 100
    lateinit var adapter: TasbihCounterAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
    private var mediaPlayer: MediaPlayer? = null
    private var userClicked = 0
    private val imageResources = listOf(
        R.drawable.ic_counter_one,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m13,
        R.drawable.m14,
    )

    var selectedImageName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TasbihCounterFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        imageView2 = binding.layoutTasbihCounterFragment.findViewById(R.id.img_bottom_count_counter)
        arcView = binding.layoutTasbihCounterFragment.findViewById(R.id.arcView)
        imgZero = binding.layoutTasbihCounterFragment.findViewById(R.id.img_top_counter_zero)
        imgZeroBottom = binding.layoutTasbihCounterFragment.findViewById(R.id.img_bottom_counter_zero)
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


        selectedImageName = SharedPreferences.retrieveImageValue(requireContext())!!
        Log.d(TasbihCounterFragment::class.simpleName, "Selected image name: $selectedImageName")
        // Map the image name to the corresponding resource ID
        val imageResource = TasbihZhikrUtil.getImageResource(selectedImageName ?: "")
        Log.d(TasbihCounterFragment::class.simpleName, "onViewCreated:$imageResource ")
        // Set the image resource to the ImageView
        imageView.setImageResource(imageResource)
        saveImageValue(requireContext(), selectedImageName.toString())
             // Retrieve the saved counter value from SharedPreferences
        val counterValue = SharedPreferences.retrieveIncrementalCounter(requireContext(),selectedImageName)
        counter = counterValue

        // Update the increment text with the saved counter value
        counterTextView.text = counter.toString()

        val digitalCounter = binding.layoutCounterType
        digitalCounter.viewDigitalTasbih.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.digitalTasbihFragment)
        }
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        adapter = TasbihCounterAdapter(imageResources) { selectedImage ->
            Log.d(TasbihCounterFragment::class.simpleName, "onViewCreated: ImageSelected")
            // Handle the click event here to set the selected image to another ImageView
            // For example, if you have an ImageView called 'selectedImageView'
            imgZero.setImageResource(selectedImage)
            imgZeroBottom.setImageResource(selectedImage)
            imgFirst.setImageResource(selectedImage)
            imgSecond.setImageResource(selectedImage)
            imgAnimated.setImageResource(selectedImage)
            imgFirstBottom.setImageResource(selectedImage)
            imageView2.setImageResource(selectedImage)
        }
        recyclerView.adapter = adapter
        //adapter.notifyDataSetChanged()
        digitalCounter.viewSetCounter.setOnClickListener {
            Log.d(
                TasbihCounterFragment::class.java.simpleName,
                "onViewCreated: viewSetCounter Clicked.."
            )
            showBottomSheetSetCounter()
        }
        binding.imgReset.setOnClickListener {
            Log.d(TasbihCounterFragment::class.java.simpleName, "onViewCreated: imgReset Clicked..")
            showBottomSheetSetCounter()
        }


/// Retrieve the stored entered value from SharedPreferences
        val enteredValue = SharedPreferences.retrieveEnteredValue(requireContext(),selectedImageName)

// Update the counter TextView with the retrieved entered value
        binding.tvCount.text = enteredValue.toString()

        view1.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                x1 = motionEvent.x
                Log.d(TasbihCounterFragment::class.simpleName, "ACTION_DOWN: ..User Clicked " +
                        "View X is "+view1.width +" Touch x1 is "+x1)
            }else if(motionEvent.action == MotionEvent.ACTION_MOVE){
                x2 = motionEvent.x
                val deltaX: Float = x2 - x1

                    // Calculate progress based on touch movement and desired range
                    val progress = kotlin.math.min(
                        kotlin.math.max(deltaX / 500.toFloat(), 0f), 1f
                    )
                    Log.d(TasbihCounterFragment::class.simpleName, "ACTION_MOVE: Delta X is "+deltaX+" Progress is "+progress)
                if (x2 > x1) {
                    Log.d(TasbihCounterFragment::class.simpleName, "ACTION_MOVE: Left to Right swipe [Next]")
                    motionLayout.setTransition(R.id.transition)
                } else {
                    Log.d(TasbihCounterFragment::class.simpleName, "ACTION_MOVE: Right to Left swipe [Previous]")
                    motionLayout.setTransition(R.id.transition1)
                }
                    // Update MotionLayout progress
                    motionLayout.progress = progress

            } else if(motionEvent.action == MotionEvent.ACTION_UP){
                x2 = motionEvent.x
                val deltaX: Float = x2 - x1
                Log.d(TasbihCounterFragment::class.simpleName, "ACTION_UP: "+Math.abs(deltaX))
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        Log.d(TasbihCounterFragment::class.simpleName, "ACTION_UP: Left to Right swipe [Next]")
                        checkValueAndGoForward()
                    } else {
                        Log.d(TasbihCounterFragment::class.simpleName, "ACTION_UP: Right to Left swipe [Previous]")
                        motionLayout.setTransition(R.id.transition1)
                        motionLayout.progress = 0.2f
                        checkValueAndGoBackward()
                    }
                } else {
                    // consider as something else - a screen tap for example
                    Log.d(TasbihCounterFragment::class.simpleName, "ACTION_UP: its a click")
                    if(Math.abs(deltaX)<=1) {
                        motionLayout.setTransition(R.id.transition)
                        motionLayout.progress = 0.2f
                        checkValueAndGoForward()
                    }
//                    if(x1>= view1.width/2){
//                        Log.d(TasbihCounterFragment::class.simpleName, "onViewCreated: Go Forward")
//                        checkValueAndGoForward(true)
//                    }else{
//                        Log.d(TasbihCounterFragment::class.simpleName, "onViewCreated: Go Backward")
//                        checkValueAndGoBackward(true)
//                    }
                }

            }
            // Return false to indicate that the listener has not consumed the event
            true
        }

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                //Log.d(TasbihCounterFragment::class.simpleName, "onTransitionStarted:  "+p0?.targetPosition)
                    // Calculate positions of images based on progress
                val startX = binding.guidelineLeftTasbihCounter.x
                val startY = imgFirst.y + imageView2.height / 2
                val endX = binding.guidelineRightTasbihCounter.x
                val endY = imageView2.y + imageView2.height / 2
                // Update the arc view's path
                //Log.d(TasbihCounterFragment::class.simpleName, "onTransitionStarted: $startX - $startY : $endX - $endY")
                arcView.updatePath(startX, startY, endX, endY)
            }
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                //Log.d(TasbihCounterFragment::class.simpleName, "onTransitionChange: Transition ")
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                //Log.d(TasbihCounterFragment::class.simpleName, "onTransitionCompleted: ")
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                //Log.d(TasbihCounterFragment::class.simpleName, "onTransitionTrigger: ")
            }
        })
    }

    private fun checkValueAndGoBackward() {
        // Retrieve the stored entered value from SharedPreferences
        val enteredValue =
            SharedPreferences.retrieveEnteredValue(requireContext(), selectedImageName)

        // Check if the current counter is greater than 0
        if (counter > 0) {
            startMotionLayout()

            Log.d(TasbihCounterFragment::class.simpleName, "checkValueAndGoBackward: ${motionLayout.currentState}")
            if(motionLayout.currentState == -1) {
                motionLayout.setTransition(R.id.transition1)
                motionLayout.transitionToEnd()
            }

            // Update the counter
            updateDecrementCounter()
            // Play the MP3 sound
            playSound()
            // Check if the counter has reached the entered value
            if (counter == enteredValue) {
                // Stop the MotionLayout animation
                stopMotionLayout()

                // Show a toast message indicating maximum count reached
                Toast.makeText(
                    mContext,
                    "You've reached the minimum count.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // If the counter is equal or greater than the entered value
            // Stop the MotionLayout animation
            stopMotionLayout()

            // Show a toast message indicating maximum count reached
            Toast.makeText(
                mContext,
                "You've reached the minimum count.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkValueAndGoForward() {
        // Retrieve the stored entered value from SharedPreferences
        val enteredValue =
            SharedPreferences.retrieveEnteredValue(requireContext(), selectedImageName)

        // Check if the current counter is less than the entered value
        if (counter < enteredValue) {
            startMotionLayout()

            Log.d(TasbihCounterFragment::class.simpleName, "checkValueAndGoForward: ${motionLayout.currentState}")
            if(motionLayout.currentState==-1) {
                motionLayout.setTransition(R.id.transition)
                motionLayout.transitionToEnd()
            }

            // Update the counter
            updateIncrementalCounter()
            // Play the MP3 sound
            playSound()
            // Check if the counter has reached the entered value
            if (counter == enteredValue) {
                // Stop the MotionLayout animation
                stopMotionLayout()

                // Show a toast message indicating maximum count reached
                Toast.makeText(
                    mContext,
                    "You've reached the maximum count.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // If the counter is equal or greater than the entered value
            // Stop the MotionLayout animation
            stopMotionLayout()

            // Show a toast message indicating maximum count reached
            Toast.makeText(
                mContext,
                "You've reached the maximum count.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun stopMotionLayout() {
        Log.d(TasbihCounterFragment::class.simpleName, "stopMotionLayout: stop motion layout")
        //motionLayout.isInteractionEnabled = false
    }

    private fun startMotionLayout() {
        Log.d(TasbihCounterFragment::class.simpleName, "startMotionLayout: start motion layout")
        //motionLayout.isInteractionEnabled = true
    }

    private fun updateCount(value: Int) {
        maxCounter = value
        binding.tvCount.text = maxCounter.toString()
    }

    private fun updateIncrementalCounter() {
        counter++
        counterTextView.text = counter.toString()
        // Save the counter value to SharedPreferences
        SharedPreferences.saveIncrementalCounter(mContext, counter,selectedImageName)
    }

    private fun updateDecrementCounter() {
        counter--
        counterTextView.text = counter.toString()
        // Save the counter value to SharedPreferences
        SharedPreferences.saveIncrementalCounter(mContext, counter,selectedImageName)
    }

    private fun showBottomSheetSetCounter() {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_sheet_set_counter, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        val cancelButton: Button = bottomSheetView.findViewById(R.id.btn_set_counter_cancel)
        val continueButton: Button = bottomSheetView.findViewById(R.id.btn_set_counter_continue)
        val valueCounter: EditText = bottomSheetView.findViewById(R.id.edt_counter)
        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        continueButton.setOnClickListener {
            val enteredValue = valueCounter.text.toString().toIntOrNull() ?: 0
            if (enteredValue != 0) {
                // updateCount(enteredValue)
                updateMaxCounter(enteredValue)
                SharedPreferences.saveEnteredValue(mContext, enteredValue,selectedImageName)

                bottomSheetDialog.dismiss()
            } else {
                // Show a toast or an error message indicating that zero is not allowed
                Toast.makeText(requireContext(), "Zero is not allowed", Toast.LENGTH_SHORT).show()
            }
        }
        bottomSheetDialog.show()
    }


    private fun updateMaxCounter(newValue: Int) {
        maxCounter = newValue
        binding.tvCount.text = maxCounter.toString()
    }


    override fun onResume() {
        super.onResume()
        Log.d(TasbihCounterFragment::class.simpleName, "onResume: ")
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        Log.d(TasbihCounterFragment::class.simpleName, "onResume: $width - $height")

        val startX = 0
        val startY = height/26
        val endX = width + width
        val endY = height/26
        arcView.updatePath(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer when the fragment is destroyed
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun View.getLocationOnScreen(): Point {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return Point(location[0],location[1])
    }

    private fun playSound() {
        // Initialize MediaPlayer with the MP3 file
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.tasbih_sound)
        mediaPlayer?.start()

        // Release MediaPlayer when sound finishes playing
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer.release()
        }
    }

    object TasbihZhikrUtil {
        fun getImageResource(imageName: String?): Int {
            return when (imageName) {
                SUBHAN_ALLAH -> R.drawable.ic_subhan_allah
                ALHAMDULILLAH -> R.drawable.allhamdulillah
                LA_ILAHA_ILLA_ALLAH -> R.drawable.laillaha_ic
                ALLAHU_AKBAR -> R.drawable.allahoakbar
                else -> R.drawable.ic_hadith // Provide a default image resource ID
            }
        }
    }
}
