package com.appwiz.quoteshub.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants
import kotlinx.android.synthetic.main.personalize_screen.*

class Personalize : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalize_screen)
        setSupportActionBar(person_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Miscellaneous"

        fav_list.setOnClickListener {
            startActivity(Intent(this, Favorites::class.java))
        }

        tv_fb_page.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Constants.fbPage)
            startActivity(intent)
        }

        tv_tnc.setOnClickListener {
            val webIntent = Intent(this, Privacy::class.java)
            webIntent.putExtra("url", Constants.termsUrl)
            startActivity(webIntent)
        }

        tv_policy.setOnClickListener {
            val webIntent = Intent(this, Privacy::class.java)
            webIntent.putExtra("url", Constants.policyUrl)
            startActivity(webIntent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}