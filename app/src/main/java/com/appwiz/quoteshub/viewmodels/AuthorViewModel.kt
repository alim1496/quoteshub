package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appwiz.quoteshub.datasources.AuthorDataSource
import com.appwiz.quoteshub.datasources.AuthorFactory
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.services.NetworkState

class AuthorViewModel : ViewModel() {
    private val authors: LiveData<PagedList<Author>>
    private val factory = AuthorFactory()
    private val pageSize = 10

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        authors = LivePagedListBuilder(factory, config).build()
    }

    fun getAuthorList() : LiveData<PagedList<Author>> = authors

    fun getInitialState() : LiveData<NetworkState> = Transformations.switchMap<AuthorDataSource, NetworkState>(factory.data, AuthorDataSource::state)

    fun getMoreState() : LiveData<NetworkState> = Transformations.switchMap<AuthorDataSource, NetworkState>(factory.data, AuthorDataSource::more)

    fun isListEmpty() : Boolean = authors.value?.isEmpty() ?: true
}