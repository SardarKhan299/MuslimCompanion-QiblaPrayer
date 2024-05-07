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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.invisible
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAddOwnTasbihBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih.ZhikrTasbih
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
        databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
        //   databaseReference = FirebaseDatabase.getInstance().reference.child("ZhikrTasbih")
//        val database = Firebase.database
//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")
//        // Read from the database
//        myRef.addValueEventListener(object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = snapshot.getValue<String>()
//                Log.d(AddOwnTasbihFragment::class.java.simpleName, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(AddOwnTasbihFragment::class.java.simpleName, "Failed to read value.", error.toException())
//            }
//
//        })
        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        // Initialize Firebase Database
//        databaseReference = FirebaseDatabase.getInstance().reference
//
//        // Check if user is signed in
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // User is signed in, you can perform database operations
//            writeToDatabase()
//            readFromDatabase()
//        } else {
//            // No user is signed in, prompt the user to sign in
//            // You can implement your sign-in flow here
//            // For example, start a sign-in activity
//        }

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
                //   val data = saveTasbihImageUriAndNameToSharedPreferences()
                //   Log.d(AddOwnTasbihFragment::class.simpleName, "saveTasbihImageUriAndNameToSharedPreferences $data")
                uri?.let {
                    saveImageToFirebaseStorage(it)
                    Log.d(AddOwnTasbihFragment::class.java.simpleName, "onViewCreated:$it ")
                    //  findNavController().navigate(R.id.tasbihFragment)
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
                    // Get current user ID
                    //  val userId = FirebaseAuth.getInstance().currentUser?.uid
                    //  userId?.let {
                    // Save Tasbih data with image URL and user ID
                    saveTasbihDataToFirebase(tasbihName , imageUrl = it.toString())
                    // }
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


    //    private fun saveTasbihDataToFirebase(zhikrName: String, zhikrImageUrl: String) {
//
//      val zhikrTasbih = ZhikrTasbih(zhikrName,zhikrImageUrl)
//          databaseReference.child(zhikrName).setValue(zhikrTasbih)
//
//                .addOnSuccessListener {
//                    binding.edtTasbihName.text!!.clear()
//                    Log.d(AddOwnTasbihFragment::class.java.simpleName, "Zikr data saved successfully")
//               findNavController().navigate(R.id.tasbihFragment)
//                }
//                .addOnFailureListener { exception ->
//                    exception.printStackTrace()
//                    Log.e(AddOwnTasbihFragment::class.java.simpleName, "Failed to save Zikr data: ${exception.message}")
//                }
//    }
    private fun saveTasbihDataToFirebase(zhikrName :String,imageUrl: String) {

        val zhikrTasbih = ZhikrTasbih(zhikrName,imageUrl)

        val key = databaseReference.push().key
        if (key != null) {
            databaseReference.child(key).setValue(zhikrTasbih)
                .addOnSuccessListener {

                    Log.d(
                        AddOwnTasbihFragment::class.java.simpleName,
                        "Tasbih data saved successfully"
                    )
                    findNavController().navigate(R.id.tasbihFragment)
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    Log.e(
                        AddOwnTasbihFragment::class.java.simpleName,
                        "Failed to save Tasbih data: ${exception.message}"
                    )
                }
        }

    }
}




















































//    private fun saveTasbihDataToFirebase(imageUrl: String) {
//        // Construct the database reference to the ZhikrTasbih table under the user's ID
//
//
//        val tasbihName = edtTasbihName.text.toString()
//        val imgUrl = imageUrl
//     //   val zhikrTasbihUrl = ZhikrTasbih(tasbihName, imageUrl)
//        val zhikrTasbih = hashMapOf(
//            "zhikrName " to tasbihName,
//            "zhikrImageUrl" to imgUrl
//        )
//
//        val key = databaseReference.push().key
//        if (key != null) {
//            databaseReference.child(key).setValue(zhikrTasbih)
//                .addOnSuccessListener {
//
//                    Log.d(
//                        AddOwnTasbihFragment::class.java.simpleName,
//                        "Tasbih data saved successfully"
//                    )
//                    findNavController().navigate(R.id.tasbihFragment)
//                }
//                .addOnFailureListener { exception ->
//                    exception.printStackTrace()
//                    Log.e(
//                        AddOwnTasbihFragment::class.java.simpleName,
//                        "Failed to save Tasbih data: ${exception.message}"
//                    )
//                }
//        }



//    private fun saveTasbihDataToFirebase(imageUrl: String) {
//        val databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
//
//        val tasbihName = edtTasbihName.text.toString()
//        val zhikrTasbih = ZhikrTasbih(tasbihName, imageUrl)
//
//        val key = databaseReference.push().key
//        if (key != null) {
//           databaseReference.child(key).setValue(zhikrTasbih)
//                .addOnSuccessListener {
//                    Log.d(AddOwnTasbihFragment::class.java.simpleName, "Tasbih data saved successfully ${databaseReference.child(key).setValue(zhikrTasbih)}")
//                    findNavController().navigate(R.id.tasbihFragment)
//                }
//                .addOnFailureListener { exception ->
//                    exception.message
//                    Log.e(AddOwnTasbihFragment::class.java.simpleName, "Failed to save Tasbih data: ${exception.message}")
//                }
//        }
//    }


//    private fun saveTasbihImageUriAndNameToSharedPreferences() {
//        val tasbihName = edtTasbihName.text.toString().trim()
//        val uriString = uri?.toString() ?: ""
//
//        SharedPreferences.saveDataTasbihName(mContext, tasbihName)
//        SharedPreferences.saveDataTasbihImageUri(mContext, uriString)
//    }


//    private fun saveTasbihImageUriAndNameToFirebaseStorage() {
//        val tasbihName = edtTasbihName.text.toString().trim()
//
//        // Check if the image URI is not null
//        uri?.let { imageUri ->
//            // Generate a unique file name for the image
//            val imageName = UUID.randomUUID().toString()
//            // Reference to the Firebase Storage location
//            val storageRef = FirebaseStorage.getInstance().reference.child("tasbih_images/$imageName")
//
//            // Upload the image to Firebase Storage
//            storageRef.putFile(imageUri)
//                .addOnSuccessListener { taskSnapshot ->
//                    // Image uploaded successfully, get the download URL
//                    storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
//                        // Save the image URL and Tasbih name to the Realtime Database
//                        saveTasbihDataToFirebaseDatabase(tasbihName, imageUrl.toString())
//                    }.addOnFailureListener { exception ->
//                        // Handle failure to get image URL
//                        Log.e(AddOwnTasbihFragment::class.java.simpleName, "Failed to get image URL: ${exception.message}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    // Handle failure to upload image
//                    Log.e(AddOwnTasbihFragment::class.java.simpleName, "Failed to upload image: ${exception.message}")
//                }
//        }
//    }
//
//    private fun saveTasbihDataToFirebaseDatabase(tasbihName: String, imageUrl: String) {
//        // Get a reference to the "ZhikrTasbih" node in your Realtime Database
//        val databaseReference = FirebaseDatabase.getInstance().getReference("ZhikrTasbih")
//        // Generate a unique key for the new data entry
//        val key = databaseReference.push().key
//
//        // Create a map to store the Tasbih data
//        val tasbihData = HashMap<String, Any>()
//        tasbihData["zhikrName"] = tasbihName
//        tasbihData["zhikrImageUrl"] = imageUrl
//
//        // Set the data in the Realtime Database under the generated key
//        if (key != null) {
//            databaseReference.child(key).setValue(tasbihData)
//                .addOnSuccessListener {
//                    Log.d(AddOwnTasbihFragment::class.java.simpleName, "Tasbih data saved successfully")
//                    // Navigate to the desired destination after saving data
//                    findNavController().navigate(R.id.tasbihFragment)
//                }
//                .addOnFailureListener { exception ->
//                    // Handle failure to save data
//                    Log.e(AddOwnTasbihFragment::class.java.simpleName, "Failed to save Tasbih data: ${exception.message}")
//                }
//        }
//    }


