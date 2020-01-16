package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.FavAdapter
import com.appwiz.quoteshub.room.AppDB
import kotlinx.android.synthetic.main.favorite_quotes_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Favorites : AppCompatActivity(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_quotes_list)
        setSupportActionBar(fav_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Favorite Quotes"

        val db = AppDB(this)

        val adapter = FavAdapter(this)
        fav_recycler.adapter = adapter
        fav_recycler.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        launch {
            withContext(Dispatchers.IO) {
                adapter.setFav(db.favDao().showAll())
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}