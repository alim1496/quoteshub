package com.appwiz.quoteshub.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants
import kotlinx.android.synthetic.main.privacy_policy.*

class Privacy : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_policy)

        privacy_webview.loadUrl(Constants.policyUrl)
        privacy_webview.settings.javaScriptEnabled = true
    }
}