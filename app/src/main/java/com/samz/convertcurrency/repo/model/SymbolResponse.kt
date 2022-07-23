package com.samz.convertcurrency.repo.model

import com.google.gson.annotations.SerializedName

/**
 * API Response Data Class
 */
data class SymbolResponse(
    @SerializedName("symbols")
    val symbols: Map<String, String>
) : GeneralResponse()
