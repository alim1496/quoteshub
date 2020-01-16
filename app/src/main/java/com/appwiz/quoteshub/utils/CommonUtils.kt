package com.appwiz.quoteshub.utils

import android.content.*
import android.widget.Toast
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.FavEntity
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class CommonUtils : CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    fun favQuote(context: Context, quote: Quote, authorName: String, callback: () -> Unit) {
        val name = if (authorName != "") authorName
        else quote.source.name
        val entity = FavEntity(quote.id, quote.title, name)
        val db = AppDB(context)

        launch {
            withContext(Dispatchers.IO) {
                try {
                    db.favDao().addFav(entity)
                } catch (e:Exception) {
                    return@withContext
                }
            }
            callback()
        }
    }

    fun unfavQuote(context: Context, id: Int, callback: () -> Unit) {
        val db = AppDB(context)

        launch {
            withContext(Dispatchers.IO) {
                try {
                    db.favDao().removeFav(id)
                } catch (e:Exception) {
                    return@withContext
                }
            }
            callback()
        }
    }

    private fun getContent(quote:Quote, authorName: String) : String {
        val name = if (authorName != "") authorName
        else quote.source.name
        return "\"" + quote.title + "\"" + "\n" +  name
    }

    fun copyQuote(context:Context, quote:Quote, authorName: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?;
        val clip = ClipData.newPlainText("label", getContent(quote, authorName))
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun shareQuote(context:Context, quote:Quote, authorName: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, getContent(quote, authorName))
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val chooserIntent = Intent.createChooser(intent, "Share to:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

}