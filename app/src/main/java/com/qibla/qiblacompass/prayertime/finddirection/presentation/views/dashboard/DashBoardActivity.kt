package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.qibla.qiblacompass.prayertime.finddirection.R

class DashBoardActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_dash_board_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.dashBoardFragment -> {
                Log.d(DashBoardActivity::class.simpleName, "onBackPressed: Finish")
                finish()
            }
            else -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}