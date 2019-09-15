package com.example.quoteshub.models

data class Response(
    var results : List<Quote>
)

data class Quote(
    var id : Int,
    var title : String,
    var source : Source,
    var featured : Boolean,
    var likes : Int,
    var tags : List<Tag>
)

data class Source(
    var id : Int,
    var name : String
)

data class Tag(
    var id : Int,
    var name : String
)