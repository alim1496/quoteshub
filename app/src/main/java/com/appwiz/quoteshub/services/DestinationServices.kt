package com.appwiz.quoteshub.services

import com.appwiz.quoteshub.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DestinationServices {

    @GET("quotes/v2/home/")
    fun getFeed(
        @Query("featured") featured: Int,
        @Query("page") page: Int
    ): Call<List<Quote>>

    @GET("quotes/v2/categories/")
    fun getCategories(): Call<List<Category>>

    @GET("quotes/v3/category/{id}/")
    fun getCategoryQuotes(
        @Path("id") id : Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<List<Quote>>

    @GET("quotes/v2/sources/")
    fun getAuthors(
        @Query("page") page: Int
    ): Call<List<Author>>

    @GET("quotes/v2/source/{id}/")
    fun getAuthorDetails(
        @Path("id") id: Int
    ): Call<AuthorDetails>

    @GET("quotes/v2/source/{id}/quotes/")
    fun getMoreQuotes(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Call<List<Quote>>

    @GET("quotes/v3/home/")
    fun getFeedLatest(
        @Query("featured") featured: Boolean,
        @Query("with_topics") with_topics: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : Call<LatestFeed>
}
