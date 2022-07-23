package com.samz.convertcurrency.model

import com.google.gson.annotations.SerializedName

/**
 * API Response Data Class
 */
data class SymbolResponse(
    @SerializedName("symbols")
    val symbols: Map<String, String>
) : GeneralResponse()
