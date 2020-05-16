package com.appwiz.quoteshub.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.NetworkState
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class AuthorDataSource : PageKeyedDataSource<Int, Author>() {
    var state: MutableLiveData<NetworkState> = MutableLiveData()
    var more: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Author>) {}

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Author>) {
        val requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getAuthors(1)
        state.postValue(NetworkState.LOADING)
        requestCall.enqueue(object : retrofit2.Callback<List<Author>> {
            override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                state.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                val authors = response.body()!!
                state.postValue(NetworkState.LOADED)
                callback.onResult(authors, null, 2)
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Author>) {
        val requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getAuthors(params.key)
        more.postValue(NetworkState.LOADING)
        requestCall.enqueue(object : retrofit2.Callback<List<Author>> {
            override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                more.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                val authors = response.body()!!
                more.postValue(NetworkState.LOADED)
                callback.onResult(authors, params.key + 1)
            }

        })
    }
}