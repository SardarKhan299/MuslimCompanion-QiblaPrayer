package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.Constants
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSignUpBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginFragment
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private lateinit var auth: FirebaseAuth
    private lateinit var phoneAuthProvider: PhoneAuthProvider
//    var mobileNumber = ""
//    var email = ""
//    var password = ""
//    var confirmPassword = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signUpFragment = this@SignUpFragment
        }
        auth = FirebaseAuth.getInstance()
        phoneAuthProvider = PhoneAuthProvider.getInstance()
        binding.tvAccountLoginIn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
        binding.viewNavigateLogin.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            val mobileNumber = binding.edtSignUpMobileNumber.text.toString()
            val email = binding.edtSignUpPassword.text.toString()
            val password = binding.edtSignUpPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            if (validateFields(email, mobileNumber, password, confirmPassword)) {
                //startPhoneNumberVerification(mobileNumber)
            }
        }
    }
//    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder()
//            .setPhoneNumber(phoneNumber)
//            .setActivity(requireActivity())
//            .setTimeout(60L, TimeUnit.SECONDS) // Set timeout for verification
//            .setCallbacks(callbacks) // Set callbacks for verification
//            .build()
//        phoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            // Sign in with credential (automatic verification)
//            signInWithPhoneAuthCredential(credential)
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            // Handle verification failure
//        }
//
//        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//            // Show verification code input UI
//            // Store verificationId for later use
//        }
//    }
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Phone number verified, create user with email and password
//                    val email = binding.edtSignUpEmail.text.toString().trim()
//                    val password = binding.edtSignUpPassword.text.toString().trim()
//                    createUserWithEmailAndPassword(email, password)
//                } else {
//                    // Handle phone number verification failure
//                }
//            }
//    }
//
//    private fun createUserWithEmailAndPassword(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // User created successfully
//                    saveUserInformation()
//                    // Navigate to main app screen or show confirmation
//                } else {
//                    // Handle email/password signup error
//                }
//            }
//    }
    private fun saveUserInformation() {
        val uid = auth.currentUser!!.uid
        val database = FirebaseDatabase.getInstance().reference
        val userRef = database.child("users").child(uid)
        // Store user information (email, phone number, etc.) in userRef
    }
    private fun validateFields(
        email: String,
        mobileNumber: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        Log.d(LoginFragment::class.simpleName, "validateNumberAndPassword: ")
//       val mobileNumber = binding.edtSignUpMobileNumber.text.toString()
//      val  email = binding.edtSignUpPassword.text.toString()
//       val password = binding.edtSignUpPassword.text.toString()
//       val confirmPassword = binding.edtConfirmPassword.text.toString()


        if (email.trim().isEmpty()) {
            binding.layoutTextInputEmail.error = getString(R.string.mobile_number_error)
            return false
        } else {
            binding.layoutTextInputEmail.error = null
        }
        if (mobileNumber.trim().isEmpty()) {
            binding.layoutTextInputMobileSignUp.error = getString(R.string.mobile_number_error)
            return false
        } else {
            binding.layoutTextInputMobileSignUp.error = null
        }
        if (password.isEmpty()) {
            binding.edtSignUpPassword.error = getString(R.string.user_pass_error)
            return false
        } else {
            binding.edtSignUpPassword.error = null
        }
        if (password.length < 8) {
            binding.edtSignUpPassword.error = getString(R.string.min_password_limit)
            return false
        } else {
            binding.edtSignUpPassword.error = null
        }
        if (confirmPassword.isEmpty()) {
            binding.edtConfirmPassword.error = getString(R.string.user_confirm_pass_error)
            return false
        } else {
            binding.edtConfirmPassword.error = null
        }
        if (confirmPassword.length < 8) {
            binding.edtConfirmPassword.error = getString(R.string.min_password_limit)
            return false
        } else {
            binding.edtConfirmPassword.error = null
        }
        if (password != confirmPassword) {
            binding.edtConfirmPassword.error = getString(R.string.confirm_password_match_error)
            return false
        } else {
            binding.edtConfirmPassword.error = null
        }

        return true
    }

}