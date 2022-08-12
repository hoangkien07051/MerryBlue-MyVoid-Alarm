package com.merryblue.myvoicealarm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.pref.SharedPrefsHelper
import com.merryblue.myvoicealarm.databinding.ActivityMainBinding
import com.merryblue.myvoicealarm.ui.base.MyCustomerOnboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        updateBottomBar()
        val firstTime = SharedPrefsHelper.getBoolean(applicationContext, SharedPrefsHelper.MyPREFERENCES, SharedPrefsHelper.OPEN_APP_FIRST_TIME, true)
        if (firstTime) {
            var onboarding = false
            if (intent != null && intent.extras != null) {
                onboarding = intent.extras!!.getBoolean("onboarding", false)
            }
            if (!onboarding) {
                initOnboarding()
            }
        }

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_alarms, R.id.navigation_add_alarm, R.id.navigation_more
            )
        )
        try {
            setSupportActionBar(bottomAppBar)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        fab.click {
            navController.navigate(R.id.navigation_add_alarm)
            //startActivity(Intent(this, AddAlarmActivity::class.java))
        }
    }

    fun hideBottomNavigation() {
        bottomAppBar.visibility = View.GONE
        fab.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }

    //    override fun attachBaseContext(newBase: Context?) {
//        val language = SharedPrefsHelper.getString(
//            newBase!!,
//            SharedPrefsHelper.MyPREFERENCES,
//            SharedPrefsHelper.SETTING_LANGUAGE
//        )
//        super.attachBaseContext(MyContextWrapper.wrap(newBase,language));
//    }
    override fun onBackPressed() {
        updateBottomBar()
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
        super.onBackPressed()
    }

    private fun initOnboarding() {
        val intent = Intent(
            this,
            MyCustomerOnboard::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun updateBottomBar() {
        bottomAppBar.fabCradleRoundedCornerRadius = 20f
        bottomAppBar.cradleVerticalOffset = 10f
        bottomAppBar.fabCradleMargin = 10f
    }

}