package com.appwiz.quoteshub.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE = "favorites"

@Database(entities = [FavEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun favDao() : FavDao

    companion object {

        @Volatile private var instance: AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDB::class.java, DATABASE)
            .build()

    }
}
