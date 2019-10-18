package com.example.quoteshub.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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
import kotlinx.android.synthetic.main.single_category.*
import retrofit2.Call
import retrofit2.Callback

class SingleCategory : AppCompatActivity() {
    var adapter: CategoryQuotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_category)
        setSupportActionBar(cat_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        val id: Int? = intent.extras?.getInt("catID")
        val name: String? = intent.extras?.getString("catName")
        setTitle(name)

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
        val requestCall : Call<Response> = destinationServices.getCategoryQuotes(id)
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