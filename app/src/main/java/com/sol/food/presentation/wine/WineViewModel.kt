package com.sol.food.presentation.wine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.WineRepository
import com.sol.food.domain.model.wine.WinePairResponse
import com.sol.food.domain.model.wine.WineResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineViewModel @Inject constructor(private val repository: WineRepository) : ViewModel() {

    private val _recommendationWine = MutableLiveData<WineResponse>()
    val recommendationWine: LiveData<WineResponse> = _recommendationWine

    private val _pairWine = MutableLiveData<WinePairResponse>()
    val pairWine: LiveData<WinePairResponse> = _pairWine

    fun getRecommendationWine(wine: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRecommendationWine(wine)
                _recommendationWine.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getPairWine(food: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPairWine(food)
                _pairWine.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}