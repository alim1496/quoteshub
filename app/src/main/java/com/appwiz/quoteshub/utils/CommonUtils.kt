package com.appwiz.quoteshub.utils

import android.content.*
import android.widget.Toast
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.utils.Constants.Companion.playstorePage
import java.lang.Exception
import android.content.Intent
import android.net.Uri
import android.util.Log


class CommonUtils {

    fun copyQuote(context:Context, quote:Quote) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val writer = if (quote.source != null) " - " + quote.source.name else ""
        val content = "\"" + quote.title + "\"" + writer
        val clip = ClipData.newPlainText("label", content)
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun shareSocial(context: Context, packageName:String, quote: Quote) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.setPackage(packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val writer = if (quote.source != null) " - " + quote.source.name else ""
        val content = "\"" + quote.title + "\"" + writer + "\n" + playstorePage
        intent.putExtra(Intent.EXTRA_TEXT, content)
        try {
            context.startActivity(intent)
        } catch (e:Exception) {
            val url = "https://play.google.com/store/apps/details?id=${packageName}"
            val _intent = Intent(Intent.ACTION_VIEW)
            _intent.data = Uri.parse(url)
            context.startActivity(_intent)
        }
    }

}