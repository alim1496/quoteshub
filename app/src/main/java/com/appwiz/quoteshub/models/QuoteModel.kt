package com.appwiz.quoteshub.models

data class LatestFeed(
    var quotes:List<Quote>,
    var categories:List<Category>
)

data class Quote(
    var id : Int,
    var title : String,
    var source : Source,
    var has_background: Boolean,
    var background_image: String,
    var narrator: String,
    var featured: Boolean
)

data class Source(
    var id : Int,
    var name : String
)