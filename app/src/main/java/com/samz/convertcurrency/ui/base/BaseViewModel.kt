package com.samz.convertcurrency.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.utils.ApiException
import com.samz.convertcurrency.utils.NoInternetException
import kotlinx.coroutines.launch

/**
 * Generic ViewModel Class with all Common methods and attributes
 */
abstract class BaseViewModel : ViewModel() {
    var isViewAttached = false


    @CallSuper
    open fun onViewCreated() {
        isViewAttached = true
    }

    var extrasData: Bundle? = null


    protected fun <T> call(work: suspend () -> T, callback: (Resources<T>) -> Unit) {
        val data: Resources<T> = Resources()
        viewModelScope.launch {
            try {
                val response = work.invoke()
                data.success(response)
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