package com.iceberry.goodhabit

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        //window.statusBarColor=Color.WHITE
        //setStatusBarFontColor(this,true)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this, R.id.fragment)
        val configuration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }


}