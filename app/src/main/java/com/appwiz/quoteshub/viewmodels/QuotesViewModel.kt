package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appwiz.quoteshub.datasources.QuoteFactory
import com.appwiz.quoteshub.datasources.QuotesDataSource
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.NetworkState

class QuotesViewModel(val id:Int, val type:String) : ViewModel() {
    private val quotes: LiveData<PagedList<Quote>>
    private val factory = QuoteFactory(id, type)
    private val pageSize = 10

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        quotes = LivePagedListBuilder(factory, config).build()
    }

    fun getQuoteList() : LiveData<PagedList<Quote>> = quotes

    fun getInitialState() : LiveData<NetworkState> = Transformations.switchMap<QuotesDataSource, NetworkState>(factory.data, QuotesDataSource::state)

}