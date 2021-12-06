package com.olayg.navigationsamplewithauthentication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.olayg.navigationsamplewithauthentication.R
import com.olayg.navigationsamplewithauthentication.databinding.ActivityMainBinding
import com.olayg.navigationsamplewithauthentication.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_content_main) as NavHostFragment
    }
    private var menu: Menu? = null
    private val NavDestination.isAuthDestination
        get() = (id == R.id.destination_register || id == R.id.destination_login)

    var showFab: Boolean = true
        set(value) {
            binding.fab.isVisible = value
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().setKeepVisibleCondition {
            // This will keep the splash screen up until the starting point is calculated
            // based on users logged in status provided by datastore in viewmodel
            mainViewModel.startingPointCalculated.value == null
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu
        menuInflater.inflate(R.menu.activity_main, menu)
        hideNonAuthViews(!mainViewModel.userLoggedIn)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                mainViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() = with(navHostFragment.navController) {
        navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initViews() = with(binding) {
        setSupportActionBar(binding.toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            hideNonAuthViews(destination.isAuthDestination)
            showFab = destination.id == R.id.destination_home
        }

    }

    private fun initObservers() = with(mainViewModel) {
        // Setting nav_graph here rather than xml depending on user authentication status
        lifecycleScope.launchWhenCreated {
            startingPointCalculated.observe(this@MainActivity) { destination ->
                if (destination != null) navHostFragment.navController.apply {
                    graph = navInflater.inflate(R.navigation.auth_graph).apply {
                        startDestination = destination
                    }
                    binding.bottomNavigation.setupWithNavController(this)
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            R.id.destination_login,
                            R.id.destination_register,
                            R.id.destination_home,
                            R.id.destination_browse,
                            R.id.destination_settings
                        )
                    )
                    setupActionBarWithNavController(this, appBarConfiguration)
                }
            }
        }
    }

    private fun hideNonAuthViews(hide: Boolean) = with(binding) {
        menu?.findItem(R.id.action_logout)?.isVisible = !hide
        fab.isVisible = !hide
        bottomNavigation.isVisible = !hide
    }
}