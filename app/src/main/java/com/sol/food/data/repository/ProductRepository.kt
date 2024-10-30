package com.sol.food.data.repository

import com.sol.food.data.local.ItemDao
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.network.ProductApi
import com.sol.food.domain.model.product.ProductResponse
import com.sol.food.domain.model.product.ProductSearchResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApi,
    private val itemDao: ItemDao
) {

    suspend fun getInformationProduct(idProduct: Int): ProductResponse {
        return api.getInformationProduct(idProduct)
    }

    suspend fun getSearchProduct(query: String): ProductSearchResponse {
        return api.getSearchProduct(query)
    }

    suspend fun saveProduct(item: ItemEntity) {
        itemDao.insertItem(item)
    }

    suspend fun isItemSaved(itemId: Int): Boolean {
        return itemDao.isItemSaved(itemId)
    }

    suspend fun deleteItemById(itemId: Int) {
        val item = itemDao.getItemById(itemId)
        if (item != null) {
            itemDao.deleteItem(item)
        }
    }
}