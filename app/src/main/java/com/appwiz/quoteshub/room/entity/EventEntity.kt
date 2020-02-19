package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity (
    @PrimaryKey
    val id:Int = 0,
    val text:String = ""
)