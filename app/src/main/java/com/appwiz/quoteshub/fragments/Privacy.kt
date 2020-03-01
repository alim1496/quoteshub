package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.Constants
import kotlinx.android.synthetic.main.privacy_policy.*

class Privacy: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.privacy_policy, container, false)
        val url = arguments?.getString("url")
        val webView: WebView = view.findViewById(R.id.privacy_webview)
        webView.loadUrl(url)
        return view
    }

}