package com.samz.convertcurrency.remote

import android.util.Log
import com.samz.convertcurrency.model.GeneralResponse
import com.samz.convertcurrency.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Generic Class for customizing the API error response
 */
@Suppress("BlockingMethodInNonBlockingContext")
open class SafeApiCall {
    suspend fun <T : GeneralResponse> invokeRequest(call: suspend () -> Response<T>): T? {
        val response: Response<T> = call.invoke()
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody == null || responseBody.success)
                return responseBody
            else
                throw  ApiException(
                    responseBody.errorResponse?.errorCode ?: 500,
                    responseBody.errorResponse?.errorMsg ?: ""
                )
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                    message.append("\n")
                } catch (e: JSONException) {
                    Log.i("JSONException", "Exception: $e")
                }

            }
            throw ApiException(response.code(), message.toString())
        }
    }

}