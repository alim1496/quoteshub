package com.appwiz.quoteshub.datasources

import android.util.Log
import androidx.paging.PagedList
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.HomeQuote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeBoundary(val db: AppDB, val featured:Int) : PagedList.BoundaryCallback<HomeQuote>() {
    private lateinit var call: Call<List<Quote>>
    private var pageRequested = 0

    override fun onZeroItemsLoaded() {
        pageRequested = 1
        getHomeQuotes(featured, pageRequested)
    }

    override fun onItemAtEndLoaded(itemAtEnd: HomeQuote) {
        getHomeQuotes(featured, pageRequested)
    }

    fun getHomeQuotes(featured:Int, page:Int) {
        call = ServiceBuilder.buildService(DestinationServices::class.java).getFeed(featured, page)
        call.enqueue(object : Callback<List<Quote>>{
            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                val quotes = response.body()!!
                val homeQuotes: MutableList<HomeQuote> = ArrayList()
                if (!quotes.isNullOrEmpty()) {
                    for (quote in quotes) {
                        val homeQuote = HomeQuote(quote.id, quote.title, quote.source.name, quote.featured, "")
                        homeQuotes.add(homeQuote)
                    }
                    CoroutineScope(IO).launch {
                        db.homeDao().addHomeQuote(homeQuotes)
                        pageRequested++
                    }
                }
            }
        })
    }
}