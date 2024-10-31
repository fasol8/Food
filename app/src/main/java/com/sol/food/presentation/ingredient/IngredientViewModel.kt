package com.sol.food.presentation.ingredient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.repository.IngredientRepository
import com.sol.food.domain.model.ingredient.IngredientResponse
import com.sol.food.domain.model.ingredient.ResultSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(private val repository: IngredientRepository) :
    ViewModel() {

    private val _informationIngredient = MutableLiveData<IngredientResponse>()
    val informationIngredient: LiveData<IngredientResponse> = _informationIngredient

    private val _searchIngredient = MutableLiveData<List<ResultSearch>>()
    val searchIngredient: LiveData<List<ResultSearch>> = _searchIngredient

    fun getInformationIngredient(idIngredient: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getInformationIngredient(idIngredient)
                _informationIngredient.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getSearchIngredient(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchIngredient(query)
                _searchIngredient.value = response.results
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun saveItem(ingredient: IngredientResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val newIngredient = ItemEntity(
                id = ingredient.id ?: 0,
                name = ingredient.name ?: "",
                image = "https://spoonacular.com/cdn/ingredients_250x250/${ingredient.image ?: ""}",
                type = "Ingredient"
            )
            repository.saveIngredient(newIngredient)
        }
    }

    fun isIngredientSaved(ingredient: IngredientResponse, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = repository.isItemSaved(ingredient.id)
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