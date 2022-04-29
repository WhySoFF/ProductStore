package com.bsuir.productlist.api

import com.bsuir.productlist.model.Product
import retrofit2.http.GET

interface StoreSearchApi {

    @GET("/products")
    suspend fun getProductList(): List<Product>

}