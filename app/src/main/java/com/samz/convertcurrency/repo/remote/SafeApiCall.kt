package com.samz.convertcurrency.repo.remote

import android.util.Log
import com.samz.convertcurrency.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Generic Class for customizing the API error response
 */
open class SafeApiCall {
    suspend fun <T : Any> invokeRequest(call: suspend () -> Response<T>): T? {
        val response: Response<T> = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            val error = response.errorBody()?.toString()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                    message.append("\n")
                } catch (e: JSONException) {
                    Log.i("JSONException", "Exception in ${SafeApiCall::class.java.name}")
                }

            }
            throw ApiException(response.code(), message.toString())
        }
    }
}