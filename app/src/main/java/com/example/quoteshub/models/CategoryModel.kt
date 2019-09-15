package com.example.quoteshub.models

data class CategoryModel(
    var results : List<Category>
)

data class Category(
    var id : Int,
    var name : String,
    var quotes : Int
)