package com.appwiz.quoteshub.models

data class FeedModel (
    var FeaturedQuotes: SingleFeedQuote,
    var RecentQuotes: SingleFeedQuote,
    var FeaturedAuthors: SingleFeedAuthor,
    var DayQuote: SingleQuote
)

data class SingleFeedQuote (
    var type: String,
    var title: String,
    var layout: String,
    var data: List<Quote>
)

data class SingleQuote (
    var type: String,
    var title: String,
    var layout: String,
    var data: Quote
)

data class SingleFeedAuthor (
    var type: String,
    var title: String,
    var layout: String,
    var data: List<Author>
)