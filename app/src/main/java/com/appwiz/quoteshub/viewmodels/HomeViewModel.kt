package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.appwiz.quoteshub.repositories.HomeRepository
import com.appwiz.quoteshub.room.entity.HomeQuote

class HomeViewModel(val repository:HomeRepository) : ViewModel() {
    private lateinit var latestQuotes: LiveData<PagedList<HomeQuote>>
    private lateinit var featuredQuotes: LiveData<PagedList<HomeQuote>>

    fun setLatest() {
        latestQuotes = repository.getQuotes(0)
    }

    fun setFeatured() {
        featuredQuotes = repository.getQuotes(1)
    }

    fun getLatest() : LiveData<PagedList<HomeQuote>> = latestQuotes

    fun getFeatured() : LiveData<PagedList<HomeQuote>> = featuredQuotes

}