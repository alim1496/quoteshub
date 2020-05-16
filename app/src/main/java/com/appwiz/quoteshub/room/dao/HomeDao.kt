package com.appwiz.quoteshub.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appwiz.quoteshub.room.entity.*

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHomeQuote(homeEntity: List<HomeQuote>)

    @Query("select * from HomeQuote where featured = :featured order by id desc")
    fun getHomeQuote(featured:Int) : DataSource.Factory<Int, HomeQuote>

    @Query("delete from HomeQuote where featured = :featured")
    fun clearQuotes(featured: Int)

}