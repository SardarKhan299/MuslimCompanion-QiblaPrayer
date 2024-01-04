package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PopUpDialog
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSideMenuBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login.LoginActivity


class SideMenuFragment : BaseFragment<FragmentSideMenuBinding>(R.layout.fragment_side_menu) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(SideMenuFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sideMenuFragment = this@SideMenuFragment
        }
        binding.toolbarSideMenu.groupToolbarProfile.visibility = View.VISIBLE
        binding.toolbarSideMenu.groupToolbar.visibility = View.GONE
        binding.toolbarSideMenu.viewToolbar.setOnClickListener {
            findNavController().closeCurrentScreen()
        }


    }

    fun gotoUpdateAccountSettings() {
        findNavController().navigate(R.id.accountSettingsFragment)
    }

    fun gotoUpdateAboutUsScreen() {
        findNavController().navigate(R.id.aboutUsFragment)

    }

    fun logoutMethod() {
                PopUpDialog(
                    getString(R.string.warning),
                    getString(R.string.logout_dialog),
                    ok_btn_callback(),
                    R.drawable.ic_warning
                ).show(requireActivity().supportFragmentManager, "")

    }

    fun ok_btn_callback(): (String) -> Unit {
        return {
            Log.d("MakkahLiveFragment"::class.simpleName, "ok_btn_callback: ")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}