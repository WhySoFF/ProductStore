package com.bsuir.productlist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "category") @SerializedName("category") val category: String,
    @ColumnInfo(name = "description") @SerializedName("description") val description: String,
    @ColumnInfo(name = "image") @SerializedName("image") val image: String,
    @ColumnInfo(name = "price") @SerializedName("price") val price: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String
): Parcelable