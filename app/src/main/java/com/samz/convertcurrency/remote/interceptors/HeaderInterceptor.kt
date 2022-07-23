package com.samz.convertcurrency.remote.interceptors

import com.samz.convertcurrency.BuildConfig
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for the Adding the Default Headers for all The API Requests
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var headers = request.headers()
        headers = headers.newBuilder()
            .addAll(Headers.of(getDefaultHeaders()))
            .build()

        val requestBuilder = request.newBuilder()
        requestBuilder.headers(headers)

        return chain.proceed(requestBuilder.build())
    }

    private fun getDefaultHeaders(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Accept"] = "application/json"
        headers["Content-Type"] = "application/json"
        headers["apikey"] = BuildConfig.API_KEY

        return headers
    }
}