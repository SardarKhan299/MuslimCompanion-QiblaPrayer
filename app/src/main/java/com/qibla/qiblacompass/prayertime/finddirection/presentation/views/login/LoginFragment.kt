package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.Constants
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentLoginBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardActivity
import java.util.regex.Pattern


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    var mobileNumber = ""
    var password = ""
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginFragment = this@LoginFragment
        }
        oneTapClient = Identity.getSignInClient(mContext)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.your_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Login Info")
        binding.viewLogin.setOnClickListener {
            Log.d(LoginFragment::class.simpleName, "onViewCreated: CLicked")
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, RC_SIGN_IN,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("LoginFragment", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(LoginFragment::class.simpleName, "signInWithGoogle: Failed ${e.message}")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try{
        val credential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = credential.googleIdToken
        val username = credential.id
        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with your backend.
                Log.d("LoginFragment", "Got ID token.")
                firebaseAuthWithGoogle(credential)
            }
            else -> {
                // Shouldn't happen.
                Log.d("LoginFragment", "No ID token or password!")
            }
        }
        } catch (e: Exception) {
            Log.e("LoginFragment", "Google sign-in failed: ${e.message}", e)
            Toast.makeText(
                requireContext(),
                "Google sign in failed: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        try {
            Log.d(LoginFragment::class.simpleName, "handleGoogleSignInResult: ")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            Log.d(LoginFragment::class.simpleName, "handleGoogleSignInResult: Done")

//            if (account != null) {
//                firebaseAuthWithGoogle(account)
//            } else {
//                Toast.makeText(requireContext(), "Google sign in failed", Toast.LENGTH_SHORT).show()
//            }
        } catch (e: ApiException) {
            Log.e("LoginFragment", "Google sign-in failed: ${e.message}", e)
            Toast.makeText(
                requireContext(),
                "Google sign in failed: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: SignInCredential) {
        val credential = GoogleAuthProvider.getCredential(account.googleIdToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    saveUserToDatabase(user)
                    // Save user information in SharedPreferences
                    saveUserInfoInSharedPreferences(user)
                    Toast.makeText(
                        requireContext(),
                        "Signed in as ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to DashboardActivity
                    startActivity(Intent(requireContext(), DashBoardActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun saveUserToDatabase(user: FirebaseUser?) {
        // Save user information to the Firebase Realtime Database
        user?.let {
            val userId = it.uid
            val userName = it.displayName
            val userEmail = it.email

            val userMap = hashMapOf(
                "userId" to userId,
                "userName" to userName,
                "userEmail" to userEmail,

                )

            databaseReference.child(userId).setValue(userMap)
        }
    }

    private fun saveUserInfoInSharedPreferences(user: FirebaseUser?) {
        SharedPreferences.saveUserDetails(mContext, user?.displayName ?: "", user?.email ?: "",
            (user?.photoUrl ?: "").toString()
        )
    }

    private fun validateNumberAndPassword(): Boolean {
        Log.d(LoginFragment::class.simpleName, "validateNumberAndPassword: ")
        mobileNumber = binding.edtMobileNumber.text.toString()
        password = binding.edtPassword.text.toString()

        if (mobileNumber.trim().isEmpty()) {
            binding.layoutTextInputMobile.error = getString(R.string.mobile_number_error)
            return false
        } else {
            binding.layoutTextInputMobile.error = null
        }
        if (password.isEmpty()) {
            binding.layoutTextInputPassword.error = getString(R.string.user_pass_error)
            return false
        } else {
            binding.layoutTextInputPassword.error = null
        }
        if (!(Pattern.matches(Constants.PASS_REG, mobileNumber.trim()))) {
            binding.layoutTextInputPassword.error = getString(R.string.user_id_invalid)
            return false
        } else {
            binding.layoutTextInputPassword.error = null
        }
        if (password.trim().length < 8) {
            binding.layoutTextInputPassword.error = getString(R.string.min_password_limit)
            return false
        } else {
            binding.layoutTextInputPassword.error = null
        }

        return true
    }

}