package com.cristianpassos.crisecommerce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianpassos.crisecommerce.model.Product
import com.cristianpassos.crisecommerce.repos.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {

    val products = MutableLiveData<List<Product>>()

    fun setup() {
        viewModelScope.launch(Dispatchers.Default) {
            products.postValue(ProductsRepository().fetchAllProductsRetrofit())
        }
    }
}