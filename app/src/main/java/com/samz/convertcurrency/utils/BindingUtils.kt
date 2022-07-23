package com.samz.convertcurrency.utils

import android.R
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.samz.convertcurrency.ui.base.adapter.BaseAdapter
import com.samz.convertcurrency.ui.base.adapter.SpinnerAdapter


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

        val adapter = SpinnerAdapter(spinner.context, R.layout.simple_spinner_item, items)
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


    /**
     * Set RecyclerView Adapter
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: BaseAdapter<*>) {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = true
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }


    @JvmStatic
    @BindingAdapter("pieChartItems")
    fun setPieChartItems(pieChart: PieChart, items: HashMap<String, Float>?) {
        if (items.isNullOrEmpty()) return
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(6f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.description.isEnabled = false

        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true

        val entries: ArrayList<PieEntry> = ArrayList()
        for (key in items.keys)
            entries.add(PieEntry(items[key]!!, key))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors;

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(6f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

}
