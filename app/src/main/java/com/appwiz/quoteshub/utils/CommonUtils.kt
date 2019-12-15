package com.appwiz.quoteshub.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CommonUtils {

    fun copyQuote(context:Context, quote:String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?;
        val clip = ClipData.newPlainText("label", quote)
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun shareQuote(context:Context, quote:String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, quote)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val chooserIntent = Intent.createChooser(intent, "Share to:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }
}