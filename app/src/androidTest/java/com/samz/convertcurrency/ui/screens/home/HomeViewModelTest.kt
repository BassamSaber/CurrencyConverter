package com.samz.convertcurrency.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.samz.convertcurrency.MainCoroutineRule
import com.samz.convertcurrency.getOrAwaitValue
import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.repo.FakeRepo
import com.samz.convertcurrency.utils.ResourcesUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel =
            HomeViewModel(FakeRepo(), ResourcesUtils(ApplicationProvider.getApplicationContext()))
    }

    @Test
    fun fetchCurrenciesSymbolsResponseSuccess_ReturnTrue() {
        viewModel.initCurrenciesSymbols()
        val value = viewModel.currenciesSymbolsStatus.getOrAwaitValue()
        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

    @Test
    fun fetchCurrenciesSymbolsResponseError_ReturnError() {
        ((viewModel.appRepo) as FakeRepo).setShouldReturnNetworkError(true)
        viewModel.initCurrenciesSymbols()
        val value = viewModel.currenciesSymbolsStatus.getOrAwaitValue()
        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }

    @Test
    fun convertCurrencyAmountResponseSuccess_ReturnSuccess() {
        //Init Data
        viewModel.initCurrenciesSymbols()

        viewModel.currenciesSymbolsStatus.getOrAwaitValue()

        viewModel.fromSelectedCurrency.set(3)
        viewModel.toSelectedCurrency.set(6)
        viewModel.convertCurrencyAmount()
        val value = viewModel.convertCurrenciesStatus.getOrAwaitValue()

        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

    @Test
    fun convertCurrencyAmountEmptyFromAmount_ReturnError() {
        //Init Data
        viewModel.initCurrenciesSymbols()
        viewModel.currenciesSymbolsStatus.getOrAwaitValue()

        viewModel.fromValue.set("")
        viewModel.fromSelectedCurrency.set(3)
        viewModel.toSelectedCurrency.set(6)
        viewModel.convertCurrencyAmount()
        val value = viewModel.convertCurrenciesStatus.getOrAwaitValue()

        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }

    @Test
    fun convertCurrencyAmountNotSelectedCurrencies_ReturnError() {
        //Init Data
        viewModel.initCurrenciesSymbols()
        viewModel.currenciesSymbolsStatus.getOrAwaitValue()

        viewModel.convertCurrencyAmount()
        val value = viewModel.convertCurrenciesStatus.getOrAwaitValue()

        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }

    @Test
    fun convertCurrencyAmountResponseError_ReturnError() {
        ((viewModel.appRepo) as FakeRepo).setShouldReturnNetworkError(true)
        //Init Data
        viewModel.initCurrenciesSymbols()
        viewModel.currenciesSymbolsStatus.getOrAwaitValue()

        viewModel.fromValue.set("")
        viewModel.fromSelectedCurrency.set(3)
        viewModel.toSelectedCurrency.set(6)

        viewModel.convertCurrencyAmount()
        val value = viewModel.convertCurrenciesStatus.getOrAwaitValue()

        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }

    @Test
    fun saveCurrenciesConversions_ReturnSuccess() {
        //Init Data
        viewModel.initCurrenciesSymbols()
        viewModel.currenciesSymbolsStatus.getOrAwaitValue()

        viewModel.fromSelectedCurrency.set(3)
        viewModel.toSelectedCurrency.set(6)

        viewModel.saveCurrenciesConversion()

        val value = viewModel.saveConversionsStatus.getOrAwaitValue()
        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

    @Test
    fun deleteOldConversions_ReturnSuccess() {
        viewModel.clearOldConversion()

        val value = viewModel.deleteOldConversionsStatus.getOrAwaitValue()
        Truth.assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

}