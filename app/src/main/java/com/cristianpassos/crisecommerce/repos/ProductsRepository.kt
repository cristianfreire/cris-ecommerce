package com.cristianpassos.crisecommerce.repos

import com.cristianpassos.crisecommerce.model.Product
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ProductsRepository {

    private fun retrofit(): EcommerceApi {
        return Retrofit.Builder()
            .baseUrl("https://finepointmobile.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(EcommerceApi::class.java)
    }

    suspend fun fetchAllProductsRetrofit(): List<Product> {
        return retrofit().fetchAllProducts()
    }
    suspend fun fetchProduct(productTitle: String): Product{
        return fetchAllProductsRetrofit().first { it.title == productTitle }
    }

    suspend fun searchForProducts(term: String): List<Product> {
        return fetchAllProductsRetrofit().filter { it.title.contains(term, true) }
    }

}