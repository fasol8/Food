package com.sol.food.presentation.ingredient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.IngredientRepository
import com.sol.food.domain.model.ingredient.IngredientResponse
import com.sol.food.domain.model.ingredient.ResultSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(private val repository: IngredientRepository) :
    ViewModel() {

    private val _informationIngredient = MutableLiveData<IngredientResponse>()
    val informationIngredient: LiveData<IngredientResponse> = _informationIngredient

    private val _searchIngredient = MutableLiveData<List<ResultSearch>>()
    val searchIngredient: LiveData<List<ResultSearch>> = _searchIngredient

    init {
        getInformationIngredient()
    }

    fun getInformationIngredient(idIngredient: Int = 9266) {
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
}