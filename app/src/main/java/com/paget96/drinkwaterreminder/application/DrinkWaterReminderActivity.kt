package com.paget96.drinkwaterreminder.application

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.DynamicColors
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.databinding.ActivityMainBinding
import com.paget96.drinkwaterreminder.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkWaterReminderActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DynamicColors.applyToActivityIfAvailable(this)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentRecords,
                R.id.fragmentHistory,
                R.id.fragmentOther
            )
        )

        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.bottomNavigationView.setupWithNavController(navController)

        initializeSharedPreferences()

        UiUtils.showWhatsNew(this@DrinkWaterReminderActivity, sharedPreferences!!)
        UiUtils.rateApp(this@DrinkWaterReminderActivity, sharedPreferences!!)

        edgeToEdge(findViewById(R.id.toolbar), true)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentAbout,
                R.id.fragmentSettings -> {
                    binding.bottomNavigation.bottomNavigationView.visibility = View.GONE
                }

                else -> binding.bottomNavigation.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    private fun initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
    }

    private fun edgeToEdge(topView: View?, useMargins: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            if (useMargins) {
                topView?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = insets.top
                }
            } else {
                topView?.setPadding(0, insets.top, 0, 0)
            }

            WindowInsetsCompat.CONSUMED
        }

        if (topView != null) ViewCompat.requestApplyInsets(binding.root)
    }
}