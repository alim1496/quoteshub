package com.appwiz.quoteshub.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
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
import com.appwiz.quoteshub.utils.Constants
import com.google.android.material.navigation.NavigationView



class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sideNav: NavigationView

    private val onSideNavClickListener = NavigationView.OnNavigationItemSelectedListener {
        val intent = Intent(this, Privacy::class.java)
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
                val webIntent = Intent(Intent.ACTION_VIEW)
                webIntent.data = Uri.parse(Constants.fbPage)
                startActivity(webIntent)
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_tnc -> {
                intent.putExtra("url", Constants.termsUrl)
                startActivity(intent)
                closeDrawer()
                return@OnNavigationItemSelectedListener true
            }
            R.id.drawer_policy -> {
                intent.putExtra("url", Constants.policyUrl)
                startActivity(intent)
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

        sideNav = findViewById(R.id.side_navigation)
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
