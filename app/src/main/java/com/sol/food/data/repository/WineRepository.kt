package com.sol.food.data.repository

import com.sol.food.data.network.WineApi
import com.sol.food.domain.model.wine.WineResponse
import javax.inject.Inject

class WineRepository @Inject constructor(private val api: WineApi) {

    suspend fun getRecommendationWine(wine: String): WineResponse {
        return api.getRecommendationWine(wine)
    }
}