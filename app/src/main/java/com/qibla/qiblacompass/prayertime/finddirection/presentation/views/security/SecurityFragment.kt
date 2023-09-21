package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.security

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.gone
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSecurityBinding


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
        binding.viewResetPassword.setOnClickListener {
            if (binding.groupSecurityResetPassword.visibility == View.VISIBLE) {
                binding.groupSecurityResetPassword.gone()
            } else if (binding.groupSecurityResetPassword.visibility == View.GONE) {
                binding.groupSecurityResetPassword.visible()

            }
        }
        binding.viewDeleteAccount.setOnClickListener {
            showBottomSheet()
        }
    }
//    private fun showBottomSheet() {
//        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_delete_account_confirmation, null)
//        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
//        bottomSheetDialog.setContentView(bottomSheetView)
//        bottomSheetDialog.show()
//}

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
            if (checkedId == radioButtonReasonDeleteAccount.id  ) {
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
     val cancelButton :Button = bottomSheetView.findViewById(R.id.btn_delete_cancel)
        val deleteButton : Button = bottomSheetView.findViewById(R.id.btn_delete_account)
        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}