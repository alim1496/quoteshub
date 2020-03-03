package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.appwiz.quoteshub.R

class Privacy: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_policy)
        val url = intent.extras?.getString("url")
        val webView: WebView = findViewById(R.id.privacy_webview)
        webView.loadUrl(url)
    }
}