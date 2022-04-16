package com.andro.indieschool.cocktailapp.feature

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.andro.indieschool.cocktailapp.R
import com.andro.indieschool.cocktailapp.databinding.ActivityMainBinding
import com.andro.indieschool.cocktailapp.util.common.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.favouriteFragment,
                R.id.settingsFragment -> {
                    binding.mainBottomNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.mainBottomNav.visibility = View.GONE
                }
            }
        }
        binding.mainBottomNav.setupWithNavController(navController)
    }
}