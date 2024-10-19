package com.sol.food.presentation.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.RecipeRepository
import com.sol.food.domain.model.recipe.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _randomRecipe = MutableLiveData<RecipeDetail>()
    val randomRecipe: LiveData<RecipeDetail> = _randomRecipe

    init {
        getRandomRecipe()
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
}