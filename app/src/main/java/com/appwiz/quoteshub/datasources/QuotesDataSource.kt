package com.appwiz.quoteshub.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.NetworkState
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class QuotesDataSource(val id:Int, val type:String) : PageKeyedDataSource<Int, Quote>() {
    var state: MutableLiveData<NetworkState> = MutableLiveData()
    lateinit var call: Call<List<Quote>>

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Quote>) {
        if (type.equals("source")) {
            call = ServiceBuilder.buildService(DestinationServices::class.java).getMoreQuotes(id, 1)
        } else {
            call = ServiceBuilder.buildService(DestinationServices::class.java).getCategoryQuotes(id, 1)
        }
        state.postValue(NetworkState.LOADING)
        call.enqueue(object : retrofit2.Callback<List<Quote>> {
            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                state.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                val quotes = response.body()!!
                state.postValue(NetworkState.LOADED)
                callback.onResult(quotes, null, 2)
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Quote>) {
        if (type.equals("source")) {
            call = ServiceBuilder.buildService(DestinationServices::class.java).getMoreQuotes(id, params.key)
        } else {
            call = ServiceBuilder.buildService(DestinationServices::class.java).getCategoryQuotes(id, params.key)
        }
        call.enqueue(object : retrofit2.Callback<List<Quote>> {
            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                val quotes = response.body()!!
                callback.onResult(quotes, params.key + 1)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Quote>) {

    }
}