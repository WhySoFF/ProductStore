package com.bsuir.productlist.provider

import com.bsuir.productlist.api.CurrencyApi
import com.bsuir.productlist.api.StoreSearchApi
import retrofit2.Retrofit

object ApiProvider {

    fun provideStoreApi(retrofit: Retrofit): StoreSearchApi =
        retrofit.create(StoreSearchApi::class.java)

    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

}