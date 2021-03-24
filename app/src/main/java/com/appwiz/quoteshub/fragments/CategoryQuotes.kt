package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.InfiniteScrollListener
import com.appwiz.quoteshub.utils.NetworkState
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
    private lateinit var networkState: MutableLiveData<NetworkState>
    private var _id:Int = 0
    private var pageCount = 1

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
        networkState = MutableLiveData()

        fetchQuotes(_id, pageCount, 10)

        adapter = QuotesAdapter()
        recyclerView.adapter = adapter
        val llm = LinearLayoutManager(context)
        recyclerView.layoutManager = llm
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        networkState.observe(viewLifecycleOwner, Observer {
            (adapter::setNetworkState)(it)
        })

        val listener = object: InfiniteScrollListener(llm) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                pageCount += 1
                if (networkState.value == NetworkState.LOADED) {
                    fetchQuotes(_id, pageCount, 10)
                }
            }

        }
        recyclerView.addOnScrollListener(listener)

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            pageCount = 1
            fetchQuotes(_id, pageCount, 10)
        }
        return view
    }

    private fun fetchQuotes(id:Int, page:Int, size:Int) {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        if (page > 1) {
            networkState.postValue(NetworkState.LOADING)
        } else {
            networkState.postValue(NetworkState.LOADED)
        }
        call.getCategoryQuotes(id, page, size)
            .enqueue(object : Callback<List<Quote>> {
                override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                    Log.e("category", "error " + t.message)
                    loader.visibility = View.GONE
                    if (page > 1) {
                        networkState.postValue(NetworkState.error("something went wrong"))
                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                    if (response.isSuccessful) {
                        val quotes = response.body()!!
                        if (page == 1) adapter.reloadData(quotes.toMutableList())
                        else {
                            adapter.appendData(quotes.toMutableList())
                            networkState.postValue(NetworkState.LOADED)
                        }
                        loader.visibility = View.GONE
                    }
                }
            })
    }
}