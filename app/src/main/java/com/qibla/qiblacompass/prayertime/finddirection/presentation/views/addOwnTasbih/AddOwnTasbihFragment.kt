package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.addOwnTasbih

import android.Manifest
import android.content.Context
import android.content.Intent
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
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.gone
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.invisible
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAddOwnTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.ZhikrTasbih
import java.io.IOException
import java.util.Locale
import java.util.UUID


class AddOwnTasbihFragment :
    BaseFragment<FragmentAddOwnTasbihBinding>(R.layout.fragment_add_own_tasbih) {
    private lateinit var imageView: ImageView
    var PERMISSION_CAMERA = arrayOf(Manifest.permission.CAMERA)
    lateinit var profileBitmap: Bitmap
    var tasbihName = ""
    private var uri: Uri? = null

    lateinit var edtTasbihName: EditText

    // Firebase Auth instance
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

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
            findNavController().navigate(R.id.tasbihFragment)
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
   //     databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih").child("user")

        binding.viewCaptureImageCamera.setOnClickListener {

            invisibleGroup()
            captureImageFromCamera()
        }

        binding.imgUploadGalleryNewTasbih.setOnClickListener {

            openCropActivity(includeCamera = false, includeGallery = true)
            Handler(Looper.getMainLooper()).postDelayed(
                { binding.groupCaptureUploadImage.invisible() },
                1000
            )

        }
        binding.imgCloseTasbihIcon.setOnClickListener {
            binding.groupCaptureUploadImage.visible()
            imageView.setImageResource(R.drawable.ic_new_tasbih)
            // Clear the stored data from SharedPreferences
            SharedPreferences.clearTasbihImageUriAndTasbihName(mContext)
        }
        binding.imgTickTasbihIcon.setOnClickListener {
            if (validateTasbih()) {
                uri?.let {
                    // Save Tasbih data to Firebase only if user ID is not null
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        saveImageToFirebaseStorage(it)
                    } else {
                        // If user ID is null, show login dialog
                        showLoginDialog()
                    }
                }
            }
        }
    }

        private fun invisibleGroup() {
            Handler(Looper.getMainLooper()).postDelayed(
                { binding.groupCaptureUploadImage.invisible() },
                10000
            )
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
            if (isImageFormatValid(uri) && isImageSizeValid(uri)) {
                Glide.with(mContext)
                    .asBitmap()
                    .load(uri)
                    .placeholder(R.drawable.doc_avatar)
                    .error(R.drawable.doc_avatar)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            // Check image format
                            imageView.setImageBitmap(resource)
                            profileBitmap = resource

                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
            } else {
                Toast.makeText(mContext, "Not able to upload image", Toast.LENGTH_LONG).show()
            }

        }

        // Function to check if image format is valid (PNG or JPEG)
        private fun isImageFormatValid(uri: Uri): Boolean {
            val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            val mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension.toLowerCase(Locale.getDefault()))
            return mimeType == "image/jpeg" || mimeType == "image/png" || mimeType == "image/jpg"
        }

        // Function to check if image size is valid (less than or equal to 5MB)
        private fun isImageSizeValid(uri: Uri): Boolean {
            val inputStream = mContext.contentResolver.openInputStream(uri)
            return inputStream?.available() ?: 0 <= 5 * 1024 * 1024
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

        private fun saveImageToFirebaseStorage(imageUri: Uri) {
            val storageReference: StorageReference = FirebaseStorage.getInstance().reference
            val imageRef = storageReference.child("images/${UUID.randomUUID()}")

            val uploadTask: UploadTask = imageRef.putFile(imageUri)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    downloadUri?.let {
                            // Save Tasbih data with image URL and user ID
                            saveTasbihDataToFirebase(tasbihName, imageUrl = downloadUri.toString()) // Use download URL instead of imageUri.toString()

                    }
                } else {
                    // Handle errors
                    Log.e(
                        AddOwnTasbihFragment::class.java.simpleName,
                        "Failed to upload image: ${task.exception?.message}"
                    )
                }
            }
        }




    private fun saveTasbihDataToFirebase(zhikrName: String, imageUrl: String) {
        binding.groupProgressBar.visible()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { uid ->
            val userReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih").child(uid)
            val key = userReference.push().key
            key?.let { pushKey ->
                val zhikrData = HashMap<String, Any>()
                zhikrData["zhikrName"] = zhikrName
                zhikrData["zhikrImageUrl"] = imageUrl // Use the image URL obtained from Firebase Storage
                userReference.child(pushKey).setValue(zhikrData)
                    .addOnSuccessListener {
                        Log.d(
                            AddOwnTasbihFragment::class.java.simpleName,
                            "Tasbih data saved successfully"
                        )
                        findNavController().navigate(R.id.tasbihFragment)
                        binding.groupProgressBar.gone()
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                        Log.e(
                            AddOwnTasbihFragment::class.java.simpleName,
                            "Failed to save Tasbih data: ${exception.message}"
                        )
                    }
            }
        } ?: run {
            Log.e(
                AddOwnTasbihFragment::class.java.simpleName,
                "User is not logged in"
            )
            binding.groupProgressBar.visible()
        }
    }

    private fun showLoginDialog() {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("Login Required")
                setMessage("You need to login to perform this action. Do you want to login now?")
                setPositiveButton("Yes") { dialog, _ ->
                    // Navigate to login screen
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                setNegativeButton("No") { dialog, _ ->
                    // Navigate to previous screen
                    findNavController().popBackStack()
                    dialog.dismiss()
                }
            }
            alertDialogBuilder.create().show()
        }
    }



