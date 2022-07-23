package com.samz.convertcurrency.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

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

}