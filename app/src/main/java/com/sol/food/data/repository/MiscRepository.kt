package com.sol.food.data.repository

import com.sol.food.data.network.MiscApi
import com.sol.food.domain.model.misc.MiscResponse
import javax.inject.Inject

class MiscRepository @Inject constructor(private val api: MiscApi) {

    suspend fun getRandomJoke(): MiscResponse {
        return api.getRandomJoke()
    }

    suspend fun getRandomTrivia(): MiscResponse {
        return api.getRandomTrivia()
    }
}