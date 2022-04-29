package com.bsuir.productlist.preferences

import android.content.SharedPreferences

class CurrencyPreferences(private val preferences: SharedPreferences): ICurrencyPreferences {

    companion object {
        private const val USD_PREF_KEY = "USD_PREF_KEY"
        const val USD_DEF_VALUE = -1f
    }

    override fun saveUsdRate(rate: Double) {
        preferences.edit().putFloat(USD_PREF_KEY, rate.toFloat()).apply()
    }

    override fun getUsdRate(): Double {
        return preferences.getFloat(USD_PREF_KEY, USD_DEF_VALUE).toDouble()
    }

}

interface ICurrencyPreferences {

    fun saveUsdRate(rate: Double)

    fun getUsdRate(): Double

}