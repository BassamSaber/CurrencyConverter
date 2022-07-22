package com.samz.convertcurrency.repo

import com.samz.convertcurrency.repo.model.CurrenciesRatesResponse
import com.samz.convertcurrency.repo.remote.APIInterface
import com.samz.convertcurrency.repo.remote.SafeApiCall
import javax.inject.Inject

/**
 * @param apiClient The API Retrofit Interface instance of the API requests
 *
 * The Repo Class that response for calling the API or the Locale data
 */
class AppRepo @Inject constructor(private val apiClient: APIInterface) : SafeApiCall() {

    /**
     * return all the Available Currencies Symbols to be Viewed
     */
    suspend fun getCurrencySymbols() = invokeRequest { apiClient.getCurrencySymbols() }

    /**
     * @param amount The amount to be converted.
     * @param fromCurrency The three-letter currency code of the currency you would like to convert from.
     * @param toCurrency The three-letter currency code of the currency you would like to convert to.
     *
     * convert any amount from one currency to another. In order to convert currency.
     */
    suspend fun convertCurrencyAmount(amount: Double, fromCurrency: String, toCurrency: String) =
        invokeRequest { apiClient.convertCurrencyAmount(amount, fromCurrency, toCurrency) }

    /**
     * @param baseCurrency the three-letter currency code of your preferred base currency.
     *
     * the top 10 popular currencies referred from this link
     * "https://www.ig.com/en/trading-strategies/what-are-the-top-10-most-traded-currencies-in-the-world-200115"
     *
     * Returns real-time exchange rate data for the top 10 Popular currencies to the baseCurrency
     */
    suspend fun convertAmountToPopularCurrencies(baseCurrency: String): CurrenciesRatesResponse? {
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
        val endIndex = popularCurrencies.toString().length - 1
        val symbols = popularCurrencies.toString().substring(1, endIndex)

        return invokeRequest { apiClient.getCurrenciesRates(baseCurrency, symbols) }
    }
}