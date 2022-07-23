package com.samz.convertcurrency.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.samz.convertcurrency.R
import javax.inject.Inject

class ResourcesUtils @Inject constructor(private val context: Context) {

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