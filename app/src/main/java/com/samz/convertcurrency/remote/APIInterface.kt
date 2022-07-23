package com.samz.convertcurrency.remote

import com.samz.convertcurrency.model.ConvertCurrencyResponse
import com.samz.convertcurrency.model.CurrenciesRatesResponse
import com.samz.convertcurrency.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API Client for all The needed Requests
 */
interface APIInterface {

    @GET(APIConstants.symbolsURl)
    suspend fun getCurrencySymbols(): Response<SymbolResponse>

    @GET(APIConstants.convertURl)
    suspend fun convertCurrencyAmount(
        @Query("amount") amount: Double,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String
    ): Response<ConvertCurrencyResponse>


    @GET(APIConstants.latestURl)
    suspend fun getCurrenciesRates(
        @Query("base") baseCurrency: String,
        @Query("symbols") symbols: String
    ): Response<CurrenciesRatesResponse>


}