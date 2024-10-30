package com.sol.food.presentation.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sol.food.data.local.ItemEntity
import com.sol.food.data.repository.ProductRepository
import com.sol.food.domain.model.ingredient.IngredientResponse
import com.sol.food.domain.model.product.ProductResponse
import com.sol.food.domain.model.product.ProductSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _informationProduct = MutableLiveData<ProductResponse>()
    val informationProduct: LiveData<ProductResponse> = _informationProduct

    private val _searchProduct = MutableLiveData<List<ProductSearch>>()
    val searchProduct: LiveData<List<ProductSearch>> = _searchProduct

    fun getInformationProduct(idProduct: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getInformationProduct(idProduct)
                _informationProduct.value = response
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun getSearchProduct(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchProduct(query)
                _searchProduct.value = response.products
            } catch (e: Exception) {
                Log.i("Error", e.message.toString())
            }
        }
    }

    fun saveItem(product: ProductResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val newProduct = ItemEntity(
                id = product.id ?: 0,
                name = product.title ?: "",
                image = product.id.toString()?: "",
                type = "Product"
            )
            repository.saveProduct(newProduct)
        }
    }

    fun isProductSaved(product: ProductResponse, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = repository.isItemSaved(product.id)
            withContext(Dispatchers.Main) {
                callback(isSaved)
            }
        }
    }

    fun deleteItemById(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemById(itemId)
        }
    }
}