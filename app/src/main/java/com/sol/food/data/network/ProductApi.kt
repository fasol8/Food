package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("food/products/{id}")
    suspend fun getInformationProduct(
        @Path("id") idProduct: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): ProductResponse
}