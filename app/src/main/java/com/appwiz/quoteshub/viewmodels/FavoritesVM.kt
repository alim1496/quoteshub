package com.appwiz.quoteshub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appwiz.quoteshub.repositories.FavRepo
import com.appwiz.quoteshub.room.entity.FavEntity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FavoritesVM(private val repository: FavRepo): ViewModel() {
    val favorites: LiveData<List<FavEntity>> = repository.favorites

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    fun checkEmptyData() {
        MainScope().launch {
            val count = repository.checkEmpty()
            if (count == 0) {
                _empty.postValue(true)
            } else {
                _empty.postValue(false)
            }
        }
    }

    fun removeFavorite(id: Int) {
        MainScope().launch {
            repository.removeFav(id)
        }
        checkEmptyData()
    }
}