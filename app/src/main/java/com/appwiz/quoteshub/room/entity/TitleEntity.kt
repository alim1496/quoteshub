package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TitleEntity (
    @PrimaryKey
    val id: Int = 0,
    val type: String = "",
    val title: String = "",
    val more: Boolean = false
)
