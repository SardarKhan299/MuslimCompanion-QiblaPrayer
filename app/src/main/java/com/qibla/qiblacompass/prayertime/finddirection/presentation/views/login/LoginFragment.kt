package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
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
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

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
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Login Info")
        binding.viewLogin.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data)
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if (account != null) {
                firebaseAuthWithGoogle(account)
            } else {
                Toast.makeText(requireContext(), "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.e("LoginFragment", "Google sign-in failed: ${e.message}", e)
            Toast.makeText(
                requireContext(),
                "Google sign in failed: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
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