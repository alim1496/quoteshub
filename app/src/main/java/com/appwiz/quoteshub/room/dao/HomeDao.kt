package com.appwiz.quoteshub.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appwiz.quoteshub.room.entity.*

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHome(homeEntity: List<HomeEntity>)

    @Query("select * from HomeEntity where type = :type")
    fun showHome(type:Int):LiveData<List<HomeEntity>>

    @Query("delete from HomeEntity where type = :type")
    suspend fun removeHome(type:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTitle(titleEntity: TitleEntity)

    @Query("select * from TitleEntity")
    fun showTitle() : LiveData<List<TitleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuthor(authorEntity: List<AuthorEntity>)

    @Query("select * from AuthorEntity")
    fun showAuthor():LiveData<List<AuthorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDayQuote(dayQuoteEntity: DayQuoteEntity)

    @Query("select * from DayQuoteEntity")
    fun showDayQuote():LiveData<DayQuoteEntity>

    @Query("delete from DayQuoteEntity")
    suspend fun removeDayQuote()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(eventEntity: List<EventEntity>)

    @Query("select * from EventEntity")
    fun showEvent():LiveData<List<EventEntity>>

    @Query("delete from EventEntity")
    suspend fun removeEvent()
}