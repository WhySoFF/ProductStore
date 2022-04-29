package com.bsuir.productlist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bsuir.productlist.dao.ProductDao
import com.bsuir.productlist.model.Product

@Database(version = 1, entities = [Product::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
}