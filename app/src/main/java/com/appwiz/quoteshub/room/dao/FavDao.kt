package com.appwiz.quoteshub.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appwiz.quoteshub.room.entity.FavEntity

@Dao
interface FavDao {
    @Insert
    fun addFav(fav: FavEntity)

    @Query("select * from FavEntity")
    fun showAll() : List<FavEntity>

    @Query("delete from FavEntity where id = :id")
    fun removeFav(id:Int)
}
