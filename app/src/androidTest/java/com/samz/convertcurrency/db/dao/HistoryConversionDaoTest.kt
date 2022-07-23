package com.samz.convertcurrency.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.samz.convertcurrency.db.AppDatabase
import com.samz.convertcurrency.model.ConvertedCurrencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class HistoryConversionDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: HistoryConversionDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.conversionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCurrenciesConversion() = runTest {
        val item =
            ConvertedCurrencies(
                "EGP", "USD",
                Calendar.getInstance().apply {
                    set(Calendar.MILLISECOND, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.HOUR_OF_DAY, 0)
                }.time,
                id = 1
            )
        dao.insert(item)

        val allConversions = dao.fetchAllConversionHistory()

        assertThat(allConversions).contains(item)
    }

    @Test
    fun insertListOfCurrenciesConversion() = runTest {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
        val item1 =
            ConvertedCurrencies(
                "EGP", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 1
            )
        val item2 =
            ConvertedCurrencies(
                "EGP", "EUR",
                calendar.time,
                id = 2
            )
        val item3 =
            ConvertedCurrencies(
                "AUR", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 3
            )
        val item4 =
            ConvertedCurrencies(
                "USD", "JPY",
                calendar.apply { add(Calendar.DATE, -2) }.time,
                id = 4
            )
        val list = listOf(item1, item2, item3, item4)

        dao.insertAll(list)

        val allConversions = dao.fetchAllConversionHistory()

        assertThat(allConversions).containsExactlyElementsIn(list)
    }

    @Test
    fun getLatestThreeDaysConversion() = runTest {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val fromDate = sdf.format(Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time)
        val toDate = sdf.format(Calendar.getInstance().apply { add(Calendar.DATE, -3) }.time)
        //Yesterday Conversions
        val item1 =
            ConvertedCurrencies(
                "EGP", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 1
            )
        val item2 =
            ConvertedCurrencies(
                "EGP", "EUR",
                calendar.time,
                id = 2
            )
        //Two-Days Ago conversions
        val item3 =
            ConvertedCurrencies(
                "AUR", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 3
            )
        //Four-Days Ago conversions
        val item4 =
            ConvertedCurrencies(
                "USD", "JPY",
                calendar.apply { add(Calendar.DATE, -2) }.time,
                id = 4
            )
        val list = listOf(item1, item2, item3, item4)
        dao.insertAll(list)

        val last3DaysConversions = dao.fetchConversionHistoryBetweenDates(fromDate, toDate)

        assertThat(last3DaysConversions).hasSize(3)
    }

    @Test
    fun deleteConversionsOlderThanThreeDays() = runTest {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val threeDaysAgoDate =
            sdf.format(Calendar.getInstance().apply { add(Calendar.DATE, -3) }.time)

        //Yesterday Conversions
        val item1 =
            ConvertedCurrencies(
                "EGP", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 1
            )
        val item2 =
            ConvertedCurrencies(
                "EGP", "EUR",
                calendar.time,
                id = 2
            )
        //Two-Days Ago conversions
        val item3 =
            ConvertedCurrencies(
                "AUR", "USD",
                calendar.apply { add(Calendar.DATE, -1) }.time,
                id = 3
            )
        //Four-Days Ago conversions
        val item4 =
            ConvertedCurrencies(
                "USD", "JPY",
                calendar.apply { add(Calendar.DATE, -2) }.time,
                id = 4
            )
        val list = listOf(item1, item2, item3, item4)
        dao.insertAll(list)

        dao.deleteOlderHistory(threeDaysAgoDate)

        val allCurrenciesConversions = dao.fetchAllConversionHistory()

        assertThat(allCurrenciesConversions).hasSize(3)
    }
}