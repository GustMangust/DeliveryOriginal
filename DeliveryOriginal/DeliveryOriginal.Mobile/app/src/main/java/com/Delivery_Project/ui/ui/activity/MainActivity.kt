package com.Delivery_Project.ui.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.Delivery_Project.R
import com.Delivery_Project.databinding.ActivityMainBinding
import com.Delivery_Project.databinding.NavHeaderMainBinding
import com.Delivery_Project.pojo.Dish
import com.Delivery_Project.pojo.User

class MainActivity : AppCompatActivity() {
    private lateinit var nameBinding: NavHeaderMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nameBinding = NavHeaderMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_menu, R.id.nav_cart
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        user  = intent.getSerializableExtra("User") as User

        /*
        val viewHeader = binding.navView.getHeaderView(0)
        val navViewHeaderBinding : NavHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader)
        navViewHeaderBinding.regularName.text = dish.Login*/
    }
    fun getUser() : User{
        return user;
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun log_out(view: android.view.View) {
        startActivity(Intent(this, LoginActivity::class.java ))
    }
}