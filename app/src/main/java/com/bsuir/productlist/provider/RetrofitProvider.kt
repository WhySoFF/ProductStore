package com.bsuir.productlist.provider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val STORE_BASE = "https://fakestoreapi.com"
    private const val CURRENCY_BASE = " https://www.nbrb.by"

    fun getStoreRetrofit(httpClient: OkHttpClient = OkHttpClient()) =
        httpClient.defaultRetrofit().baseUrl(STORE_BASE).build()

    fun getCurrencyRetrofit(httpClient: OkHttpClient = OkHttpClient()) =
        httpClient.defaultRetrofit().baseUrl(CURRENCY_BASE).build()

    private fun OkHttpClient.defaultRetrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(this)
}