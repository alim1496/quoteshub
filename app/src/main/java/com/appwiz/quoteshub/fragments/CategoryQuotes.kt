package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.models.LatestFeed
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.viewmodels.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryQuotes : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: ProgressBar
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: QuotesAdapter
    private var _id:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_quotes_container, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        loader = view.findViewById(R.id.loader)
        swipe = view.findViewById(R.id.swipe)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _id = arguments!!.getInt("category_id")

        fetchQuotes(_id)

        adapter = QuotesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            fetchQuotes(_id)
        }
        return view
    }

    private fun fetchQuotes(id:Int) {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        call.getCategoryQuotes(id, 1, 10)
            .enqueue(object : Callback<List<Quote>> {
                override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                    Log.e("category", "error " + t.message)
                    loader.visibility = View.GONE
                }

                override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                    if (response.isSuccessful) {
                        Log.e("category", "response " + response.body())
                        val quotes = response.body()!!
                        adapter.reloadData(quotes.toMutableList())
                        loader.visibility = View.GONE
                    }
                }
            })
    }
}