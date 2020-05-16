package com.appwiz.quoteshub.models

data class AuthorModel (
    var results: List<Author>
)

data class Author (
    var id: Int,
    var name: String,
    var image: String,
    var quotes: Int
)

data class AuthorQuote (
    var id: Int,
    var title: String,
    var has_background: Boolean,
    var background_image: String,
    var narrator: String
)

data class TinyAuthor (
    var cover: String,
    var shortDesc: String
)

data class AuthorDetails (
    var source: TinyAuthor,
    var quotes: List<AuthorQuote>
)
