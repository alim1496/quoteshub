package com.example.quoteshub.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.quoteshub.R
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

class SingleAuthor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_author)

        val id = intent.extras?.getInt("authorID")
        val count = intent.extras?.getInt("authorQuotes")
        if (id != null && count != null) {
            loadData(id, count)
        }
    }

    private fun loadData(id: Int, count: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorDetails> = destinationServices.getAuthorDetails(id)

        requestCall.enqueue(object: Callback<AuthorDetails> {
            override fun onResponse(call: Call<AuthorDetails>, response: retrofit2.Response<AuthorDetails>) {

                if (response.isSuccessful) {
                    val authorDetails : AuthorDetails = response.body()!!
                    quotes_count.text = count.toString()
                    Picasso.get().load(authorDetails.source.image).into(single_author_img)
                }
            }

            override fun onFailure(call: Call<AuthorDetails>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }
}