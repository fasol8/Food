package com.sol.food.data.repository

import com.sol.food.data.local.ItemDao
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.network.IngredientApi
import com.sol.food.domain.model.ingredient.IngredientResponse
import com.sol.food.domain.model.ingredient.IngredientSearchResponse
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val api: IngredientApi,
    private val itemDao: ItemDao
) {

    suspend fun getInformationIngredient(idIngredient: Int): IngredientResponse {
        return api.getInformationIngredient(idIngredient)
    }

    suspend fun getSearchIngredient(query: String): IngredientSearchResponse {
        return api.getSearchIngredient(query)
    }

    suspend fun saveIngredient(item: ItemEntity) {
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