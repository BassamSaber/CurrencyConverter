package com.samz.convertcurrency.db

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    @TypeConverter
    fun fromDate(value: String?): Date? {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun toDate(date: Date): String? {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return df.format(date)
    }
}