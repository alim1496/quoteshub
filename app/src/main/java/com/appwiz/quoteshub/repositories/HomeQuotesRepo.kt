package com.appwiz.quoteshub.repositories

import com.appwiz.quoteshub.models.FeedModel
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeQuotesRepo {
    private lateinit var requestCall: Call<FeedModel>

    fun retrieveHomeQuotes(callback: OperationCallback) {
        requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getFeed()
        requestCall.enqueue(object : Callback<FeedModel> {
            override fun onFailure(call: Call<FeedModel>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<FeedModel>, response: Response<FeedModel>) {
                if (response.isSuccessful) {
                    val feedResponse = response.body()!!
                    callback.onSuccess(feedResponse)
                } else {
                    callback.onError("Error")
                }
            }

        })
    }
}