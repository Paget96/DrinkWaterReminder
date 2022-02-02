package com.paget96.drinkwaterreminder.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.DynamicColors
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.database.settings.SettingsDatabase
import com.paget96.drinkwaterreminder.database.stats.StatsDatabase
import com.paget96.drinkwaterreminder.fragments.FragmentHistory
import com.paget96.drinkwaterreminder.fragments.FragmentMain
import com.paget96.drinkwaterreminder.fragments.FragmentOther
import com.paget96.drinkwaterreminder.utils.Theme
import com.paget96.drinkwaterreminder.utils.UiUtils

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    // Variables
    private var theme: Theme? = null
    var bannerAdContainer: FrameLayout? = null
    private var haveStackedFragment = false
    var toolbar: MaterialToolbar? = null
    var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    var appBarLayout: AppBarLayout? = null
    var bottomNavigationView: BottomNavigationView? = null
    private var doubleBackPressed = false
    var sharedPreferences: SharedPreferences? = null
    var settingsDatabase: SettingsDatabase? = null
    var statsDatabase: StatsDatabase? = null

    private fun initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
    }

    private fun initializeDatabase(context: Context) {
        settingsDatabase = SettingsDatabase.getDatabase(context)
        statsDatabase = StatsDatabase.getDatabase(context)
    }

    fun edgeToEdge(topView: View?, useMargins: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val rootLayout = findViewById<LinearLayout>(R.id.main_activity_root)

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { view, windowInsets ->
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

        if (topView != null)
            ViewCompat.requestApplyInsets(rootLayout)
    }

    // Method for setting an app theme (reads current state from Shared Preferences)
    private fun appTheme() {
        theme = Theme(this@MainActivity)
        theme?.apply {
            loadDarkMode(theme!!.getDarkMode())
        }
    }

    private fun initializeToolbar() {
        appBarLayout = findViewById(R.id.app_bar)
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout)
        toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initializeBottomNavigation() {
        bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation_view) as BottomNavigationView

        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    replaceFragment(
                        FragmentMain::class.java,
                        addToStack = false,
                        transitionAnimations = true,
                        arguments = null,
                        "FragmentMain"
                    )
                    true
                }

                R.id.action_history -> {
                    replaceFragment(
                        FragmentHistory::class.java,
                        addToStack = false,
                        transitionAnimations = true,
                        arguments = null,
                        "FragmentAppUsage"
                    )
                    true
                }

                R.id.action_other -> {
                    replaceFragment(
                        FragmentOther::class.java,
                        addToStack = false,
                        transitionAnimations = true,
                        arguments = null,
                        "FragmentOther"
                    )
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Dynamic color changing
        DynamicColors.applyIfAvailable(this)

        appTheme()

        setContentView(R.layout.activity_main)
        initializeDatabase(this@MainActivity)
        initializeSharedPreferences()
        initializeBottomNavigation()
        initializeToolbar()

        UiUtils.showWhatsNew(this@MainActivity, sharedPreferences!!)
        UiUtils.rateApp(this@MainActivity, sharedPreferences!!)

        edgeToEdge(findViewById(R.id.toolbar), true)

        //Listen for changes in the back stack
        supportFragmentManager.addOnBackStackChangedListener { shouldDisplayHomeUp() }

        // Set saved fragment
        if (savedInstanceState == null) {
            bottomNavigationView?.selectedItemId = R.id.action_home

            replaceFragment(FragmentMain::class.java, false, true, null, "FragmentMain")
        } else {
            shouldDisplayHomeUp()
        }
    }

    fun shouldDisplayHomeUp() {
        val supportFragmentManager = supportFragmentManager
        haveStackedFragment = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(haveStackedFragment)
        supportActionBar!!.setHomeButtonEnabled(haveStackedFragment)
        bottomNavigationView?.visibility = if (haveStackedFragment) View.GONE else View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        //This method is called when the up button is pressed. Just the pop back stack.
        supportFragmentManager.popBackStack()
        return true
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    override fun onBackPressed() {
        if (!doubleBackPressed && supportFragmentManager.backStackEntryCount == 0) {
            doubleBackPressed = true
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({ doubleBackPressed = false }, 2000)
        } else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_support -> {

                true
            }
            R.id.action_recommend -> {

                true
            }
            R.id.action_help -> {

                true
            }

            else ->                 // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
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

    fun refreshFragment(fragmentTag: String?) {
        val frg = supportFragmentManager.findFragmentByTag(fragmentTag)
        val ft = supportFragmentManager.beginTransaction()
        if (frg != null) {
            ft.detach(frg)
            ft.attach(frg)
            ft.setReorderingAllowed(true).commit()
        }
    }

}