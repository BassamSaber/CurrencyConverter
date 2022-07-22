package com.samz.convertcurrency.ui.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.samz.convertcurrency.R
import com.samz.convertcurrency.utils.Utilities

/**
 * Adapter for the creating the items of the spinner
 */
class SpinnerAdapter(
    context: Context?,
    resource: Int,
    items: List<String>?
) : ArrayAdapter<String?>(
    context!!,
    resource,
    items ?: ArrayList<String>() as List<String>
) {

    /**
     * init the View that be show in the spinner
     */
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val textView = super.getView(position, convertView, parent) as TextView
        textView.setTextColor(
            Utilities.getColorFromRes(
                textView.context,
                if (position == 0) R.color.lightGrey else R.color.black
            )
        )
        return textView
    }

    /**
     * init Spinner item on opened state of the spinner
     */
    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val textView = super.getDropDownView(position, convertView, parent) as TextView
        textView.setTextColor(
            Utilities.getColorFromRes(
                textView.context,
                if (position == 0) R.color.lightGrey else R.color.black
            )
        )
        return textView
    }

    /**
     * check if the spinner it in position is Clickable(Enabled) or not
     */
    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}