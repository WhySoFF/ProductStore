package com.bsuir.productlist.util

import com.bsuir.productlist.provider.ApiProvider
import com.bsuir.productlist.provider.RetrofitProvider

fun provideStoreApi() = ApiProvider.provideStoreApi(RetrofitProvider.getStoreRetrofit())

fun provideCurrencyApi() = ApiProvider.provideCurrencyApi(RetrofitProvider.getCurrencyRetrofit())