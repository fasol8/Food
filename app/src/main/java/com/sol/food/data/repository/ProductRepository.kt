package com.sol.food.data.repository

import com.sol.food.data.network.ProductApi
import com.sol.food.domain.model.product.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductApi) {

    suspend fun getInformationProduct(idProduct: Int): ProductResponse {
        return api.getInformationProduct(idProduct)
    }
}