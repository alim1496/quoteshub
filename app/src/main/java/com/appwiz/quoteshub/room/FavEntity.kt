package com.appwiz.quoteshub.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavEntity (
    @PrimaryKey
    val id: Int = 0,
    val text: String = "",
    val src: String = ""
)
