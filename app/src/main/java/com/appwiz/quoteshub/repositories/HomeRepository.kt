package com.appwiz.quoteshub.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appwiz.quoteshub.datasources.HomeBoundary
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.HomeQuote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeRepository(private val db: AppDB) {

    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

    fun getQuotes(featured:Int) : LiveData<PagedList<HomeQuote>> {
        CoroutineScope(IO).launch { db.homeDao().clearQuotes(featured) }
        val dataSourceFactory = db.homeDao().getHomeQuote(featured)
        val boundaryCallback = HomeBoundary(db, featured)
        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE).setBoundaryCallback(boundaryCallback).build()
    }

}