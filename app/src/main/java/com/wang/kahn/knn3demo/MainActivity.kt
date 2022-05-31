package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.wang.kahn.knn3demo.databinding.ActivityMainBinding

const val NUM_PAGES = 5

const val ADDRESS_TO_QUERY = "0xae89ad222e67205e8d947f131fdc9fa139828745"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.address_query, R.id.nft_query, R.id.membership_query
        ), binding.drawLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return when (item.itemId) {
            R.id.action_settings -> true
            else -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)}
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (binding.drawLayout.isDrawerOpen(binding.navView)) {
            binding.drawLayout.closeDrawers()
            return
        }
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val currentDestination = navController.currentDestination
        val topLevelDestinations: Set<Int> = appBarConfiguration.topLevelDestinations
        if (topLevelDestinations.contains(currentDestination?.id)) {
            finish()
            return
        }
        super.onBackPressed()
    }
}