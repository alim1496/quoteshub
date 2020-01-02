package com.appwiz.quoteshub.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants
import kotlinx.android.synthetic.main.terms_conditions.*

class Conditions : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terms_conditions)

        terms_webview.loadUrl(Constants.termsUrl)
        terms_webview.settings.javaScriptEnabled = true
    }
}