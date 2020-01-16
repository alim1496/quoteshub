package com.appwiz.quoteshub.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants
import kotlinx.android.synthetic.main.privacy_policy.*

class Privacy : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_policy)

        val url = intent.extras?.getString("url")
        privacy_webview.loadUrl(url)
        privacy_webview.settings.javaScriptEnabled = true

    }

}