package com.example.quoteshub.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.adapters.AuthorQuotesAdapter
import com.example.quoteshub.adapters.CategoryQuotesAdapter
import com.example.quoteshub.models.AuthorDetails
import com.example.quoteshub.models.Response
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.single_author.*
import retrofit2.Call
import retrofit2.Callback
import android.widget.LinearLayout
import android.view.animation.Animation
import android.service.autofill.Transformation
import android.view.View


class SingleAuthor : AppCompatActivity() {
    var adapter: AuthorQuotesAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.quoteshub.R.layout.single_author)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("authorID")
        val count = intent.extras?.getInt("authorQuotes")
        val name = intent.extras?.getString("authorname")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        setTitle(name)
        quotes_title.text = "Quotes By ${name}"

        if (id != null && count != null) {
            loadData(id, count, layoutManager)
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

    private fun loadData(id: Int, count: Int, layoutManager: LinearLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id)

        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    val authorDetails : AuthorDetails = response.body()!!
                    quotes_count.text = count.toString()
                    Picasso.get().load(authorDetails.source.image).into(single_author_img)
                    author_desc.text = authorDetails.source.shortDesc

                    val authorQuotes = authorDetails.quotes
                    auth_quotes_recycle.layoutManager = layoutManager
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