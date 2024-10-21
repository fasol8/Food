package com.sol.food.presentation.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.RecipeRepository
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeDetail
import com.sol.food.domain.model.recipe.SimilarResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _randomRecipe = MutableLiveData<RecipeDetail>()
    val randomRecipe: LiveData<RecipeDetail> = _randomRecipe

    private val _similarRecipe = MutableLiveData<List<SimilarResponseItem>>()
    val similarRecipe: LiveData<List<SimilarResponseItem>> = _similarRecipe

    private val _nutrientRecipe = MutableLiveData<NutrientResponse>()
    val nutrientRecipe: LiveData<NutrientResponse> = _nutrientRecipe

    init {
        getRandomRecipe()
    }

    fun loadData(idRecipe: Int) {
        getSimilarRecipe(idRecipe)
        getNutrientRecipe(idRecipe)
    }

    private fun getRandomRecipe() {
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
}