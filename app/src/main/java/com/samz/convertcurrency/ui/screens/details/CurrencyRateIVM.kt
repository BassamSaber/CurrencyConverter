package com.samz.convertcurrency.ui.screens.details

import androidx.databinding.ObservableField
import com.samz.convertcurrency.R
import com.samz.convertcurrency.ui.base.adapter.BaseItemViewModel

class CurrencyRateIVM(amount: Double, currency: String, rate: Double) : BaseItemViewModel {
    val title = ObservableField("")

    init {
        val convertedAmount = amount * rate
        title.set(String.format("%.5f %s", convertedAmount, currency))
    }

    override fun getLayoutId(): Int = R.layout.item_currency_rate
}