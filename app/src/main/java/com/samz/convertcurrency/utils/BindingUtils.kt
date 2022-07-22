package com.samz.convertcurrency.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import com.samz.convertcurrency.ui.base.SpinnerAdapter

/**
 * Utilities Class for The DataBinding Adapters
 */
object BindingUtils {

    /**
     * @param spinner Spinner View
     * @param items need data to be viewed
     *
     * Setting the passed data to the spinner view
     */
    @JvmStatic
    @BindingAdapter(value = ["spinnerItems"])
    fun setSpinnerItems(spinner: Spinner, items: ObservableArrayList<String>) {
        if (items.isEmpty()) return

        val adapter = SpinnerAdapter(spinner.context, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
    }


    /**
     * binding Adapter for Spinner item click listener
     */
    @JvmStatic
    @BindingAdapter("onItemSelected")
    fun setSpinnerListener(spinner: Spinner, listener: SpinnerInterface) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listener.onItemClick(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    /**
     * set selected item in the spinner
     */
    @JvmStatic
    @BindingAdapter("selectedItem")
    fun setSpinnerSelectedItem(spinner: Spinner, position: Int) {
        if (position == -1) return

        spinner.setSelection(position)
    }
}
