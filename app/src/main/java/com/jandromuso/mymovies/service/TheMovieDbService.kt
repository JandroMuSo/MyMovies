package com.jandromuso.mymovies.service

import com.jandromuso.mymovies.model.MovieDbResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("movie/popular")
    suspend fun listPopularMovies(@Query("api_key") apiKey:String): MovieDbResult
}