package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.appwiz.quoteshub.room.Converter

@Entity
data class DayQuoteEntity (
    @PrimaryKey
    val id:Int = 0,
    val text:String = "",
    val author:String = "",
    @TypeConverters(Converter::class)
    val tags: ArrayList<Tag> = ArrayList()
)

data class Tag (
    val id: Int = 0,
    val name:String = ""
)