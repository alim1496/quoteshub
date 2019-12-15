package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.models.Response
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.single_tag.*
import retrofit2.Call
import retrofit2.Callback

class SingleTag : AppCompatActivity() {
    var adapter: QuotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_tag)
        setSupportActionBar(tag_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        val id: Int? = intent.extras?.getInt("tagID")
        val name: String? = intent.extras?.getString("tagName")
        title = name

        if (id != null) {
            loadData(layoutManager, id)
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

    private fun loadData(layoutManager: LinearLayoutManager, id: Int) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<Response> = destinationServices.getTagQuotes(id)
        requestCall.enqueue(object: Callback<Response> {

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

                if (response.isSuccessful) {
                    single_tag_loader.visibility = View.GONE
                    single_tag_recycler.visibility = View.VISIBLE
                    val quoteList : Response = response.body()!!
                    single_tag_recycler.layoutManager = layoutManager
                    adapter = QuotesAdapter(applicationContext, quoteList.results, false)
                    single_tag_recycler.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }
}