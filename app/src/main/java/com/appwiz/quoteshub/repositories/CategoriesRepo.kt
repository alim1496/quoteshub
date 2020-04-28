package com.appwiz.quoteshub.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.models.CategoryModel
import com.appwiz.quoteshub.room.dao.CatDao
import com.appwiz.quoteshub.room.entity.CatEntity
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesRepo(private val dao: CatDao) {
    val categories:LiveData<List<CatEntity>> = dao.showCats()
    private lateinit var requestCall: Call<List<Category>>

    suspend fun addCatToDB(cats: List<CatEntity>) {
        dao.addCats(cats)
    }

    suspend fun checkEmpty(): Int {
        return dao.checkEmptyCategories()
    }

    fun retrieveCategories(callback: OperationCallback) {
        requestCall = ServiceBuilder.buildService(DestinationServices::class.java).getCategories()
        requestCall.enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val categories = response.body()!!
                    callback.onSuccess(categories)
                } else {
                    callback.onError("Error")
                }
            }

        })
    }
}