package com.appwiz.quoteshub.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.AuthorDetails
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.appwiz.quoteshub.adapters.AuthorQuotesAdapter
import com.appwiz.quoteshub.utils.CarouselItemIndicator
import com.appwiz.quoteshub.utils.SliderLayoutManager
import retrofit2.Response


class SingleAuthor : AppCompatActivity() {
    lateinit var name: String
    lateinit var toolbar: Toolbar
    lateinit var cover: ImageView
    lateinit var button: TextView
    lateinit var desc: TextView
    lateinit var loader: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var indicator: CarouselItemIndicator
    lateinit var authorQuotesAdapter: AuthorQuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_author)

        toolbar = findViewById(R.id.toolbar)
        cover = findViewById(R.id.author_cover)
        desc = findViewById(R.id.author_desc)
        loader = findViewById(R.id.loader)
        recyclerView = findViewById(R.id.quotesRV)
        indicator = findViewById(R.id.item_indicator)
        button = findViewById(R.id.see_more)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("authorID")
        name = intent.extras?.getString("authorname")!!

        title = name

        id?.let { getAuthorDetails(it) }

        button.setOnClickListener {
            val intent = Intent(this, ActivityQuotes::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("type", "source")
            startActivity(intent)
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

    private fun getAuthorDetails(id: Int) {
        val requestCall: Call<AuthorDetails> = ServiceBuilder.buildService(DestinationServices::class.java).getAuthorDetails(id);
        requestCall.enqueue(object : Callback<AuthorDetails> {
            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                loader.visibility = View.GONE
            }

            override fun onResponse(call: Call<AuthorDetails>, response: Response<AuthorDetails>) {
                loader.visibility = View.GONE
                button.visibility = View.VISIBLE
                val res: AuthorDetails = response.body()!!
                desc.text = res.source.shortDesc
                Picasso.get().load(res.source.cover).placeholder(R.drawable.empty_cover).into(cover)
                val quotes = res.quotes
                if (quotes.isNotEmpty()) {
                    indicator.visibility = View.VISIBLE
                    indicator.initialize(quotes.size, 0) {pos -> recyclerView.smoothScrollToPosition(pos)}
                    authorQuotesAdapter = AuthorQuotesAdapter(quotes)
                    recyclerView.adapter = authorQuotesAdapter
                    recyclerView.layoutManager = SliderLayoutManager(applicationContext) {pos:Int -> indicator.changeSelection(pos)}
                }
            }

        })
    }
}