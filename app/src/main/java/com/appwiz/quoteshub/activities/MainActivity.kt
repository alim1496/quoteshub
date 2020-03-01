package com.appwiz.quoteshub.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.fragments.AuthorsFragment
import com.appwiz.quoteshub.fragments.CategoriesFragment
import com.appwiz.quoteshub.fragments.Favorites
import com.appwiz.quoteshub.fragments.HomeFragment
import com.appwiz.quoteshub.fragments.Privacy
import com.appwiz.quoteshub.utils.Constants
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout


    private val onSideNavClickListener = NavigationView.OnNavigationItemSelectedListener {
        val bundle = Bundle()
        val fragment = Privacy()
        when (it.itemId) {
            R.id.navigation_home -> {
                replaceFragment(HomeFragment())
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(CategoriesFragment())
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                replaceFragment(AuthorsFragment())
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_fav -> {
                replaceFragment(Favorites())
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_fb -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(Constants.fbPage)
                startActivity(intent)
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_tnc -> {
                bundle.putString("url", Constants.termsUrl)
                fragment.arguments = bundle
                replaceFragment(fragment)
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_policy -> {
                bundle.putString("url", Constants.policyUrl)
                fragment.arguments = bundle
                replaceFragment(fragment)
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sideNav: NavigationView = findViewById(R.id.side_navigation)
        drawerLayout = findViewById(R.id.main_drawer)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        sideNav.setNavigationItemSelectedListener(onSideNavClickListener)

        replaceFragment(HomeFragment())


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
