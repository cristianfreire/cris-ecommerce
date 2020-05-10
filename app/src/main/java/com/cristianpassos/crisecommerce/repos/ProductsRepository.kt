package com.cristianpassos.crisecommerce.repos

import com.cristianpassos.crisecommerce.model.Product
import com.google.gson.Gson
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import java.net.URL

class ProductsRepository {
    fun getAllProducts(): @NonNull Single<List<Product>>? {
        return Single.create<List<Product>>{
           val json =  URL("https://finepointmobile.com/data/products.json").readText()
            val products = Gson().fromJson(json, Array<Product>::class.java).toList()
            it.onSuccess(products)
        }
    }

}