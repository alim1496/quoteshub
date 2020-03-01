package com.appwiz.quoteshub.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appwiz.quoteshub.room.entity.FavEntity

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(fav: FavEntity)

    @Query("select * from FavEntity order by id desc")
    fun showAll() : List<FavEntity>

    @Query("delete from FavEntity where id = :id")
    suspend fun removeFav(id:Int)
}
