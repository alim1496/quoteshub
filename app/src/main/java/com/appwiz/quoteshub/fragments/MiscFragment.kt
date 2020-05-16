package com.appwiz.quoteshub.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants

class MiscFragment : Fragment() {
    private lateinit var fb: TextView
    private lateinit var tweet: TextView
    private lateinit var privacy: TextView
    private lateinit var tnc: TextView
    private lateinit var rate: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.misc_layout, container, false)
        fb = view.findViewById(R.id.fb_page)
        tweet = view.findViewById(R.id.twitter_page)
        privacy = view.findViewById(R.id.privacy_page)
        tnc = view.findViewById(R.id.tnc_page)
        rate = view.findViewById(R.id.review_page)
        setListeners()
        return view
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
            webIntent.data = Uri.parse("market://details?id=" + context?.packageName)
            startActivity(webIntent)
        })
    }

}