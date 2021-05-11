package ru.itis.navigationhw.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.navigationhw.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var nc = findNavController(R.id.fragment1)
        var b = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        b.setupWithNavController(nc)
//        setupActionBarWithNavController(findNavController(R.id.fragment), AppBarConfiguration(setOf(R.id.homeFragment, R.id.friendsFragment, R.id.settingsFragment)))
    }

}