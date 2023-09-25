package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.security

import android.app.Activity
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.gone
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSecurityBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.SplashActivity


class SecurityFragment : BaseFragment<FragmentSecurityBinding>(R.layout.fragment_security) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            securityFragment = this@SecurityFragment
        }
        binding.toolbarSecurity.groupToolbarSubScreenProfile.visible()
        binding.toolbarSecurity.tvToolbarSubScreen.text = getString(R.string.security)
        binding.toolbarSecurity.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        binding.imgDownArrow.visible()
        binding.viewResetPassword.setOnClickListener {
            if (binding.groupSecurityResetPassword.visibility == View.VISIBLE) {
                binding.groupSecurityResetPassword.gone()
                binding.imgDownArrow.visible()
                binding.imgUpArrow.gone()
            } else if (binding.groupSecurityResetPassword.visibility == View.GONE) {
                binding.groupSecurityResetPassword.visible()
                binding.imgUpArrow.visible()
                binding.imgDownArrow.gone()

            }
        }
        binding.viewDeleteAccount.setOnClickListener {
            showBottomSheet()
        }
    }


    private fun showBottomSheet() {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_delete_account_confirmation, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        val btnCancel =
            bottomSheetView.findViewById<Button>(R.id.btn_delete_account_cancel)
        val btnContinue =
            bottomSheetView.findViewById<Button>(R.id.btn_delete_account_continue)
        val radioGroup = bottomSheetView.findViewById<RadioGroup>(R.id.radio_group_delete_account)
        val radioButtonReasonDeleteAccount =
            bottomSheetView.findViewById<RadioButton>(R.id.radioBtn_reason_four)
        val groupToDeleteAccount =
            bottomSheetView.findViewById<Group>(R.id.group_security_delete_account)


        bottomSheetDialog.show()
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == radioButtonReasonDeleteAccount.id) {
                groupToDeleteAccount.visibility = View.VISIBLE
            } else {
                groupToDeleteAccount.visibility = View.GONE
            }
        }
        btnContinue.setOnClickListener {
            showBottomSheetDeleteInfo()
            bottomSheetDialog.dismiss()

        }
        btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()

        }

    }

    private fun showBottomSheetDeleteInfo() {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_sheet_delete_account, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        val cancelButton: Button = bottomSheetView.findViewById(R.id.btn_delete_cancel)
        val deleteButton: Button = bottomSheetView.findViewById(R.id.btn_delete_account)
        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        deleteButton.setOnClickListener {
            showBottomSheetDeleteAccountSuccessfully()
            bottomSheetDialog.dismiss()

        }
        bottomSheetDialog.show()
    }

    private fun showBottomSheetDeleteAccountSuccessfully() {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_sheet_delete_account_successfully, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        val quitAppButton: Button = bottomSheetView.findViewById(R.id.btn_quit_app)
        quitAppButton.setOnClickListener {
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
        }
        bottomSheetDialog.show()
    }


}