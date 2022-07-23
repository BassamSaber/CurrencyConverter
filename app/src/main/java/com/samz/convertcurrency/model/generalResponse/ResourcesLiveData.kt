package com.samz.convertcurrency.model.generalResponse

import androidx.lifecycle.MutableLiveData

/**
 * Generic LiveData with The General ResponseType (Resource) to be observed after the API Response
 */
class ResourcesLiveData<T> : MutableLiveData<Resources<T>>() {
    fun postSuccess(data: T) {
        postValue(Resources<T>().success(data))
    }

    fun postError(throwable: Throwable) {
        postValue(Resources<T>().error(throwable))
    }
}