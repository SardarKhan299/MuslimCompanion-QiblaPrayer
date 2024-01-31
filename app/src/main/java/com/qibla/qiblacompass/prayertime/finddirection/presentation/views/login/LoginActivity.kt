package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.login

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseActivity
import com.qibla.qiblacompass.prayertime.finddirection.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var navController: NavController
//    @Inject
//    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.loginFragment -> {
                Log.d(LoginActivity::class.simpleName, "onBackPressed: Finish")
                finish()
            }
//            R.id.signUpFragment -> {
//                Log.d(AuthActivity::class.simpleName, "onBackPressed: Appointment Finish")
//                finish()
//            }
            else -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}