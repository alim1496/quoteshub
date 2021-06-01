package com.appwiz.quoteshub.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.fragments.HomeFragment
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var settings:ImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        settings = findViewById(R.id.settings)
        settings.setOnClickListener {
            val intent = Intent(this, MiscActivity::class.java)
            startActivity(intent)
        }
        replaceFragment(HomeFragment(), "home")
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun gotoAuthor(id:Int, name:String) {
        val intent = Intent(this, SingleAuthor::class.java)
        intent.putExtra("authorID", id)
        intent.putExtra("authorname", name)
        startActivity(intent)
    }
}
