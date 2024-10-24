package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.wine.WinePairResponse
import com.sol.food.domain.model.wine.WineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WineApi {

    @GET("food/wine/recommendation")
    suspend fun getRecommendationWine(
        @Query("wine") wine: String,
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): WineResponse

    @GET("food/wine/pairing")
    suspend fun getPairingWine(
        @Query("food") food: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): WinePairResponse
}