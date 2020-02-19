package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeEntity (
    @PrimaryKey
    val id:Int = 0,
    val text:String = "",
    val author:String = "",
    val type:Int = 0 // 0 for recent & 1 for featured
)
