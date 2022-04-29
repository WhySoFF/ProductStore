package com.bsuir.productlist.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
data class CurrencyRate(
    @SerializedName("Cur_Abbreviation") val abbreviation: String,
    @SerializedName("Cur_OfficialRate") val rateToByn: Double,
    @SerializedName("Date") val date: String
)