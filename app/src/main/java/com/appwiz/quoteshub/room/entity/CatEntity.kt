package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatEntity(
    @PrimaryKey
    val id:Int = 0,
    var name : String = "",
    var quotes : Int = 0
)