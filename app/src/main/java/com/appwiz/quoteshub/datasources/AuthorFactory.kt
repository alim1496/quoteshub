package com.appwiz.quoteshub.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.appwiz.quoteshub.models.Author


class AuthorFactory : DataSource.Factory<Int, Author>() {
    val data = MutableLiveData<AuthorDataSource>()

    override fun create(): DataSource<Int, Author> {
        val dataSource = AuthorDataSource()
        data.postValue(dataSource)
        return dataSource
    }
}