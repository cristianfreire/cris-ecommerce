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

    private fun retrofit() : EcommerceApi {
        return Retrofit.Builder()
            .baseUrl("https://finepointmobile.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(EcommerceApi::class.java)
    }

    suspend fun fetchAllProductsRetrofit() : List<Product>{
        return retrofit().fetchAllProducts()
    }

    fun getAllProducts(): @NonNull Single<List<Product>>? {
        return Single.create<List<Product>> {
            it.onSuccess(fetchProducts())
        }
    }

    fun searchForProducts(term: String): Single<List<Product>> {
        return Single.create<List<Product>> {
            val filteredProducts = fetchProducts().filter { it.title.contains(term, true) }
            it.onSuccess(filteredProducts)
        }
    }

    fun getProductByName(name: String): Single<Product> {
        return Single.create<Product> {
            val product = fetchProducts().first { it.title == name }
            it.onSuccess(product)
        }
    }

    fun fetchProducts(): List<Product> {
        val json = URL("https://finepointmobile.com/data/products.json").readText()
        return Gson().fromJson(json, Array<Product>::class.java).toList()

    }

}