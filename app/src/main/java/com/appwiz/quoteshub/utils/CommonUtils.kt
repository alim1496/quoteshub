package com.appwiz.quoteshub.utils

import android.content.*
import android.util.Log
import android.widget.Toast
import com.appwiz.quoteshub.activities.MainActivity
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.FavDao
import com.appwiz.quoteshub.room.FavEntity
import android.content.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class CommonUtils : CoroutineScope {
    private val job = Job()
    private val PRIVATE_MODE = 0
    private val prefName = "Favorites"

    override val coroutineContext: CoroutineContext
        get() = job

    fun favQuote(context: Context, quote: Quote, authorName: String, callback: () -> Unit) {
        val nameFound = authorName != ""
        val name: String
        if (nameFound) name = authorName
        else name = quote.source.name
        val entity = FavEntity(quote.id, quote.title, name)
        val db = AppDB(context)

        launch {
            withContext(Dispatchers.IO) {
                db.favDao().addFav(entity)
                addFavoritePref(context, quote.id)
            }
            callback()
        }
    }

    fun unfavQuote(context: Context, id: Int, callback: () -> Unit) {
        val db = AppDB(context)

        launch {
            withContext(Dispatchers.IO) {
                db.favDao().removeFav(id)
                addFavoritePref(context, id)
            }
            callback()
        }
    }

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

    fun addFavoritePref(context: Context, id: Int) {
        val sharedpreferences = context.getSharedPreferences(prefName, PRIVATE_MODE)
        val editor = sharedpreferences.edit()
        editor.putInt("Favorite${id}", id)
        editor.apply()
    }

    fun removeFavoritePref(context: Context, id: Int) {
        val sharedpreferences = context.getSharedPreferences(prefName, PRIVATE_MODE)
        val editor = sharedpreferences.edit()
        editor.remove("Favorite${id}")
        editor.apply()
    }

    fun checkFavoritePref(context: Context, id: Int) : Boolean {
        val sharedpreferences = context.getSharedPreferences(prefName, PRIVATE_MODE)
        val fetchedID = sharedpreferences.getInt("Favorite${id}", 0)
        return id == fetchedID
    }
}