package com.appwiz.quoteshub.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeQuote (
    @PrimaryKey
    var id: Int,
    var title: String,
    var source: String,
    var featured: Boolean,
    var background_image: String
)