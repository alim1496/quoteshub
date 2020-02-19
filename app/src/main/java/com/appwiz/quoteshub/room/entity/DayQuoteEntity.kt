package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayQuoteEntity (
    @PrimaryKey
    val id:Int = 0,
    val text:String = "",
    val author:String = ""
    // val tags: ArrayList<Tag> = ArrayList()
)

data class Tag (
    val id: Int = 0,
    val name:String = ""
)