package com.samz.convertcurrency.ui.screens.details

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.samz.convertcurrency.MainCoroutineRule
import com.samz.convertcurrency.getOrAwaitValue
import com.samz.convertcurrency.model.generalResponse.Resources
import com.samz.convertcurrency.repo.FakeRepo
import com.samz.convertcurrency.utils.Constants
import com.samz.convertcurrency.utils.ResourcesUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        viewModel =
            DetailsViewModel(
                FakeRepo(),
                ResourcesUtils(ApplicationProvider.getApplicationContext())
            )
    }

    @Test
    fun fetchHistoryConversion_ReturnSuccess() {
        viewModel.getHistoryConversion()
        val value = viewModel.conversionsHistoryStatus.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

    @Test
    fun topCurrenciesRatesSuccessResponse_ReturnSuccess() {
        viewModel.extrasData = Bundle().apply {
            putString(Constants.keyBaseCurrency, "EGP")
            putString(Constants.keyConvertAmount, "12")
        }
        viewModel.getTopCurrenciesRates()
        val value = viewModel.topCurrenciesRatesStatus.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Resources.DataStatus.SUCCESS)
    }

    @Test
    fun topCurrenciesRatesWithEmptyCurrencyAmount_ReturnSuccess() {
        viewModel.getTopCurrenciesRates()
        val value = viewModel.topCurrenciesRatesStatus.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }

    @Test
    fun topCurrenciesRatesErrorResponse_ReturnError() {
        ((viewModel.appRepo) as FakeRepo).setShouldReturnNetworkError(true)

        viewModel.extrasData = Bundle().apply {
            putString(Constants.keyBaseCurrency, "EGP")
            putString(Constants.keyConvertAmount, "12")
        }
        viewModel.getTopCurrenciesRates()
        val value = viewModel.topCurrenciesRatesStatus.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Resources.DataStatus.ERROR)
    }
}