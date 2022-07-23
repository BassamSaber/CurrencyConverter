package com.samz.convertcurrency.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.samz.convertcurrency.R

/**
 * Generic ViewModel Class with all Common methods and attributes
 */
abstract class BaseViewModel : ViewModel() {
    var isViewAttached = false

    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var context: Context

    @CallSuper
    open fun onViewCreated(context: Context, lifecycleOwner: LifecycleOwner) {
        isViewAttached = true
        this.context = context
        this.lifecycleOwner = lifecycleOwner
    }

    var extrasData: Bundle? = null

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun showErrorDialog(errorMsg: String?) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(true)
        alertDialog.setTitle(R.string.str_error)
        alertDialog.setMessage(errorMsg ?: getString(R.string.something_went_wrong))

        alertDialog.setPositiveButton(R.string.str_ok) { _, _ -> }

        alertDialog.create().show()
    }
}