package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.accountsettings

import android.app.Activity
import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log

import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAccountSettingsBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.security.SecurityFragment


class AccountSettingsFragment :
    BaseFragment<FragmentAccountSettingsBinding>(R.layout.fragment_account_settings) {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            accountSettingsFragment = this@AccountSettingsFragment
        }
        binding.toolbarAccountSettings.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarAccountSettings.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        setupBiometricAuth()

        binding.switchBioMetric.isChecked = SharedPreferences.isBiometricEnabled(mContext)
        binding.switchBioMetric.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(AccountSettingsFragment::class.simpleName, "onViewCreated: $isChecked")
            if (isChecked) {
                checkBiometricFeatureState()
            } else {
                SharedPreferences.enableBiometricLogin(mContext, false)
            }
        }

    }

    fun gotoSecurityScreen() {
        findNavController().navigate(R.id.securityFragment)
    }

    private fun setupBiometricAuth() {
        Log.d(AccountSettingsFragment::class.simpleName, "setupBiometricAuth: ")
        biometricManager = BiometricManager.from(requireContext())
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this@AccountSettingsFragment, executor, biometricCallback)

    }

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Log.d(AccountSettingsFragment::class.simpleName, "onAuthenticationSucceeded: ")
            SharedPreferences.enableBiometricLogin(requireContext(), true)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.d(
                AccountSettingsFragment::class.simpleName,
                "onAuthenticationError: $errorCode ... $errString.."
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkBiometricFeatureState() {
        Log.d(SecurityFragment::class.simpleName, "checkBiometricFeatureState: ")
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d(
                    AccountSettingsFragment::class.simpleName,
                    "checkBiometricFeatureState: No hardware"
                )
                binding.switchBioMetric.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d(
                    AccountSettingsFragment::class.simpleName,
                    "checkBiometricFeatureState: Biometric Feature Unavailable"
                )
                binding.switchBioMetric.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d(
                    AccountSettingsFragment::class.simpleName,
                    "checkBiometricFeatureState: Biometric UnEnrolled"
                )
                binding.switchBioMetric.isEnabled = true
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
                                or android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                resultLauncher.launch(enrollIntent)
            }
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d(SecurityFragment::class.simpleName, "checkBiometricFeatureState: available")
                binding.switchBioMetric.isEnabled = true
                biometricPrompt.authenticate(buildBiometricPrompt())

            }
        }
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.title_verify_biometric))
            .setDescription(getString(R.string.message_confirm_biometric))
            .setNegativeButtonText(getString(R.string.cancel))
            .setConfirmationRequired(false) //Allows user to authenticate without performing an action, such as pressing a button, after their biometric credential is accepted.
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d(AccountSettingsFragment::class.simpleName, ": Activity Result ${data}")
                checkBiometricFeatureState()
            } else {
                Log.d(AccountSettingsFragment::class.simpleName, ": User cancelled the Settings.")
            }
        }
}