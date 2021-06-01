package com.appwiz.quoteshub.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants

class MiscActivity : AppCompatActivity() {
    private lateinit var fb: TextView
    private lateinit var tweet: TextView
    private lateinit var privacy: TextView
    private lateinit var tnc: TextView
    private lateinit var rate: TextView
    private lateinit var _activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.misc_layout)
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        fb = findViewById(R.id.fb_page)
        tweet = findViewById(R.id.twitter_page)
        privacy = findViewById(R.id.privacy_page)
        tnc = findViewById(R.id.tnc_page)
        rate = findViewById(R.id.review_page)
        toolbar.setNavigationOnClickListener { _: View? -> onBackPressed() }
        setListeners()
    }

    private fun setListeners() {
        fb.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse(Constants.fbPage)
            startActivity(webIntent)
        })

        tweet.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse(Constants.twitterPage)
            startActivity(webIntent)
        })

        tnc.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse(Constants.termsUrl)
            startActivity(webIntent)
        })

        privacy.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse(Constants.policyUrl)
            startActivity(webIntent)
        })

        rate.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse("market://details?id=$packageName")
            startActivity(webIntent)
        })
    }

}