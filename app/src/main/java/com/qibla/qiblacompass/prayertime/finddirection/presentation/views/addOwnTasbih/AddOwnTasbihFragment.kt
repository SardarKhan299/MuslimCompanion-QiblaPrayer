package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.addOwnTasbih

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.Constants
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.invisible
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAddOwnTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginFragment
import java.util.regex.Pattern


class AddOwnTasbihFragment :
    BaseFragment<FragmentAddOwnTasbihBinding>(R.layout.fragment_add_own_tasbih) {
    private lateinit var imageView: ImageView
    var PERMISSION_CAMERA = arrayOf(Manifest.permission.CAMERA)
    lateinit var profileBitmap: Bitmap
    var tasbihName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addOwnTasbihFragment =
                this@AddOwnTasbihFragment
        }
        val toolbar = binding.toolbarAddOwnTasbih
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.add_own_tasbih)
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        toolbar.imgAddMore.invisible()
        imageView = binding.imgNewTasbihBackground

        val nameInputLayout = binding.layoutTextTasbihName
        val stringNum = String.format(getString(R.string.name), "*")
        val startPosition = 4
        val endPosition = stringNum.length
        val spannableStr = SpannableString(stringNum)
        spannableStr.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    mContext,
                    R.color.zakat_point_heading_text_color
                )
            ),
            startPosition,
            endPosition,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        nameInputLayout.hint = spannableStr


        binding.viewCaptureImageCamera.setOnClickListener {
            captureImageFromCamera()
            binding.groupCaptureUploadImage.invisible()
            Handler().postDelayed({
                binding.groupCaptureUploadImage.invisible()
            }, 50000)
        }

        binding.imgUploadGalleryNewTasbih.setOnClickListener {
            openCropActivity(includeCamera = false, includeGallery = true)
            // binding.groupCaptureUploadImage.invisible()
            Handler().postDelayed({
                binding.groupCaptureUploadImage.invisible()
            }, 50000)
        }
        binding.imgCloseTasbihIcon.setOnClickListener {
            binding.groupCaptureUploadImage.visible()
            imageView.setImageResource(R.drawable.ic_new_tasbih)
        }
        binding.imgTickTasbihIcon.setOnClickListener {
            validateTasbih()
        }
    }


    private fun captureImageFromCamera() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCropActivity(includeCamera = true, includeGallery = false)
        } else {
            permReqLauncher.launch(PERMISSION_CAMERA)
        }
    }

    private fun openCropActivity(includeCamera: Boolean, includeGallery: Boolean) {
        cropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = includeCamera,
                    imageSourceIncludeGallery = includeGallery,
                    maxCropResultHeight = 1500,
                    maxCropResultWidth = 1500,
                    autoZoomEnabled = true
                ),
            ),
        )
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            setBitmapGlide(result.uriContent!!)
        } else {
            // An error occurred.
            val exception = result.error
            Log.d(AddOwnTasbihFragment::class.simpleName, ": $exception")
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                captureImageFromCamera()
            }
        }

    private fun setBitmapGlide(uri: Uri) {
        Glide.with(mContext)
            .asBitmap()
            .load(uri)
            .placeholder(R.drawable.doc_avatar)
            .error(R.drawable.doc_avatar)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.setImageBitmap(resource)
                    profileBitmap = resource
                    Log.d(
                        AddOwnTasbihFragment::class.simpleName,
                        "onResourceReady: ${profileBitmap.width} - ${profileBitmap.height}"
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }

    private fun validateTasbih(): Boolean {
        Log.d(AddOwnTasbihFragment::class.simpleName, " validateTasbih: ")
        tasbihName = binding.edtTasbihName.text.toString()

        if (tasbihName.trim().isEmpty()) {
            binding.layoutTextTasbihName.error = getString(R.string.tasbih_name)
            return false
        } else {
            binding.layoutTextTasbihName.error = null
        }

        return true
    }

    private fun hideCaptureUploadImageDelayed() {
        // Delayed action to hide the view after 500 milliseconds
        Handler().postDelayed({
            binding.groupCaptureUploadImage.invisible()
        }, DELAY_DURATION)
    }

    companion object {
        private const val DELAY_DURATION = 500L // Milliseconds
        private const val DELAY_BEFORE_HIDING_GROUP = 100L // Milliseconds
        private const val DELAY_BEFORE_OPENING_GALLERY = 200L // Milliseconds
    }
}


