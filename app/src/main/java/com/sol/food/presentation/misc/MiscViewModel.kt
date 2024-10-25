package com.sol.food.presentation.misc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.MiscRepository
import com.sol.food.domain.model.misc.MiscResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiscViewModel @Inject constructor(private val repository: MiscRepository) : ViewModel() {

    private val _randomJoke = MutableLiveData<MiscResponse>()
    val randomJoke: LiveData<MiscResponse> = _randomJoke

    fun getRandomJoke() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomJoke()
                _randomJoke.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

}