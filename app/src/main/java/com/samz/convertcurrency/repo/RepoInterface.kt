package com.samz.convertcurrency.repo

import com.samz.convertcurrency.model.ConvertCurrencyResponse
import com.samz.convertcurrency.model.ConvertedCurrencies
import com.samz.convertcurrency.model.CurrenciesRatesResponse
import com.samz.convertcurrency.model.SymbolResponse
import com.samz.convertcurrency.model.generalResponse.ResourcesLiveData

interface RepoInterface {
    suspend fun getCurrencySymbols(): SymbolResponse?

    suspend fun convertCurrencyAmount(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ): ConvertCurrencyResponse?

    suspend fun convertAmountToPopularCurrencies(baseCurrency: String): CurrenciesRatesResponse?

    fun newCurrenciesConversion(convertedCurrencies: ConvertedCurrencies): ResourcesLiveData<Unit>

    fun fetchConversionHistory(
        fromDate: String,
        toDate: String
    ): ResourcesLiveData<List<ConvertedCurrencies>>

    fun deleteOldConversions(oldDate: String): ResourcesLiveData<Unit>
}