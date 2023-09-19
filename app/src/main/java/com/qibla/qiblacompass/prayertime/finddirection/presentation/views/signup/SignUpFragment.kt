package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSignUpBinding


class SignUpFragment :BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signUpFragment =this@SignUpFragment
        }
        binding.tvAccountLoginIn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
    }
}