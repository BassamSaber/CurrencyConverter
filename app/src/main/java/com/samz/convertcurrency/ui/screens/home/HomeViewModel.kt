package com.samz.convertcurrency.ui.screens.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import com.samz.convertcurrency.R
import com.samz.convertcurrency.repo.AppRepo
import com.samz.convertcurrency.repo.model.generalResponse.Resources
import com.samz.convertcurrency.ui.base.BaseViewModel
import com.samz.convertcurrency.utils.Constants
import com.samz.convertcurrency.utils.Coroutines
import com.samz.convertcurrency.utils.NoInternetException
import com.samz.convertcurrency.utils.SpinnerInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepo: AppRepo) : BaseViewModel() {

    val isLoading = ObservableBoolean(false)
    val isNetworkError = ObservableBoolean(false)
    val errorMsg = ObservableField("")

    val isConvertLoading = ObservableBoolean(false)

    val fromValue = ObservableField("1")
    val toValue = ObservableField("")
    var rate: Double = 0.0

    val currencySymbolsFromList = ObservableArrayList<String>()
    val currencySymbolsToList = ObservableArrayList<String>()
    var fromSelectedCurrency = ObservableInt(0)
    var toSelectedCurrency = ObservableInt(0)

    val onFromCurrencyChangeListener = object : SpinnerInterface {
        override fun onItemClick(position: Int) {
            if (fromSelectedCurrency.get() == position) return
            rate = 0.0
            fromSelectedCurrency.set(position)
            convertCurrencyAmount()
        }
    }

    val onToCurrencyChangeListener = object : SpinnerInterface {
        override fun onItemClick(position: Int) {
            if (toSelectedCurrency.get() == position) return
            rate = 0.0
            toSelectedCurrency.set(position)
            convertCurrencyAmount()
        }
    }

    override fun onViewCreated(context: Context, lifecycleOwner: LifecycleOwner) {
        super.onViewCreated(context, lifecycleOwner)
        initCurrenciesSymbols()
    }

    fun initCurrenciesSymbols() {
        isLoading.set(true)
        getCurrencySymbols().observe(lifecycleOwner) { response ->
            isLoading.set(false)
            if (response.status == Resources.DataStatus.SUCCESS) {
                errorMsg.set("")

                currencySymbolsFromList.add(getString(R.string.str_from))
                currencySymbolsToList.add(getString(R.string.str_to))
                response.data?.symbols?.keys?.let {
                    currencySymbolsFromList.addAll(it)
                    currencySymbolsToList.addAll(it)
                }
            } else if (response.status == Resources.DataStatus.ERROR) {
                errorMsg.set(response.error?.message ?: "Error")
                isNetworkError.set(response.error is NoInternetException)
            }
        }
    }

    private fun convertCurrencyAmount() {
        if (fromValue.get()
                .isNullOrEmpty() || fromSelectedCurrency.get() == 0 || toSelectedCurrency.get() == 0
        ) return
        val amount = fromValue.get()?.toDouble() ?: 1.0
        if (rate != 0.0) {
            toValue.set((amount * rate).toString())
        } else {
            isConvertLoading.set(true)
            convertCurrencyAmount(
                amount,
                currencySymbolsFromList[fromSelectedCurrency.get()],
                currencySymbolsToList[toSelectedCurrency.get()]
            ).observe(lifecycleOwner) { response ->
                isConvertLoading.set(false)
                if (response.status == Resources.DataStatus.SUCCESS) {
                    rate = response.data?.rate ?: 0.0
//                    if (response.data?.amount == fromValue.get()?.toDouble()) {
                    toValue.set(response.data?.rate.toString())
//                    } else {
//                        val newAmount = fromValue.get()?.toDouble() ?: 1.0
//                        toValue.set((newAmount * rate).toString())
//                    }
                } else if (response.status == Resources.DataStatus.ERROR) {
                    showErrorDialog(response.error?.message)
                }
            }
        }
    }

    fun swapFromToCurrencies() {
        rate = 0.0

        val prevFrom = fromSelectedCurrency.get()
        fromSelectedCurrency.set(toSelectedCurrency.get())
        toSelectedCurrency.set(prevFrom)

        val prevFromValue = fromValue.get()
        fromValue.set(toValue.get())
        toValue.set(prevFromValue)

    }

    fun afterTextChanged(editable: Editable) {
        convertCurrencyAmount()
    }

    private fun getCurrencySymbols() = Coroutines.call { appRepo.getCurrencySymbols() }
    private fun convertCurrencyAmount(amount: Double, fromCurrency: String, toCurrency: String) =
        Coroutines.call { appRepo.convertCurrencyAmount(amount, fromCurrency, toCurrency) }

    fun getDetailsNavData(): Bundle {
        val bundle = Bundle()
        if (fromSelectedCurrency.get() != 0) {
            bundle.putString(
                Constants.keyBaseCurrency,
                currencySymbolsFromList[fromSelectedCurrency.get()]
            )
            bundle.putString(
                Constants.keyConvertAmount,
                fromValue.get()
            )
        }

        return bundle
    }
}