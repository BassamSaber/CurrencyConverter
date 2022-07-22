package com.samz.convertcurrency.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Utilities Helper Class for the network Layer
 */
object NetworkUtils {
    /**
     * method to check the network status if it's online or offline
     * @param context the application Context
     * @return boolean true if the isOnline and false otherwise
     */
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}