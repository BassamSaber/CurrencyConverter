package com.samz.convertcurrency.repo

import androidx.lifecycle.MutableLiveData
import com.samz.convertcurrency.model.*
import com.samz.convertcurrency.utils.ApiException
import java.text.SimpleDateFormat
import java.util.*

class FakeRepo : RepoInterface {

    private val conversionHistoryList = mutableListOf<ConvertedCurrencies>()
    private val observableConversionHistory =
        MutableLiveData<List<ConvertedCurrencies>>(conversionHistoryList)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getCurrencySymbols(): SymbolResponse? {
        if (shouldReturnNetworkError)
            throw ApiException(500, "Network Error")
        return SymbolResponse(FakeData.symbols).apply {
            success = true
            errorResponse = null
        }
    }

    override suspend fun convertCurrencyAmount(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ): ConvertCurrencyResponse? {
        if (shouldReturnNetworkError)
            throw ApiException(500, "Network Error")

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val rate = 1.02
        return ConvertCurrencyResponse(
            sdf.format(Calendar.getInstance().time),
            ConvertInfo(rate, Calendar.getInstance().timeInMillis),
            ConvertQuery(amount, fromCurrency, toCurrency),
            result = rate * amount
        ).apply {
            success = true
            errorResponse = null
        }
    }

    override suspend fun convertAmountToPopularCurrencies(baseCurrency: String): CurrenciesRatesResponse? {
        if (shouldReturnNetworkError)
            throw ApiException(500, "Network Error")

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return CurrenciesRatesResponse(
            baseCurrency,
            sdf.format(Calendar.getInstance().time),
            FakeData.popularCurrenciesRates,
            Calendar.getInstance().timeInMillis.toString()
        ).apply {
            success = true
            errorResponse = null
        }
    }

    override suspend fun newCurrenciesConversion(convertedCurrencies: ConvertedCurrencies) {
        conversionHistoryList.add(convertedCurrencies)
        observableConversionHistory.postValue(conversionHistoryList)
    }

    override suspend fun fetchConversionHistory(
        fromDate: String,
        toDate: String
    ): List<ConvertedCurrencies> {
        return conversionHistoryList
    }

    override suspend fun deleteOldConversions(oldDate: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(oldDate)
        conversionHistoryList.removeIf { it.date < date }

        observableConversionHistory.postValue(conversionHistoryList)
    }
}