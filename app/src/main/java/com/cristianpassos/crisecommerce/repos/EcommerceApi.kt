package com.cristianpassos.crisecommerce.repos

import com.cristianpassos.crisecommerce.model.Product
import retrofit2.http.GET

interface EcommerceApi {

    @GET("api/ecommerce/v1/allProducts")
    suspend fun fetchAllProducts(): List<Product>
}