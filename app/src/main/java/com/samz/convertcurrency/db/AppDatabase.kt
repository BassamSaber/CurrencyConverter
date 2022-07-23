package com.samz.convertcurrency.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samz.convertcurrency.db.dao.HistoryConversionDao
import com.samz.convertcurrency.model.ConvertedCurrencies

@Database(entities = [ConvertedCurrencies::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionDao(): HistoryConversionDao
}