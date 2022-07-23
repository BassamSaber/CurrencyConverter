package com.samz.convertcurrency.repo.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val errorCode: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("info")
    val errorMsg: String
)