package com.appwiz.quoteshub.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CategoryModel(
    var results : List<Category>
)

@Entity
data class Category(
    @PrimaryKey
    var id : Int,
    var name : String,
    var quotes : Int
)