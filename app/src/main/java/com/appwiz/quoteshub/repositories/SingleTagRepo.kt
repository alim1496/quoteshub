package com.appwiz.quoteshub.repositories

import com.appwiz.quoteshub.models.Response
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class SingleTagRepo {
    private lateinit var requestCall: Call<Response>

    fun retrieveTags(id: Int, page: Int, callback: OperationCallback) {
        requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getTagQuotes(id, page)
        requestCall.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val tags = response.body()!!
                    callback.onSuccess(tags.results)
                } else {
                    callback.onError("Error")
                }
            }

        })
    }
}