package com.samz.convertcurrency.repo.model

import java.util.*

data class ConvertedCurrencies(
    val fromCurrency: String,
    val toCurrency: String,
    val date: Date,
//    val fromAmount:Double,
//    val toAmount:Double
)
