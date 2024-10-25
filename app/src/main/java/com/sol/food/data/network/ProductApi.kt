package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.ingredient.IngredientSearchResponse
import com.sol.food.domain.model.product.ProductResponse
import com.sol.food.domain.model.product.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("food/products/{id}")
    suspend fun getInformationProduct(
        @Path("id") idProduct: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): ProductResponse

    @GET("food/products/search")
    suspend fun getSearchProduct(
        @Query("query") query: String,
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): ProductSearchResponse
}