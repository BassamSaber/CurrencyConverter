package com.samz.convertcurrency.utils

import java.io.IOException

/**
 * API Request Exception to be handled in the View
 */
class ApiException(errorCode: Int, message: String) : IOException(message)

/**
 * No Internet Connection Exception to be handled in the View
 */
class NoInternetException(message: String) : IOException(message)