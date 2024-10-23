package com.sol.food.presentation.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.repository.ProductRepository
import com.sol.food.domain.model.product.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _informationProduct = MutableLiveData<ProductResponse>()
    val informationProduct: LiveData<ProductResponse> = _informationProduct

    init {
        getInformationProduct()
    }

    fun getInformationProduct(idProduct: Int = 22347) {
        viewModelScope.launch {
            try {
                val response = repository.getInformationProduct(idProduct)
                _informationProduct.value = response
                Log.i("info badges", _informationProduct.value!!.importantBadges.toString())
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }
}