package com.bsuir.productlist.db

import android.content.Context
import androidx.room.Room

object ModelDb {

    fun getInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "db").build()

}