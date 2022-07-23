package com.samz.convertcurrency.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samz.convertcurrency.model.ConvertedCurrencies

@Dao
interface HistoryConversionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ConvertedCurrencies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ConvertedCurrencies>)

    @Query("SELECT * FROM conversion")
    suspend fun fetchAllConversionHistory(
    ): List<ConvertedCurrencies>

    @Query("SELECT * FROM conversion WHERE date <= date(:from) AND date >= date(:to)")
    suspend fun fetchConversionHistoryBetweenDates(
        from: String,
        to: String
    ): List<ConvertedCurrencies>

    @Query("DELETE FROM conversion WHERE date < date(:oldDate)")
    suspend fun deleteOlderHistory(oldDate: String)
}