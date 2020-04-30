package com.appwiz.quoteshub.repositories

import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.room.dao.HomeDao
import com.appwiz.quoteshub.room.entity.AuthorEntity
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorsRepo(private val dao:HomeDao) {
    private lateinit var requestCall: Call<List<Author>>

    fun retrieveAuthors(page: Int, letter: String, callback: OperationCallback) {
        requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getAuthors(page, letter)
        requestCall.enqueue(object : Callback<List<Author>> {
            override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                if (response.isSuccessful) {
                    val authors = response.body()!!
                    callback.onSuccess(authors)
                } else {
                    callback.onError("Error")
                }
            }

        })
    }

    suspend fun addAuthorsToDB(authors:List<AuthorEntity>) {
        dao.addAuthor(authors)
    }

    suspend fun getAuthors(letter: String) : List<AuthorEntity> {
        return dao.showAuthor(letter)
    }
}