package com.samz.convertcurrency.ui.screens.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.samz.convertcurrency.R
import com.samz.convertcurrency.repo.model.ConvertedCurrencies
import com.samz.convertcurrency.ui.base.adapter.BaseAdapter
import com.samz.convertcurrency.ui.base.adapter.BaseItemViewModel

class HistoryGroupIVM(date: String, items: List<ConvertedCurrencies>) : BaseItemViewModel {
    val title = ObservableField("")
    val isExpanded = ObservableBoolean(true)

    var adapter: BaseAdapter<HistoryIVM>

    init {
        val list = ArrayList<HistoryIVM>()
        for (item in items)
            list.add(HistoryIVM(item))
        adapter = BaseAdapter(list, null)

        title.set(date)
    }

    fun expandCollapseToggle() {
        isExpanded.set(!isExpanded.get())
    }

    override fun getLayoutId(): Int = R.layout.item_convert_history_group
}