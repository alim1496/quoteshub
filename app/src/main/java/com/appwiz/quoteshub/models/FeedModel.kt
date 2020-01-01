package com.appwiz.quoteshub.models

data class FeedModel (
    var FeaturedQuotes: SingleFeedQuote,
    var RecentQuotes: SingleFeedQuote,
    var FeaturedAuthors: SingleFeedAuthor,
    var DayQuote: SingleQuote,
    var EventsToday: EventList
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

data class EventList (
    var type: String,
    var title: String,
    var layout: String,
    var data: List<Event>
)

data class Event (
    var id: Int,
    var text: String,
    var type: Int
)
