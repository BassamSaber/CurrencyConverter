package com.samz.convertcurrency.utils

import android.content.Context
import javax.inject.Inject

class ResourcesUtils @Inject constructor(private val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}