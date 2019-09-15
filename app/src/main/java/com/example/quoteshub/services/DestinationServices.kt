package com.example.quoteshub.services

import com.example.quoteshub.models.CategoryModel
import com.example.quoteshub.models.Response
import retrofit2.Call
import retrofit2.http.GET

interface DestinationServices {

    @GET("quotes/v1/quotes/feed/")
    fun getFeed(): Call<Response>

    @GET("quotes/v1/categories/team/")
    fun getCategories(): Call<CategoryModel>
}