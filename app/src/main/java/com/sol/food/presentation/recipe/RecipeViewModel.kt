package com.sol.food.presentation.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.repository.RecipeRepository
import com.sol.food.domain.model.ingredient.IngredientResponse
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeRandomInfo
import com.sol.food.domain.model.recipe.RecipeInformation
import com.sol.food.domain.model.recipe.ResultSearch
import com.sol.food.domain.model.recipe.SimilarResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _randomRecipe = MutableLiveData<RecipeRandomInfo>()
    val randomRecipe: LiveData<RecipeRandomInfo> = _randomRecipe

    private val _similarRecipe = MutableLiveData<List<SimilarResponseItem>>()
    val similarRecipe: LiveData<List<SimilarResponseItem>> = _similarRecipe

    private val _nutrientRecipe = MutableLiveData<NutrientResponse>()
    val nutrientRecipe: LiveData<NutrientResponse> = _nutrientRecipe

    private val _searchRecipe = MutableLiveData<List<ResultSearch>>()
    val searchRecipe: LiveData<List<ResultSearch>> = _searchRecipe

    private val _informationRecipe = MutableLiveData<RecipeInformation>()
    val informationRecipe: LiveData<RecipeInformation> = _informationRecipe

    fun loadData(idRecipe: Int) {
        getSimilarRecipe(idRecipe)
        getNutrientRecipe(idRecipe)
    }

    fun getRandomRecipe() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomRecipe()
                _randomRecipe.value = response.recipes[0]
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    private fun getSimilarRecipe(idRecipe: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getSimilarRecipe(idRecipe)
                _similarRecipe.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    private fun getNutrientRecipe(idRecipe: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getNutrientRecipe(idRecipe)
                _nutrientRecipe.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getSearchRecipe(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchRecipe(query)
                _searchRecipe.value = response.results
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }


    fun getInformationRecipe(idRecipe: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getInformationRecipe(idRecipe)
                _informationRecipe.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun saveItem(recipe: RecipeInformation) {
        viewModelScope.launch(Dispatchers.IO) {
            val newRecipe = ItemEntity(
                id = recipe.id ?: 0,
                name = recipe.title ?: "",
                image = recipe.image ?: "",
                type = "Recipe"
            )
            repository.saveRecipe(newRecipe)
        }
    }

    fun saveItemRandom(recipe: RecipeRandomInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val newRecipe = ItemEntity(
                id = recipe.id ?: 0,
                name = recipe.title ?: "",
                image = recipe.image ?: "",
                type = "Recipe"
            )
            repository.saveRecipe(newRecipe)
        }
    }

    fun isRecipeSaved(recipe: RecipeInformation, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = repository.isItemSaved(recipe.id)
            withContext(Dispatchers.Main) {
                callback(isSaved)
            }
        }
    }

    fun isRecipeSavedRandom(recipe: RecipeRandomInfo, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = repository.isItemSaved(recipe.id)
            withContext(Dispatchers.Main) {
                callback(isSaved)
            }
        }
    }

    fun deleteItemById(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemById(itemId)
        }
    }
}