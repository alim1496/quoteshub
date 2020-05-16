package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.HomeQuotesAdapter
import com.appwiz.quoteshub.viewmodels.HomeViewModel

class LatestQuotes(private val viewmodel:HomeViewModel) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: ProgressBar
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_quotes_container, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        loader = view.findViewById(R.id.loader)
        swipe = view.findViewById(R.id.swipe)
        viewmodel.setLatest()
        val adapter = context?.let { HomeQuotesAdapter(it) }
        viewmodel.getLatest().observe(viewLifecycleOwner, Observer {
            showEmptyList(it.size == 0)
            adapter?.submitList(it)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewmodel.setLatest()
        }
        return view
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            loader.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            loader.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}