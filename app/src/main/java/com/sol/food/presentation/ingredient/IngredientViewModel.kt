package com.sol.food.presentation.ingredient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.IngredientRepository
import com.sol.food.domain.model.ingredient.IngredientResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(private val repository: IngredientRepository) :
    ViewModel() {

    private val _informationIngredient = MutableLiveData<IngredientResponse>()
    val informationIngredient: LiveData<IngredientResponse> = _informationIngredient

    init {
        getInformationIngredient()
    }

    fun getInformationIngredient(idIngredient: Int = 9266) {
        viewModelScope.launch {
            try {
                val response = repository.getInformationIngredient(idIngredient)
                _informationIngredient.value = response
                Log.i("Units", response.possibleUnits.toString())
                Log.i("Units _info", _informationIngredient.value!!.possibleUnits.toString())
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}