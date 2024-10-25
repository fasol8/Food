package com.sol.food.presentation.misc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.MiscRepository
import com.sol.food.domain.model.misc.MiscResponse
import com.sol.food.domain.model.misc.MiscType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiscViewModel @Inject constructor(private val repository: MiscRepository) : ViewModel() {

    private val _randomMisc = MutableLiveData<MiscResponse>()
    val randomMisc: LiveData<MiscResponse> = _randomMisc

    fun getRandomMisc(miscType: MiscType) {
        when (miscType) {
            MiscType.Joke -> getRandomJoke()
            MiscType.Trivia -> getRandomTrivia()
        }
    }

    private fun getRandomJoke() {
        viewModelScope.launch {
            try {
//                _randomMisc.value = "" //Limpiar randomMisc
                val response = repository.getRandomJoke()
                _randomMisc.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    private fun getRandomTrivia() {
        viewModelScope.launch {
            try {
//                _randomMisc.value = "" //Limpiar randomMisc
                val response = repository.getRandomTrivia()
                _randomMisc.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

}