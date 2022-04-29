package com.bsuir.productlist.api

import com.bsuir.productlist.model.CurrencyRate
import retrofit2.http.GET

interface CurrencyApi {

    @GET("/api/exrates/rates/431")
    suspend fun getUsdRate(): CurrencyRate

}