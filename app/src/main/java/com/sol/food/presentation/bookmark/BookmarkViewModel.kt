package com.sol.food.presentation.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: BookmarkRepository) :
    ViewModel() {
    private val _items = MutableLiveData<List<ItemEntity>>()
    val items: LiveData<List<ItemEntity>> = _items

    suspend fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getAllItems()
                _items.postValue(response)
            } catch (e: Exception) {
                Log.i("Error DB", e.message.toString())
            }
        }
    }
}