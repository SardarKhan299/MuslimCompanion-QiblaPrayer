package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.PopUpDialog
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSideMenuBinding
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
        val userName = SharedPreferences.getUserName(mContext)
        val userImageView = binding.toolbarSideMenu.imgProfileImage
        val userImageUri = SharedPreferences.getUserProfile(mContext)

        // Load and display the user's profile image using a library like Picasso or Glide
        if (userImageUri != null) {
            Glide.with(this)
                .load(userImageUri)
                .placeholder(R.drawable.doc_avatar) // Placeholder image while loading
                .error(R.drawable.doc_avatar) // Error image if loading fails
                .into(userImageView)
        } else {
            // If the user doesn't have a profile image, you can set a default image
            userImageView.setImageResource(R.drawable.doc_avatar)
        }


        binding.toolbarSideMenu.tvProfileName.text = userName


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
            R.drawable.ic_warning,
            true
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