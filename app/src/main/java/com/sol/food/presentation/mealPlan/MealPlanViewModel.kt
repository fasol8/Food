package com.sol.food.presentation.mealPlan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.MealPlanRepository
import com.sol.food.domain.model.mealPlan.MealGenerateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(private val repository: MealPlanRepository) :
    ViewModel() {

    private val _generateMeal = MutableLiveData<MealGenerateResponse>()
    val generateMeal: LiveData<MealGenerateResponse> = _generateMeal

    init {
        getGenerateMeal()
    }

    private fun getGenerateMeal(time: String = "day") {
        viewModelScope.launch {
            try {
                val response = repository.getGenerateMealPlan(time)
                _generateMeal.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}