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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.adapters.FavAdapter
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.FavEntity
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.FavoritesVM
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Favorites: Fragment() {
    lateinit var adapter: FavAdapter
    lateinit var viewModel: FavoritesVM

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
        setupVM()
        setupUI()
    }

    private fun setupUI() {
        adapter = FavAdapter(viewModel.favorites.value?: emptyList()) { fav:FavEntity ->
            val removeQuote = RemoveQuote(fav.id, viewModel)
            fragmentManager?.let { removeQuote.show(it, removeQuote.tag) }
        }
        fav_recycler.adapter = adapter
        fav_recycler.layoutManager = LinearLayoutManager(context)
        fav_recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun setupVM() {
        val db = context?.let { AppDB(it) }
        if (db != null) {
            viewModel = ViewModelProviders.of(this, BaseViewModelFactory{FavoritesVM(Injection.getFavoritesRepo(db.favDao()))}).get(FavoritesVM::class.java)
        }
        viewModel.favorites.observe(this, renderQuotes)
        viewModel.empty.observe(this, renderEmpty)
        viewModel.checkEmptyData()
    }

    private val renderQuotes = Observer<List<FavEntity>> {
        adapter.setFav(it)
    }

    private val renderEmpty = Observer<Boolean> {
        var visible = View.GONE
        if (it) visible = View.VISIBLE
        tv_empty_fav_quote.visibility = visible
    }
}