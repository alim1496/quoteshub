package com.appwiz.quoteshub.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.models.LatestFeed
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.CatEntity
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var db:AppDB
    private var categoryList = MutableLiveData<List<Category>>()

    fun getCategories() : LiveData<List<Category>> {
        return categoryList
    }

    fun loadInitialData() {
        db = AppDB(getApplication())
        CoroutineScope(IO).launch {
            val size = db.catDao().checkEmptyCategories()
            if (size == 0) fetchInitialData()
            else categoryList.postValue(db.catDao().showCats())
        }
    }

    private fun fetchInitialData() {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        call.getFeedLatest(false, true, 1, 0)
            .enqueue(object : Callback<LatestFeed> {
                override fun onFailure(call: Call<LatestFeed>, t: Throwable) {
                    Log.e("home", "error " + t.message)
                }

                override fun onResponse(call: Call<LatestFeed>, response: Response<LatestFeed>) {
                    if (response.isSuccessful) {
                        val categories = response.body()!!.categories
                        CoroutineScope(IO).launch {
                            db.catDao().addCats(categories)
                        }
                        categoryList.value = categories
                    }
                }
            })
    }
}