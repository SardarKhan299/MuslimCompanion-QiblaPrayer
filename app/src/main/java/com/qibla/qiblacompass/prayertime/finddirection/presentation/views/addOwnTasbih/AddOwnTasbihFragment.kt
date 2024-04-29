package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.addOwnTasbih

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.invisible
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAddOwnTasbihBinding


class AddOwnTasbihFragment :
    BaseFragment<FragmentAddOwnTasbihBinding>(R.layout.fragment_add_own_tasbih) {
    private lateinit var imageView: ImageView
    var PERMISSION_CAMERA = arrayOf(Manifest.permission.CAMERA)
    lateinit var profileBitmap: Bitmap
    var tasbihName = ""
    private var uri: Uri? = null

    lateinit var edtTasbihName: EditText


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
        edtTasbihName = binding.edtTasbihName
        enterMustDataEditText()
        edtTasbihName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // EditText lost focus, hide the keyboard
                hideKeyboard(mContext, edtTasbihName)
            }
        }
        binding.viewCaptureImageCamera.setOnClickListener {

            invisibleGroup()
            captureImageFromCamera()
        }

        binding.imgUploadGalleryNewTasbih.setOnClickListener {
            invisibleGroup()
            openCropActivity(includeCamera = false, includeGallery = true)

        }
        binding.imgCloseTasbihIcon.setOnClickListener {
            binding.groupCaptureUploadImage.visible()
            imageView.setImageResource(R.drawable.ic_new_tasbih)
            // Clear the stored data from SharedPreferences
            SharedPreferences.clearTasbihImageUriAndTasbihName(mContext)
        }
        binding.imgTickTasbihIcon.setOnClickListener {
            if (validateTasbih()) {
                val data = saveTasbihImageUriAndNameToSharedPreferences()
                Log.d(AddOwnTasbihFragment::class.simpleName, "saveTasbihImageUriAndNameToSharedPreferences $data")
                findNavController().navigate(R.id.tasbihFragment)
            }
        }
    }

    private fun invisibleGroup() {
        Handler(Looper.getMainLooper()).postDelayed({ binding.groupCaptureUploadImage.invisible() }, 10000)
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
            uri = result.uriContent!!
            // Pass the image URI to setBitmapGlide or validateTasbih
            setBitmapGlide(uri!!)
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
        tasbihName = edtTasbihName.text.toString()

        if (tasbihName.trim().isEmpty()) {
            binding.layoutTextTasbihName.error = getString(R.string.tasbih_name)
            return false
        } else {
            binding.layoutTextTasbihName.error = null
        }

        return true
    }

    private fun enterMustDataEditText() {
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
    }

    fun hideKeyboard(context: Context, editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun saveTasbihImageUriAndNameToSharedPreferences() {
        val tasbihName = edtTasbihName.text.toString().trim()
        val uriString = uri?.toString() ?: ""

        SharedPreferences.saveDataTasbihName(mContext, tasbihName)
        SharedPreferences.saveDataTasbihImageUri(mContext, uriString)
    }
}

