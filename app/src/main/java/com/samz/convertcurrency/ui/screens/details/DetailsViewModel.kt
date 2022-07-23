package com.samz.convertcurrency.ui.screens.details

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.samz.convertcurrency.R
import com.samz.convertcurrency.model.ConvertedCurrencies
import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.repo.RepoInterface
import com.samz.convertcurrency.ui.base.BaseViewModel
import com.samz.convertcurrency.ui.base.adapter.BaseAdapter
import com.samz.convertcurrency.utils.Constants
import com.samz.convertcurrency.utils.Coroutines
import com.samz.convertcurrency.utils.ResourcesUtils
import com.samz.convertcurrency.utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val appRepo: RepoInterface,
    private val resUtils: ResourcesUtils
) : BaseViewModel() {
    val isHistoryLoading = ObservableBoolean(false)
    val isRatesLoading = ObservableBoolean(false)

    val ratesErrorMsg = ObservableField("")
    val historyErrorMsg = ObservableField("")

    val fromAmountWithCurrency = ObservableField("")

    private val ratesList = ArrayList<CurrencyRateIVM>()
    val ratesAdapter = BaseAdapter(ratesList, null)

    val historyChartItem = ObservableField<HashMap<String, Float>>()
    private val historyList = ArrayList<HistoryGroupIVM>()
    val historyAdapter = BaseAdapter(historyList, null)

    override fun onViewCreated() {
        super.onViewCreated()
        initViewData()
    }

    private fun initViewData() {
        getTopCurrenciesRates()

        getHistoryConversion()
    }


    private fun updatePieChartData(list: List<ConvertedCurrencies>) {
        val listMapped =
            list.map { "${it.fromCurrency} -> ${it.toCurrency}" }.toList().groupingBy { it }
                .eachCount()
        val map = HashMap<String, Float>()
        for (item in listMapped)
            map[item.key] = item.value.toFloat() / list.size

        historyChartItem.set(map)
    }

    private fun getHistoryConversion() {
        val fromDate = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
        val toDate = Calendar.getInstance().apply { add(Calendar.DATE, -3) }.time
        isHistoryLoading.set(true)
        Coroutines.call({
            appRepo.fetchConversionHistory(
                Utilities.formatDate(fromDate),
                Utilities.formatDate(toDate)
            )
        }, { response ->
            isHistoryLoading.set(false)
            if (response.status == Resources.DataStatus.SUCCESS) {
                val list = response.data
                if (!list.isNullOrEmpty()) {
                    updatePieChartData(list)

                    val groupedList = list.groupBy { it.date }
                    historyList.clear()
                    for (key in groupedList.keys)
                        historyList.add(
                            HistoryGroupIVM(
                                Utilities.formatDate(key),
                                groupedList[key] ?: ArrayList()
                            )
                        )
                    historyAdapter.notifyDataSetChanged()
                }
            } else if (response.status == Resources.DataStatus.ERROR) {
                historyErrorMsg.set(
                    response.error?.message ?: getString(R.string.something_went_wrong)
                )
            }
        })

    }

    private fun getTopCurrenciesRates() {
        val baseCurrency = extrasData?.getString(Constants.keyBaseCurrency)
        val amount = extrasData?.getString(Constants.keyConvertAmount)
        if (baseCurrency.isNullOrEmpty() || amount.isNullOrEmpty()) {
            ratesErrorMsg.set(getString(R.string.str_empty_from_currency_error))
            return
        }

        fromAmountWithCurrency.set("$amount $baseCurrency")
        isRatesLoading.set(true)
        Coroutines.call({ appRepo.convertAmountToPopularCurrencies(baseCurrency) }, { response ->
            isRatesLoading.set(false)
            if (response.status == Resources.DataStatus.SUCCESS) {
                ratesList.clear()
                if (response.data?.rates?.isEmpty() == true) {
                    ratesErrorMsg.set(getString(R.string.str_empty_from_currency_error))
                } else {
                    val rates = response.data!!.rates
                    for (key in rates.keys)
                        ratesList.add(CurrencyRateIVM(amount.toDouble(), key, rates[key] ?: 0.0))
                }
                ratesAdapter.notifyDataSetChanged()
            } else if (response.status == Resources.DataStatus.ERROR) {
                ratesErrorMsg.set(
                    response.error?.message ?: getString(R.string.something_went_wrong)
                )
            }
        })
    }

    private fun getString(resId: Int): String = resUtils.getString(resId)
//    private fun showErrorDialog(errorMsg:String?) = resUtils.showErrorDialog(errorMsg)
}