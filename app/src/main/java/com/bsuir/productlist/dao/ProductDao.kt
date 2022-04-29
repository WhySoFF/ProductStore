package com.bsuir.productlist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bsuir.productlist.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    suspend fun getProducts(): List<Product>

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()

    @Insert
    suspend fun saveProducts(productList: List<Product>)

}