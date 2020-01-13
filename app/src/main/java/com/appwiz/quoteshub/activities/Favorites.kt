package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.FavAdapter
import com.appwiz.quoteshub.room.AppDB
import kotlinx.android.synthetic.main.favorite_quotes_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Favorites : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_quotes_list)
        setSupportActionBar(fav_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Favorite Quotes"

        val db = AppDB(this)

        val adapter = FavAdapter(this)
        fav_recycler.adapter = adapter
        fav_recycler.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            adapter.setFav(db.favDao().showAll())
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
}