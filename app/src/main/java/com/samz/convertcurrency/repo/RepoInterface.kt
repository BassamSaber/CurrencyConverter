package com.samz.convertcurrency.repo

import com.samz.convertcurrency.model.ConvertCurrencyResponse
import com.samz.convertcurrency.model.ConvertedCurrencies
import com.samz.convertcurrency.model.CurrenciesRatesResponse
import com.samz.convertcurrency.model.SymbolResponse

interface RepoInterface {
    suspend fun getCurrencySymbols(): SymbolResponse?

    suspend fun convertCurrencyAmount(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ): ConvertCurrencyResponse?

    suspend fun convertAmountToPopularCurrencies(baseCurrency: String): CurrenciesRatesResponse?

    suspend fun newCurrenciesConversion(convertedCurrencies: ConvertedCurrencies): Unit

    suspend fun fetchConversionHistory(
        fromDate: String,
        toDate: String
    ): List<ConvertedCurrencies>

    suspend fun deleteOldConversions(oldDate: String): Unit
}