package com.bsuir.productlist.util

object CurrencyFormatUtil {

    enum class Currency {
        BYN,
        USD
    }

    var selectedCurrency = Currency.USD
    var usdRate = 1f

    fun getFormattedCurrency(value: String) =
        when (selectedCurrency) {
            Currency.BYN -> "${(value.toFloat() * usdRate).round()} BYN"
            Currency.USD -> "$value \$"
        }

    private fun Float.round(decimals: Int = 2) =
        "%.${decimals}f".format(this).toFloat()

}