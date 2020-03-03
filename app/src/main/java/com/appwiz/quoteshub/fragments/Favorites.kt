package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appwiz.quoteshub.R
import kotlinx.android.synthetic.main.favorite_quotes_list.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.adapters.FavAdapter
import com.appwiz.quoteshub.room.AppDB
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Favorites: Fragment(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_quotes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val title: TextView = activity!!.findViewById(R.id.app_tool_bar_title)
        title.text = "Favorites"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = context?.let { AppDB(it) }

        val adapter = context?.let { FavAdapter(it) }
        fav_recycler.adapter = adapter
        fav_recycler.layoutManager = LinearLayoutManager(context)

        launch {
            withContext(Dispatchers.IO) {
                if (adapter != null && db != null) {
                    adapter.setFav(db.favDao().showAll())
                }
            }
        }
    }
}