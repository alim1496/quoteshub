package com.example.quoteshub.models

data class TinyResponse(
    var results: List<TinyQuote>
)

data class TinyQuote(
    var id : Int,
    var title : String,
    var source: Source
)