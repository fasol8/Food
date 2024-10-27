package com.sol.food.presentation.misc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.MiscRepository
import com.sol.food.domain.model.misc.AnalysisImageResponse
import com.sol.food.domain.model.misc.ClassifyImageResponse
import com.sol.food.domain.model.misc.MiscResponse
import com.sol.food.domain.model.misc.MiscType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiscViewModel @Inject constructor(private val repository: MiscRepository) : ViewModel() {

    private val _randomMisc = MutableLiveData<MiscResponse>()
    val randomMisc: LiveData<MiscResponse> = _randomMisc

    private val _classifyImage = MutableLiveData<ClassifyImageResponse>()
    val classifyImage: LiveData<ClassifyImageResponse> = _classifyImage

    private val _analysisImage = MutableLiveData<AnalysisImageResponse>()
    val analysisImage: LiveData<AnalysisImageResponse> = _analysisImage

    fun getRandomMisc(miscType: MiscType) {
        when (miscType) {
            MiscType.Joke -> getRandomJoke()
            MiscType.Trivia -> getRandomTrivia()
        }
    }

    private fun getRandomJoke() {
        viewModelScope.launch {
            try {
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
                val response = repository.getRandomTrivia()
                _randomMisc.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getClassifyImage(imageURl: String) {
        viewModelScope.launch {
            try {
                val response = repository.getClassifyImage(imageURl)
                _classifyImage.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getAnalysisImage(imageURl: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAnalysisImage(imageURl)
                _analysisImage.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}