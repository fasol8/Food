package com.sol.food.data.repository

import com.sol.food.data.local.ItemDao
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.network.RecipeApi
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeInformation
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import com.sol.food.domain.model.recipe.RecipeSearchResponse
import com.sol.food.domain.model.recipe.SimilarResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: RecipeApi,
    private val itemDao: ItemDao
) {

    suspend fun getRandomRecipe(): RecipeRandomResponse {
        return api.getRandomRecipe()
    }

    suspend fun getSimilarRecipe(idRecipe: Int): SimilarResponse {
        return api.getSimilarRecipe(idRecipe)
    }

    suspend fun getNutrientRecipe(idRecipe: Int): NutrientResponse {
        return api.getNutrientRecipe(idRecipe)
    }

    suspend fun getSearchRecipe(query: String): RecipeSearchResponse {
        return api.getSearchRecipe(query)
    }

    suspend fun getInformationRecipe(idRecipe: Int): RecipeInformation {
        return api.getInformationRecipe(idRecipe)
    }

    suspend fun saveRecipe(item: ItemEntity) {
        itemDao.insertItem(item)
    }

    suspend fun isItemSaved(itemId: Int):Boolean{
        return itemDao.isItemSaved(itemId)
    }

    suspend fun deleteItemById(itemId: Int) {
        val item = itemDao.getItemById(itemId)
        if (item != null) {
            itemDao.deleteItem(item)
        }
    }
}