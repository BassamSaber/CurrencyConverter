package com.samz.convertcurrency.repo

import com.samz.convertcurrency.db.DatabaseHelper
import com.samz.convertcurrency.model.ConvertedCurrencies
import com.samz.convertcurrency.model.CurrenciesRatesResponse
import com.samz.convertcurrency.remote.APIInterface
import com.samz.convertcurrency.remote.SafeApiCall
import javax.inject.Inject

/**
 * @param apiClient The API Retrofit Interface instance of the API requests
 *
 * The Repo Class that response for calling the API or the Locale data
 */
class AppRepo @Inject constructor(
    private val apiClient: APIInterface,
    private val databaseHelper: DatabaseHelper
) : RepoInterface, SafeApiCall() {

    /**
     * return all the Available Currencies Symbols to be Viewed
     */
    override suspend fun getCurrencySymbols() = invokeRequest { apiClient.getCurrencySymbols() }

    /**
     * @param amount The amount to be converted.
     * @param fromCurrency The three-letter currency code of the currency you would like to convert from.
     * @param toCurrency The three-letter currency code of the currency you would like to convert to.
     *
     * convert any amount from one currency to another. In order to convert currency.
     */
    override suspend fun convertCurrencyAmount(
        amount: Double,
        fromCurrency: String,
        toCurrency: String
    ) =
        invokeRequest { apiClient.convertCurrencyAmount(amount, fromCurrency, toCurrency) }

    /**
     * @param baseCurrency the three-letter currency code of your preferred base currency.
     *
     * the top 10 popular currencies referred from this link
     * "https://www.ig.com/en/trading-strategies/what-are-the-top-10-most-traded-currencies-in-the-world-200115"
     *
     * Returns real-time exchange rate data for the top 10 Popular currencies to the baseCurrency
     */
    override suspend fun convertAmountToPopularCurrencies(baseCurrency: String): CurrenciesRatesResponse? {
        val popularCurrencies = arrayOf(
            "USD",
            "EUR",
            "JPY",
            "GBP",
            "AUD",
            "CAD",
            "CHF",
            "CNH",
            "HKD",
            "NZD"
        )
        val popularCurrenciesStr = popularCurrencies.contentToString()
        val endIndex = popularCurrenciesStr.length - 1
        val symbols = popularCurrenciesStr.substring(1, endIndex)

        return invokeRequest { apiClient.getCurrenciesRates(baseCurrency, symbols) }
    }

    override suspend fun newCurrenciesConversion(convertedCurrencies: ConvertedCurrencies) =
        databaseHelper.newCurrencyConversion(convertedCurrencies)

    override suspend fun fetchConversionHistory(fromDate: String, toDate: String) =
        databaseHelper.fetchConversionHistoryBetweenDates(fromDate, toDate)

    override suspend fun deleteOldConversions(oldDate: String) =
        databaseHelper.deleteOlderHistory(oldDate)

}