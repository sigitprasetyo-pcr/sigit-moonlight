package com.example.sigit_moonlight.pertemuan_11.news

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("v1/cnn-news")
    fun getCnnNews(): Call<NewsResponse>
}