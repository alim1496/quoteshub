package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.InfiniteScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityQuotes : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var loader: ProgressBar
    private lateinit var recyclerView: RecyclerView
    lateinit var error: RelativeLayout
    private lateinit var networkState: MutableLiveData<com.appwiz.quoteshub.utils.NetworkState>
    private lateinit var adapter: QuotesAdapter
    private var pageCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotes_holder_common)

        toolbar = findViewById(R.id.toolbar)
        loader = findViewById(R.id.loader)
        recyclerView = findViewById(R.id.quotesRV)
        error = findViewById(R.id.auth_net_err)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("id")!!
        val name = intent.extras?.getString("name")!!

        title = "$name Quotes"

        networkState = MutableLiveData()
        fetchQuotes(id, pageCount)

        adapter = QuotesAdapter(false)
        recyclerView.adapter = adapter
        val llm = LinearLayoutManager(this)
        recyclerView.layoutManager = llm
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        networkState.observe(this, Observer {
            (adapter::setNetworkState)(it)
        })

        val listener = object: InfiniteScrollListener(llm) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                pageCount += 1
                if (networkState.value == com.appwiz.quoteshub.utils.NetworkState.LOADED) {
                    fetchQuotes(id, pageCount)
                }
            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    private fun fetchQuotes(id:Int, page:Int) {
        Log.e("category", "inside fetch quotes")
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        if (page > 1) {
            networkState.postValue(com.appwiz.quoteshub.utils.NetworkState.LOADING)
        } else {
            networkState.postValue(com.appwiz.quoteshub.utils.NetworkState.LOADED)
        }
        call.getMoreQuotes(id, page)
            .enqueue(object : Callback<List<Quote>> {
                override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                    Log.e("category", "error " + t.message)
                    loader.visibility = View.GONE
                    if (page > 1) {
                        networkState.postValue(com.appwiz.quoteshub.utils.NetworkState.error("something went wrong"))
                        Toast.makeText(this@ActivityQuotes, "something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                    if (response.isSuccessful) {
                        val quotes = response.body()!!
                        Log.e("category", "response is "+quotes.size)
                        if (page == 1) adapter.reloadData(quotes.toMutableList())
                        else {
                            adapter.appendData(quotes.toMutableList())
                            networkState.postValue(com.appwiz.quoteshub.utils.NetworkState.LOADED)
                        }
                        loader.visibility = View.GONE
                    } else {
                        Log.e("category", "oh no")
                    }
                }
            })
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