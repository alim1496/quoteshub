package com.example.quoteshub.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.adapters.AuthorQuotesAdapter
import com.example.quoteshub.models.AuthorDetails
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_author.*
import retrofit2.Call
import retrofit2.Callback
import java.text.FieldPosition


class SingleAuthor : AppCompatActivity() {
    var adapter: AuthorQuotesAdapter?= null
    var loading: Boolean = true
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0
    var pageRequested: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.quoteshub.R.layout.single_author)
        setSupportActionBar(collapseToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("authorID")
        val count = intent.extras?.getInt("authorQuotes")
        val name = intent.extras?.getString("authorname")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        auth_quotes_recycle.layoutManager = layoutManager

        title = name
        quotes_title.text = "Quotes By ${name}"

        auth_quotes_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            // val page: Int = (totalItemCount/10) + 1
                            pageRequested += 1
                            loading = false
                            if (id != null) {
                                loadMore(id, pageRequested)
                            }
                        }
                    }
                }
            }
        })

        if (id != null && count != null) {
            loadData(id, count, 1)
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

    private fun loadMore(id: Int, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id, page)
        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    val authorDetails : AuthorDetails = response.body()!!
                    val authorQuotes = authorDetails.quotes
                    adapter?.addItems(authorQuotes.results)
                }
            }

            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }

    private fun loadData(id: Int, count: Int, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id, page)

        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    val authorDetails : AuthorDetails = response.body()!!
                    quotes_count.text = count.toString()
                    Picasso.get().load(authorDetails.source.image).into(single_author_img)
                    author_desc.text = authorDetails.source.shortDesc

                    val authorQuotes = authorDetails.quotes
                    adapter = AuthorQuotesAdapter(applicationContext, authorQuotes.results)
                    auth_quotes_recycle.adapter = adapter
                }
            }

            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }
}