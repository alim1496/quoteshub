package com.appwiz.quoteshub.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appwiz.quoteshub.room.entity.CatEntity

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCats(cats: List<CatEntity>)

    @Query("select * from CatEntity")
    fun showCats(): LiveData<List<CatEntity>>

    @Query("select count(*) from CatEntity")
    suspend fun checkEmptyCategories(): Int

}