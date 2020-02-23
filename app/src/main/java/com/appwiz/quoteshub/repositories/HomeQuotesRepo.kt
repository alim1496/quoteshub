package com.appwiz.quoteshub.repositories

import androidx.lifecycle.LiveData
import com.appwiz.quoteshub.models.FeedModel
import com.appwiz.quoteshub.room.dao.HomeDao
import com.appwiz.quoteshub.room.entity.*
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.OperationCallback
import com.appwiz.quoteshub.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeQuotesRepo(private val dao: HomeDao) {
    val recentQuotes:LiveData<List<HomeEntity>> = dao.showHome(0)
    val featuredQuotes:LiveData<List<HomeEntity>> = dao.showHome(1)
    val featuredAuthors:LiveData<List<AuthorEntity>> = dao.showAuthor()
    val eventsToday:LiveData<List<EventEntity>> = dao.showEvent()
    val dayQuote:LiveData<DayQuoteEntity> = dao.showDayQuote()
    val homeTitles:LiveData<List<TitleEntity>> = dao.showTitle()
    private lateinit var requestCall: Call<FeedModel>

    suspend fun addHomeQuotes(homeQuotes:List<HomeEntity>) {
        dao.addHome(homeQuotes)
    }

    suspend fun checkEmptyHomeQuote(type:Int): Int {
        return dao.checkEmptyHome(type)
    }

    suspend fun addHomeTitles(titleEntity: TitleEntity) {
        dao.addTitle(titleEntity)
    }

    suspend fun addEventToDB(recents:List<EventEntity>) {
        dao.removeEvent()
        dao.addEvent(recents)
    }

    suspend fun addAuthorToDB(recents:List<AuthorEntity>) {
        dao.addAuthor(recents)
    }

    suspend fun addDayToDB(dayQuoteEntity: DayQuoteEntity) {
        dao.removeDayQuote()
        dao.addDayQuote(dayQuoteEntity)
    }

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