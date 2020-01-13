package com.appwiz.quoteshub.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.fragments.AuthorsFragment
import com.appwiz.quoteshub.fragments.CategoriesFragment
import com.appwiz.quoteshub.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(CategoriesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                replaceFragment(AuthorsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(app_bar as Toolbar?)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        replaceFragment(HomeFragment())

        btn_personalize.setOnClickListener {
            startActivity(Intent(this, Personalize::class.java))
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
