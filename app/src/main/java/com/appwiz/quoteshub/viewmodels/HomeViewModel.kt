package com.appwiz.quoteshub.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.models.LatestFeed
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var categoryList = MutableLiveData<List<Category>>()

    fun getCategories() : MutableLiveData<List<Category>> = categoryList

    fun loadInitialData() {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        call.getFeedLatest(false, true, 1, 0)
            .enqueue(object : Callback<LatestFeed> {
                override fun onFailure(call: Call<LatestFeed>, t: Throwable) {
                    Log.e("home", "error " + t.message)
                }

                override fun onResponse(call: Call<LatestFeed>, response: Response<LatestFeed>) {
                    if (response.isSuccessful) {
                        val categories = response.body()!!.categories
                        categoryList.postValue(categories)
                    }
                }
            })
    }
}