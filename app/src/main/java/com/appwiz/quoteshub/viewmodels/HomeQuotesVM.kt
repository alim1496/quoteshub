package com.appwiz.quoteshub.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.models.FeedModel
import com.appwiz.quoteshub.repositories.HomeQuotesRepo
import com.appwiz.quoteshub.room.entity.*
import com.appwiz.quoteshub.services.OperationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class HomeQuotesVM(private val repository: HomeQuotesRepo) : ViewModel() {
    val recentQuotes:LiveData<List<HomeEntity>> = repository.recentQuotes
    val featuredQuotes:LiveData<List<HomeEntity>> = repository.featuredQuotes
    val eventsToday:LiveData<List<EventEntity>> = repository.eventsToday
    val dayQuote:LiveData<DayQuoteEntity> = repository.dayQuote
    val titlesHome:LiveData<List<TitleEntity>> = repository.homeTitles

    private val _quotes = MutableLiveData<FeedModel>()
    val quotes: LiveData<FeedModel> = _quotes

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _emptyEvent = MutableLiveData<Boolean>()
    val emptyEvent:LiveData<Boolean> = _emptyEvent

    fun loadHomeData() {
        repository.retrieveHomeQuotes(object : OperationCallback {

            override fun onSuccess(obj: Any?) {
                if (obj != null) {
                    val feedModel = obj as FeedModel

                    val _featureds:MutableList<HomeEntity> = ArrayList()
                    val _recents:MutableList<HomeEntity> = ArrayList()
                    val _authors:MutableList<AuthorEntity> = ArrayList()
                    val _dayTags:ArrayList<Tag> = ArrayList()
                    val _events:MutableList<EventEntity> = ArrayList()

                    val featureds = feedModel.FeaturedQuotes.data
                    val featuredBlock = TitleEntity(2, "featured", feedModel.FeaturedQuotes.title, feedModel.FeaturedQuotes.more)

                    val recents = feedModel.RecentQuotes.data
                    val recentBlock = TitleEntity(1, "recent", feedModel.RecentQuotes.title, feedModel.RecentQuotes.more)

                    val authors = feedModel.FeaturedAuthors.data
                    val authorBlock = TitleEntity(4, "author", feedModel.FeaturedAuthors.title)

                    val day = feedModel.DayQuote.data
                    val dayTags = day.tags
                    val dayBlock = TitleEntity(3, "day", feedModel.DayQuote.title)

                    val events = feedModel.EventsToday.data
                    val eventBlock = TitleEntity(5, "event", feedModel.EventsToday.title)


                    var fcount = 5
                    for (featured in featureds) {
                        fcount += 1
                        val entity = HomeEntity(
                            fcount,
                            featured.title,
                            featured.source.name,
                            1
                        )
                        _featureds.add(entity)
                    }

                    var rcount = 0
                    for (recent in recents) {
                        rcount += 1
                        val entity = HomeEntity(
                            rcount,
                            recent.title,
                            recent.source.name,
                            0
                        )
                        _recents.add(entity)
                    }


                    for (author in authors) {
                        val entity = AuthorEntity(
                            author.id,
                            author.name,
                            author.quotes,
                            author.image
                        )
                        _authors.add(entity)
                    }

                    for (tag in dayTags) {
                        val entity = Tag(tag.id, tag.name)
                        _dayTags.add(entity)
                    }
                    val dayEntity = DayQuoteEntity(
                        day.id,
                        day.title,
                        day.source.name,
                        _dayTags
                    )

                    if (events.isEmpty()) {
                        _emptyEvent.postValue(true)
                    } else {
                        _emptyEvent.postValue(false)
                        for (event in events) {
                            val entity = EventEntity(event.id, event.text)
                            _events.add(entity)
                        }
                    }

                    CoroutineScope(IO).launch {
                        repository.addHomeTitles(recentBlock)
                        repository.addHomeTitles(featuredBlock)
                        repository.addHomeTitles(dayBlock)
                        repository.addHomeTitles(authorBlock)
                        repository.addHomeTitles(eventBlock)
                        repository.addHomeQuotes(_featureds)
                        repository.addHomeQuotes(_recents)
                        repository.addAuthorToDB(_authors)
                        repository.addDayToDB(dayEntity)
                        repository.addEventToDB(_events)
                    }
                }
            }

            override fun onError(obj: Any?) {
                MainScope().launch {
                    val home = repository.checkEmptyHomeQuote(1)
                    if (home == 0) {
                        _onMessageError.postValue(obj)
                    }
                }
            }
        })
    }
}