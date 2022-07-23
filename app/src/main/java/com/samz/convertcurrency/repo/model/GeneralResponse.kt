package com.samz.convertcurrency.repo.model

import com.google.gson.annotations.SerializedName

open class GeneralResponse {
    @SerializedName("success")
    var success: Boolean = true

    @SerializedName("error")
    var errorResponse: ErrorResponse? = null
}