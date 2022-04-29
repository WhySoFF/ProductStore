package com.bsuir.productlist.repository

import com.bsuir.productlist.api.StoreSearchApi
import com.bsuir.productlist.db.AppDatabase
import com.bsuir.productlist.model.Product
import com.bsuir.productlist.model.ResultWrapper
import com.bsuir.productlist.util.onSuccess
import com.bsuir.productlist.util.safeApiCall

class StoreRepository(private val api: StoreSearchApi, private val database: AppDatabase) :
    IStoreRepository {

    override suspend fun getProductList() =
        when (val fetchResult = fetchProductList()) {
            is ResultWrapper.Error.Network -> {
                val localProducts = database.productDao.getProducts()
                when {
                    localProducts.isNotEmpty() -> localProducts to DataSource.LOCAL
                    else -> localProducts to DataSource.EMPTY
                }
            }
            is ResultWrapper.Success -> fetchResult.value to DataSource.REMOTE
            else -> emptyList<Product>() to DataSource.EMPTY
        }

    private suspend fun fetchProductList() =
        safeApiCall {
            api.getProductList()
        }.onSuccess {
            database.productDao.deleteAllProducts()
            database.productDao.saveProducts(it.value)
        }

}

interface IStoreRepository {
    suspend fun getProductList(): Pair<List<Product>, DataSource>
}

enum class DataSource() {
    REMOTE,
    LOCAL,
    EMPTY
}