package com.sol.food.data.repository

import com.sol.food.data.network.MiscApi
import com.sol.food.domain.model.misc.AnalysisImageResponse
import com.sol.food.domain.model.misc.ClassifyImageResponse
import com.sol.food.domain.model.misc.MiscResponse
import javax.inject.Inject

class MiscRepository @Inject constructor(private val api: MiscApi) {

    suspend fun getRandomJoke(): MiscResponse {
        return api.getRandomJoke()
    }

    suspend fun getRandomTrivia(): MiscResponse {
        return api.getRandomTrivia()
    }

    suspend fun getClassifyImage(imageUrl: String): ClassifyImageResponse {
        return api.getClassifyImage(imageUrl)
    }

    suspend fun getAnalysisImage(imageUrl: String): AnalysisImageResponse {
        return api.getAnalysisImage(imageUrl)
    }
}