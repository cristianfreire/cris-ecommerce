package com.cristianpassos.crisecommerce.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianpassos.crisecommerce.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import com.cristianpassos.crisecommerce.repos.ProductsRepository
import java.net.URL

class ProductDetailsViewModel : ViewModel() {

    val productDetails = MutableLiveData<Product>()

    fun fetchProductDetails(productTitle: String) {
        viewModelScope.launch(Dispatchers.Default) {
              productDetails.postValue(ProductsRepository().fetchProduct(productTitle))

        }
    }

}