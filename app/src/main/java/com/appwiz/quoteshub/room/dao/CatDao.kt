package com.appwiz.quoteshub.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appwiz.quoteshub.models.Category

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCats(cats: List<Category>)

    @Query("select * from Category")
    fun showCats(): List<Category>

    @Query("select count(*) from Category")
    suspend fun checkEmptyCategories(): Int

}