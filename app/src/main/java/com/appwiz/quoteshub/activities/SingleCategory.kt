package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.CategoryQuotesAdapter
import com.appwiz.quoteshub.models.Response
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.single_category.*
import retrofit2.Call
import retrofit2.Callback

class SingleCategory : AppCompatActivity() {
    var adapter: CategoryQuotesAdapter? = null
    var scrolling: Boolean = false
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0
    var pageRequested: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_category)
        setSupportActionBar(cat_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        val id: Int? = intent.extras?.getInt("catID")
        val name: String? = intent.extras?.getString("catName")
        val more: String? = intent.extras?.getString("moreName")
        title = name

        single_cat_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (scrolling) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount
                            && pastVisiblesItems >= 0 && totalItemCount >= 10) {
                            pageRequested += 1
                            if (id != null && id != 0) {
                                fetchMore(id, pageRequested)
                            } else if (more != null) {
                                fetchHomeMore(more, pageRequested)
                            }
                        }
                    }
                }
            }
        })

        if (id != null && id != 0) {
            fetchData(layoutManager, id, 1)
        } else if (more != null) {
            fetchHomeData(layoutManager, more, 1)
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

    private fun fetchData(layoutManager: LinearLayoutManager, id: Int, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getCategoryQuotes(id, page)
        loadData(requestCall, layoutManager)
    }

    private fun fetchHomeData(layoutManager: LinearLayoutManager, more: String, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getMoreQuotes(more, page)
        Log.e("fetch", "yo")
        loadData(requestCall, layoutManager)
    }

    private fun fetchMore(id: Int, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getCategoryQuotes(id, page)
        loadMore(requestCall)
    }

    private fun fetchHomeMore(more: String, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getMoreQuotes(more, page)
        loadMore(requestCall)
    }

    private fun loadMore(requestCall : Call<Response>) {
        requestCall.enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

                if (response.isSuccessful) {
                    val quoteList : Response = response.body()!!
                    adapter?.addItems(quoteList.results)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }

    private fun loadData(requestCall : Call<Response>, layoutManager: LinearLayoutManager) {
        requestCall.enqueue(object: Callback<Response> {

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

                if (response.isSuccessful) {
                    single_category_loader.visibility = View.GONE
                    single_cat_recycler.visibility = View.VISIBLE
                    val quoteList : Response = response.body()!!
                    single_cat_recycler.layoutManager = layoutManager
                    adapter = CategoryQuotesAdapter(applicationContext, quoteList.results)
                    single_cat_recycler.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }
}