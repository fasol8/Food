package com.sol.food.domain.model.product

data class ProductSearchResponse(
    val number: Int,
    val offset: Int,
    val products: List<ProductSearch>,
    val totalProducts: Int,
    val type: String
)