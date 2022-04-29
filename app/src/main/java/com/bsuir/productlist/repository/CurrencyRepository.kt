package com.bsuir.productlist.repository

import com.bsuir.productlist.api.CurrencyApi
import com.bsuir.productlist.model.CurrencyRate
import com.bsuir.productlist.model.ResultWrapper
import com.bsuir.productlist.preferences.CurrencyPreferences
import com.bsuir.productlist.preferences.ICurrencyPreferences
import com.bsuir.productlist.util.onSuccess
import com.bsuir.productlist.util.safeApiCall

class CurrencyRepository(
    private val api: CurrencyApi,
    private val preferences: ICurrencyPreferences
) : ICurrencyRepository {

    override suspend fun getUsdRate() =
        when (val fetchResult = fetchUsdRate()) {
            is ResultWrapper.Error.Network -> {
                val prefRate = preferences.getUsdRate()
                when (prefRate) {
                    CurrencyPreferences.USD_DEF_VALUE.toDouble() -> prefRate to DataSource.EMPTY
                    else -> prefRate to DataSource.LOCAL
                }
            }
            is ResultWrapper.Success -> fetchResult.value.rateToByn to DataSource.REMOTE
            else -> CurrencyPreferences.USD_DEF_VALUE.toDouble() to DataSource.EMPTY
        }

    private suspend fun fetchUsdRate() =
        safeApiCall {
            api.getUsdRate()
        }.onSuccess {
            preferences.saveUsdRate(it.value.rateToByn)
        }

}

interface ICurrencyRepository {

    suspend fun getUsdRate(): Pair<Double, DataSource>

}