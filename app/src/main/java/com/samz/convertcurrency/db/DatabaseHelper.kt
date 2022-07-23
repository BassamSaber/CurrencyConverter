package com.samz.convertcurrency.db

import com.samz.convertcurrency.db.dao.HistoryConversionDao
import com.samz.convertcurrency.model.ConvertedCurrencies
import javax.inject.Inject

class DatabaseHelper @Inject constructor(private val conversionDao: HistoryConversionDao) {

    suspend fun newCurrencyConversion(convertedCurrencies: ConvertedCurrencies) =
        conversionDao.insert(convertedCurrencies)

    suspend fun newCurrencyConversion(convertedCurrencies: List<ConvertedCurrencies>) =
        conversionDao.insertAll(convertedCurrencies)

    suspend fun fetchConversionHistoryBetweenDates(
        from: String,
        to: String
    ) = conversionDao.fetchConversionHistoryBetweenDates(from, to)

    suspend fun deleteOlderHistory(oldDate: String) =
        conversionDao.deleteOlderHistory(oldDate)

}