package com.appwiz.quoteshub.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDao {
    @Insert
    fun addFav(fav:FavEntity)

    @Query("select * from FavEntity")
    fun showAll() : List<FavEntity>
}
