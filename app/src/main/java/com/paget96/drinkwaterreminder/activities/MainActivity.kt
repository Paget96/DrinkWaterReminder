package com.paget96.drinkwaterreminder.activities

import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.color.DynamicColors
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.database.settings.SettingsDatabase
import com.paget96.drinkwaterreminder.database.stats.StatsDatabase
import com.paget96.drinkwaterreminder.databinding.ActivityMainBinding
import com.paget96.drinkwaterreminder.utils.Theme
import com.paget96.drinkwaterreminder.utils.UiUtils

class MainActivity : AppCompatActivity() {

    // Variables
    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var theme: Theme? = null
    private var appBarLayout: AppBarLayout? = null
    private var doubleBackPressed = false
    private var sharedPreferences: SharedPreferences? = null
    private var settingsDatabase: SettingsDatabase? = null
    private var statsDatabase: StatsDatabase? = null


    private fun initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
    }

    private fun initializeDatabase(context: Context) {
        settingsDatabase = SettingsDatabase.getDatabase(context)
        statsDatabase = StatsDatabase.getDatabase(context)
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

    // Method for setting an app theme (reads current state from Shared Preferences)
    private fun appTheme() {
        theme = Theme(this@MainActivity)
        theme?.apply {
            loadDarkMode(theme!!.getDarkMode())
        }
    }

//    private fun initializeToolbar() {
//        appBarLayout = findViewById(R.id.app_bar)
//        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout)
//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//    }

//    private fun initializeBottomNavigation() {
//        bottomNavigationView =
//            findViewById<View>(R.id.bottom_navigation_view) as BottomNavigationView
//
//        bottomNavigationView?.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.action_home -> {
//                    replaceFragment(
//                        FragmentMain::class.java,
//                        addToStack = false,
//                        transitionAnimations = true,
//                        arguments = null,
//                        "FragmentMain"
//                    )
//                    true
//                }
//
//                R.id.action_history -> {
//                    replaceFragment(
//                        FragmentHistory::class.java,
//                        addToStack = false,
//                        transitionAnimations = true,
//                        arguments = null,
//                        "FragmentAppUsage"
//                    )
//                    true
//                }
//
//                R.id.action_other -> {
//                    replaceFragment(
//                        FragmentOther::class.java,
//                        addToStack = false,
//                        transitionAnimations = true,
//                        arguments = null,
//                        "FragmentOther"
//                    )
//                    true
//                }
//
//                else -> false
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Dynamic color changing
        DynamicColors.applyToActivityIfAvailable(this)

        appTheme()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentMain,
                R.id.fragmentHistory,
                R.id.fragmentOther
            )
        )

        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.bottomNavigationView.setupWithNavController(navController)

        initializeDatabase(this@MainActivity)
        initializeSharedPreferences()

        UiUtils.showWhatsNew(this@MainActivity, sharedPreferences!!)
        UiUtils.rateApp(this@MainActivity, sharedPreferences!!)

        edgeToEdge(findViewById(R.id.toolbar), true)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentAbout,
                R.id.fragmentSettings -> hideBottomNavigation()

                else -> showBottomNavigation()
            }
        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (!doubleBackPressed && supportFragmentManager.backStackEntryCount == 0) {
//                    doubleBackPressed = true
//                    Toast.makeText(
//                        this@MainActivity,
//                        getString(R.string.press_again_to_exit),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Handler(Looper.getMainLooper()).postDelayed({ doubleBackPressed = false }, 2000)
//                } else finish()
//            }
//        })

        // Listen for changes in the back stack
        // supportFragmentManager.addOnBackStackChangedListener { shouldDisplayHomeUp() }

        // Set saved fragment
//        if (savedInstanceState == null) {
//            bottomNavigationView?.selectedItemId = R.id.action_home
//
//            replaceFragment(FragmentMain::class.java, false, true, null, "FragmentMain")
//        } else {
//            shouldDisplayHomeUp()
//        }
    }

//    fun shouldDisplayHomeUp() {
//        val supportFragmentManager = supportFragmentManager
//        haveStackedFragment = supportFragmentManager.backStackEntryCount > 0
//        supportActionBar!!.setDisplayHomeAsUpEnabled(haveStackedFragment)
//        supportActionBar!!.setHomeButtonEnabled(haveStackedFragment)
//        bottomNavigationView?.visibility = if (haveStackedFragment) View.GONE else View.VISIBLE
//    }

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

    // override fun onBackStackChanged() {
    // shouldDisplayHomeUp()
    // }


    private fun showBottomNavigation() {
        binding.bottomNavigation.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.bottomNavigationView.visibility = View.GONE
    }

    fun replaceFragment(
        newFragment: Class<out Fragment>,
        addToStack: Boolean,
        transitionAnimations: Boolean,
        arguments: Bundle?,
        fragmentTag: String?
    ) {
        appBarLayout?.setExpanded(true, true)

        if (addToStack) {
            supportFragmentManager.commit {
                if (transitionAnimations) setCustomAnimations(
                    R.anim.fragment_open_enter,
                    R.anim.fragment_fade_exit
                )
                setReorderingAllowed(true)
                addToBackStack(null)

                replace(R.id.fragment_container, newFragment, arguments)
            }
        } else {
            supportFragmentManager.commit {
                if (transitionAnimations) setCustomAnimations(
                    R.anim.fragment_open_enter,
                    R.anim.fragment_fade_exit
                )
                setReorderingAllowed(true)
                replace(R.id.fragment_container, newFragment, arguments)
            }
        }
    }

//    fun refreshFragment(fragmentTag: String?) {
//        val frg = supportFragmentManager.findFragmentByTag(fragmentTag)
//        val ft = supportFragmentManager.beginTransaction()
//        if (frg != null) {
//            ft.detach(frg)
//            ft.attach(frg)
//            ft.setReorderingAllowed(true).commit()
//        }
//    }
}