package com.appwiz.quoteshub.repositories

import com.appwiz.quoteshub.models.AuthorModel
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorsRepo {
    private lateinit var requestCall: Call<AuthorModel>

    fun retrieveAuthors(page: Int, letter: String, callback: OperationCallback) {
        requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getAuthors(page, letter)
        requestCall.enqueue(object : Callback<AuthorModel> {
            override fun onFailure(call: Call<AuthorModel>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<AuthorModel>, response: Response<AuthorModel>) {
                if (response.isSuccessful) {
                    val authors = response.body()!!
                    callback.onSuccess(authors.results)
                } else {
                    callback.onError("Error")
                }
            }

        })
    }
}