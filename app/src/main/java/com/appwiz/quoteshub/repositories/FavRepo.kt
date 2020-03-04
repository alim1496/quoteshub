package com.appwiz.quoteshub.repositories

import androidx.lifecycle.LiveData
import com.appwiz.quoteshub.room.dao.FavDao
import com.appwiz.quoteshub.room.entity.FavEntity

class FavRepo(private val dao: FavDao) {
    val favorites: LiveData<List<FavEntity>> = dao.showAll()

    suspend fun checkEmpty(): Int {
        return dao.checkEmpty()
    }

    suspend fun removeFav(id: Int) {
        dao.removeFav(id)
    }
}