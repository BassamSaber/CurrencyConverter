package com.samz.convertcurrency.ui.screens.details

import androidx.databinding.ObservableField
import com.samz.convertcurrency.R
import com.samz.convertcurrency.repo.model.ConvertedCurrencies
import com.samz.convertcurrency.ui.base.adapter.BaseItemViewModel

class HistoryIVM(convertedCurrencies: ConvertedCurrencies) : BaseItemViewModel {
    val fromCurrency = ObservableField("")
    val toCurrency = ObservableField("")

    init {
        fromCurrency.set(convertedCurrencies.fromCurrency)
        toCurrency.set(convertedCurrencies.toCurrency)
    }

    override fun getLayoutId(): Int = R.layout.item_convert_history
}