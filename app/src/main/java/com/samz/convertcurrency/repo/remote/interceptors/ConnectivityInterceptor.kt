package com.samz.convertcurrency.repo.remote.interceptors

import android.content.Context
import com.samz.convertcurrency.utils.NetworkUtils
import com.samz.convertcurrency.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor that check the network connectivity (if it's online or not)
 */
class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isOnline(context))
            throw NoInternetException("No internet Connection")
        return chain.proceed(chain.request())
    }

}