package com.appwiz.quoteshub.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatDao {
    @Insert
    fun addCat(cat: CatEntity)

    @Query("select * from CatEntity")
    fun showCats(): LiveData<List<CatEntity>>

    @Query("delete from CatEntity")
    fun deleteCats()
}