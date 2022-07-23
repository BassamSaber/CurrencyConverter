package com.samz.convertcurrency.repo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "conversion")
data class ConvertedCurrencies(
    val fromCurrency: String,
    val toCurrency: String,
    val date: Date,
//    val fromAmount:Double,
//    val toAmount:Double
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
