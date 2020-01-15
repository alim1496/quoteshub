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

    @Query("delete from FavEntity where id = :id")
    fun removeFav(id:Int)
}
