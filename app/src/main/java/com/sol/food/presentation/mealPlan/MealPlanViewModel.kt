package com.sol.food.presentation.mealPlan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.MealPlanRepository
import com.sol.food.domain.model.mealPlan.Meal
import com.sol.food.domain.model.mealPlan.MealGenerateResponse
import com.sol.food.domain.model.mealPlan.Week
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(private val repository: MealPlanRepository) :
    ViewModel() {

    private val _generateMeal = MutableLiveData<List<Meal>>(emptyList())
    val generateMeal: LiveData<List<Meal>> = _generateMeal

    private val _generateMealWeek = MutableLiveData<Week>()
    val generateMealWeek: LiveData<Week> = _generateMealWeek

    fun getGenerateMealDay() {
        viewModelScope.launch {
            try {
                val response = repository.getGenerateMealPlan()
                _generateMeal.value = response.meals
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getGenerateMealWeek() {
        viewModelScope.launch {
            try {
                val response = repository.getGenerateMealPlanWeek()
                _generateMealWeek.value = response.week
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}