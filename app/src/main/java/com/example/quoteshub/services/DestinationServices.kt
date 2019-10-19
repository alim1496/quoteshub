package com.example.quoteshub.services

import com.example.quoteshub.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DestinationServices {

    @GET("quotes/v1/quotes/feed/")
    fun getFeed(): Call<FeedModel>

    @GET("quotes/v1/categories/team/")
    fun getCategories(): Call<CategoryModel>

    @GET("quotes/v1/categories/{id}/quotes/")
    fun getCategoryQuotes(
        @Path("id") id : Int,
        @Query("page") page: Int
    ): Call<Response>

    @GET("quotes/v1/sources/team/")
    fun getAuthors(): Call<AuthorModel>

    @GET("quotes/v1/sources/{id}/details/")
    fun getAuthorDetails(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Call<AuthorDetails>

}