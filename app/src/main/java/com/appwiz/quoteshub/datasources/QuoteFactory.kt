package com.appwiz.quoteshub.datasources

import androidx.lifecycle.MutableLiveData
import com.appwiz.quoteshub.models.Quote
import androidx.paging.DataSource

class QuoteFactory(val id:Int, val type:String) : DataSource.Factory<Int, Quote>() {
    val data = MutableLiveData<QuotesDataSource>()

    override fun create(): DataSource<Int, Quote> {
        val dataSource = QuotesDataSource(id, type)
        data.postValue(dataSource)
        return dataSource
    }
}