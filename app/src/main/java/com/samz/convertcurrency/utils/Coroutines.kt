package com.samz.convertcurrency.utils

import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.model.generalResponse.ResourcesLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Coroutines Helper Class for API Call that publish the API response data to The Generic LiveData Type
 */
object Coroutines {
    fun <T> call(work: suspend () -> T): ResourcesLiveData<T> {
        val data: ResourcesLiveData<T> = ResourcesLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = work.invoke()
                withContext(Dispatchers.Main) {
                    data.postSuccess(response)
                }
            } catch (error: ApiException) {
                data.postError(error)
            } catch (error: NoInternetException) {
                data.postError(error)
            } catch (error: Exception) {
                data.postError(error)
            }
        }
        return data
    }

    fun <T> call(work: suspend () -> T, callback: (Resources<T>) -> Unit) {
        val data: Resources<T> = Resources()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = work.invoke()
                withContext(Dispatchers.Main) {
                    data.success(response)
                }
            } catch (error: ApiException) {
                data.error(error)
            } catch (error: NoInternetException) {
                data.error(error)
            } catch (error: Exception) {
                data.error(error)
            }
            callback(data)
        }
    }

}

