package com.appwiz.quoteshub.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.fragments.QuoteActions
import com.appwiz.quoteshub.models.AuthorDetails
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_author.*
import retrofit2.Call
import retrofit2.Callback
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.res.ResourcesCompat


class SingleAuthor : AppCompatActivity() {
    var adapter: QuotesAdapter?= null
    var scrolling: Boolean = false
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0
    var pageRequested: Int = 1
    var name: String? = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_author)
        setSupportActionBar(collapseToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("authorID")
        name = intent.extras?.getString("authorname")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        auth_quotes_recycle.layoutManager = layoutManager

        title = name
        quotes_title.text = "Quotes By ${name}"

        sharedPreferences = getSharedPreferences("Favorite Authors", 0)
        val editor = sharedPreferences.edit()

        if (sharedPreferences.getInt("FavAuthor$id", 0) != 0) {
            action_author_star.setImageResource(R.drawable.ic_star)
        }

        action_author_star.setOnClickListener {
            if (sharedPreferences.getInt("FavAuthor$id", 0) != 0) {
                editor.remove("FavAuthor$id")
                editor.apply()
                action_author_star.setImageResource(R.drawable.ic_star_unfilled)
            } else {
                id?.let { it1 -> editor.putInt("FavAuthor$id", it1) }
                editor.apply()
                action_author_star.setImageResource(R.drawable.ic_star)
            }
        }

        auth_quotes_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            pageRequested += 1
                            if (id != null) {
                                loadMore(id, pageRequested)
                            }
                        }
                    }
                }
            }
        })

        if (id != null) {
            loadData(id, 1)
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
        single_author_loader_more.visibility = View.VISIBLE
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id, page)
        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    single_author_loader_more.visibility = View.GONE
                    val authorDetails : AuthorDetails = response.body()!!
                    val authorQuotes = authorDetails.quotes
                    adapter?.addItems(authorQuotes.results)
                }
            }

            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                single_author_loader_more.visibility = View.GONE
            }
        })
    }

    private fun loadData(id: Int, page: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id, page)

        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    single_author_loader.visibility = View.GONE
                    author_app_bar_layout.visibility = View.VISIBLE
                    author_linear_layout.visibility = View.VISIBLE
                    val authorDetails : AuthorDetails = response.body()!!

                    Picasso.get().load(authorDetails.source.image).placeholder(R.drawable.avatar_placeholder).into(single_author_img)
                    author_desc.text = authorDetails.source.shortDesc

                    val authorQuotes = authorDetails.quotes

                    quotes_count.text = authorDetails.quotes.count.toString()
                    adapter = QuotesAdapter(applicationContext, authorQuotes.results, true) {quote: Quote ->
                        val quoteActions = name?.let { QuoteActions(quote, it) }
                        quoteActions?.show(supportFragmentManager, quoteActions?.tag)
                    }
                    auth_quotes_recycle.adapter = adapter
                    auth_quotes_recycle.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))

                    if (authorQuotes.results.isEmpty()) {
                        tv_empty_author_quote.visibility = View.VISIBLE
                    } else {
                        tv_empty_author_quote.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                single_author_loader.visibility = View.GONE
            }
        })
    }
}