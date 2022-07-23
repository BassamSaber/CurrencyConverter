package com.samz.convertcurrency.model

import com.google.gson.annotations.SerializedName

/**
 * API Response Data Class
 */
data class ConvertCurrencyResponse(
    @SerializedName("data") val date: String,
    @SerializedName("info") val info: ConvertInfo,
    @SerializedName("query") val query: ConvertQuery,
    @SerializedName("result") val result: Double,
) : GeneralResponse() {
    val amount: Double
        get() = query.amount
    val rate: Double
        get() = info.rate
}

data class ConvertInfo(
    @SerializedName("rate") val rate: Double,
    @SerializedName("timestamp") val timestamp: Long,
)

data class ConvertQuery(
    @SerializedName("amount") val amount: Double,
    @SerializedName("from") val fromCurrency: String,
    @SerializedName("to") val toCurrency: String,
)