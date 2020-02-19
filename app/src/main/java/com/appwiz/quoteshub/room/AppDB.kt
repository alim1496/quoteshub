package com.appwiz.quoteshub.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appwiz.quoteshub.room.dao.CatDao
import com.appwiz.quoteshub.room.dao.FavDao
import com.appwiz.quoteshub.room.dao.HomeDao
import com.appwiz.quoteshub.room.entity.*

private const val DATABASE = "favorites"


@Database(entities = [FavEntity::class, CatEntity::class, HomeEntity::class, TitleEntity::class,
    EventEntity::class, AuthorEntity::class, DayQuoteEntity::class], version = 6)
abstract class AppDB : RoomDatabase() {
    abstract fun favDao() : FavDao
    abstract fun catDao() : CatDao
    abstract fun homeDao() : HomeDao

    companion object {

        @Volatile private var instance: AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDB::class.java, DATABASE)
            .fallbackToDestructiveMigration()
            .build()

    }
}
