package com.samz.convertcurrency.repo.model

import com.google.gson.annotations.SerializedName

/**
 * API Response Data Class
 */
data class CurrenciesRatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double>,
    @SerializedName("timestamp") val timestamp: String
) : GeneralResponse()

