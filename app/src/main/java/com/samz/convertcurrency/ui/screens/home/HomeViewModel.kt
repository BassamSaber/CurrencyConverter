package com.samz.convertcurrency.ui.screens.home

import android.os.Bundle
import android.text.Editable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.samz.convertcurrency.R
import com.samz.convertcurrency.model.ConvertCurrencyResponse
import com.samz.convertcurrency.model.ConvertedCurrencies
import com.samz.convertcurrency.model.SymbolResponse
import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.repo.RepoInterface
import com.samz.convertcurrency.ui.base.BaseViewModel
import com.samz.convertcurrency.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val appRepo: RepoInterface,
    private val resUtils: ResourcesUtils
) : BaseViewModel() {

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


    //Status
    private val _currenciesSymbolsStatus = MutableLiveData<Resources<SymbolResponse?>>()
    val currenciesSymbolsStatus = _currenciesSymbolsStatus
    private val _convertCurrenciesStatus = MutableLiveData<Resources<ConvertCurrencyResponse?>>()
    val convertCurrenciesStatus = _convertCurrenciesStatus
    private val _saveConversionsStatus = MutableLiveData<Resources<Unit>>()
    val saveConversionsStatus = _saveConversionsStatus
    private val _deleteOldConversionsStatus = MutableLiveData<Resources<Unit>>()
    val deleteOldConversionsStatus = _deleteOldConversionsStatus

    override fun onViewCreated() {
        super.onViewCreated()
        initCurrenciesSymbols()

        clearOldConversion()
    }

    fun clearOldConversion() {
        call({
            appRepo.deleteOldConversions(
                Utilities.formatDate(
                    Calendar.getInstance().apply { add(Calendar.DATE, -3) }.time
                )
            )
        }, { response ->
            _deleteOldConversionsStatus.value = response
            if (response.status == Resources.DataStatus.ERROR)
                showErrorDialog(response.error?.message)
        })
    }

    fun initCurrenciesSymbols() {
        isLoading.set(true)
        call({ appRepo.getCurrencySymbols() }, { response ->
            isLoading.set(false)
            _currenciesSymbolsStatus.value = response

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
        })

    }

    fun convertCurrencyAmount() {
        if (fromValue.get()
                .isNullOrEmpty() || fromSelectedCurrency.get() == 0 || toSelectedCurrency.get() == 0
        ) {
            _convertCurrenciesStatus.postValue(Resources<ConvertCurrencyResponse?>().apply {
                error(
                    Exception("Please select from/to currencies and enter amount")
                )
            })
            return
        }
        val amount = fromValue.get()?.toDouble() ?: 1.0
        if (rate != 0.0) {
            setConversionResult((amount * rate))
        } else {
            isConvertLoading.set(true)
            call({
                appRepo.convertCurrencyAmount(
                    amount,
                    currencySymbolsFromList[fromSelectedCurrency.get()],
                    currencySymbolsToList[toSelectedCurrency.get()]
                )
            }, { response ->
                isConvertLoading.set(false)
                _convertCurrenciesStatus.value = response

                if (response.status == Resources.DataStatus.SUCCESS) {
                    rate = response.data?.rate ?: 0.0
                    if (response.data?.amount == fromValue.get()?.toDouble()) {
                        setConversionResult(response.data?.result ?: 0.0)
                    } else {
                        val newAmount = fromValue.get()?.toDouble() ?: 1.0
                        setConversionResult((newAmount * rate))
                    }
                    saveCurrenciesConversion()
                } else if (response.status == Resources.DataStatus.ERROR) {
                    showErrorDialog(response.error?.message)
                }
            })
        }
    }

    fun saveCurrenciesConversion() {
        call({
            appRepo.newCurrenciesConversion(
                ConvertedCurrencies(
                    currencySymbolsFromList[fromSelectedCurrency.get()],
                    currencySymbolsToList[toSelectedCurrency.get()],
                    Calendar.getInstance().time
                )
            )
        }, { response ->
            _saveConversionsStatus.value = response
        })
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

    private fun setConversionResult(result: Double) {
        toValue.set(String.format("%.5f", result))
    }

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

    private fun getString(resId: Int): String = resUtils.getString(resId)
}