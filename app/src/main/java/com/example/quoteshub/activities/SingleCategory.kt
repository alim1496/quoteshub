package com.example.quoteshub.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.adapters.CategoryQuotesAdapter
import com.example.quoteshub.adapters.QuotesAdapter
import com.example.quoteshub.models.Response
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback

class SingleCategory : AppCompatActivity() {
    var adapter: CategoryQuotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        val id: Int? = intent.extras?.getInt("catID")
        if (id != null) {
            loadData(layoutManager, id)
        }
    }

    private fun loadData(layoutManager: LinearLayoutManager, id: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getCategoryQuotes(id)
        requestCall.enqueue(object: Callback<Response> {

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

                if (response.isSuccessful) {
                    val quoteList : Response = response.body()!!
                    recyclerView.layoutManager = layoutManager
                    adapter = CategoryQuotesAdapter(applicationContext, quoteList.results)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }
}