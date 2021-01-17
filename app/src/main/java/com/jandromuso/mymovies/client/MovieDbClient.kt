package com.jandromuso.mymovies.client

import com.jandromuso.mymovies.service.TheMovieDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDbClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(TheMovieDbService::class.java)
}