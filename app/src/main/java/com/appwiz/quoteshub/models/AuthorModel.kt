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

data class SingleAuthor (
    var id: Int,
    var name: String,
    var image: String,
    var featured: Boolean,
    var shortDesc: String
)

data class AuthorDetails (
    var source: SingleAuthor,
    var quotes: AuthorQuotes
)

data class AuthorQuotes (
    var count: Int,
    var results: List<Quote>
)