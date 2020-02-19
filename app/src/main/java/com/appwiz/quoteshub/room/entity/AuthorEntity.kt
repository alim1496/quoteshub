package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthorEntity (
    @PrimaryKey
    val id:Int = 0,
    val name:String = "",
    val quotes:Int = 0,
    val image:String = ""
)