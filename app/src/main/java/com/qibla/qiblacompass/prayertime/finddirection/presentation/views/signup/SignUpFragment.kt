package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signUpFragment = this@SignUpFragment
        }
        auth= FirebaseAuth.getInstance()


        binding.tvAccountLoginIn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
        binding.viewNavigateLogin.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
        }
    }
}