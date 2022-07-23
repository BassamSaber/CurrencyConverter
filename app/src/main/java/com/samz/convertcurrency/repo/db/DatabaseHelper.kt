package com.samz.convertcurrency.repo.db

import com.samz.convertcurrency.repo.db.dao.HistoryConversionDao
import com.samz.convertcurrency.repo.model.ConvertedCurrencies
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

    suspend fun fetchConversionHistoryLessThanDate(oldDate: String) =
        conversionDao.fetchConversionHistoryLessThanDate(oldDate)

    suspend fun deleteOlderHistory(oldDate: String) =
        conversionDao.deleteOlderHistory(oldDate)

}