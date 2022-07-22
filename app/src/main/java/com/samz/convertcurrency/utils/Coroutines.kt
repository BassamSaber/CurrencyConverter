package com.samz.convertcurrency.utils

import com.samz.convertcurrency.repo.model.generalResponse.ResourcesLiveData
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

}

