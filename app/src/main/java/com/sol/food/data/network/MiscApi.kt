package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.misc.ClassifyImageResponse
import com.sol.food.domain.model.misc.MiscResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MiscApi {

    @GET("food/jokes/random")
    suspend fun getRandomJoke(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): MiscResponse

    @GET("food/trivia/random")
    suspend fun getRandomTrivia(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): MiscResponse

    @GET("food/images/classify")
    suspend fun getClassifyImage(
        @Query("imageUrl") imageUrl: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): ClassifyImageResponse
}