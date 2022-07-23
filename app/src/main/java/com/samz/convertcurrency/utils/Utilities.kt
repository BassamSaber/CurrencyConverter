package com.samz.convertcurrency.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utilities Helper Class
 */
object Utilities {

    /**
     * @param context ViewContext
     * @param id the Color Resource Id
     *
     * @return Color value of the passed ColorResId
     */
    fun getColorFromRes(context: Context, @ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun dateFormatForDisplay(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return simpleDateFormat.format(date)
    }
}