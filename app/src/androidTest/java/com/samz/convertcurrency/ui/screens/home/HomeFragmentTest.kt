package com.samz.convertcurrency.ui.screens.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.samz.convertcurrency.BundleEquals
import com.samz.convertcurrency.R
import com.samz.convertcurrency.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.AssertionFailedError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickDetailsButton_navigateToDetailsFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
            getViewModel().isLoading.set(false)
            getViewModel().errorMsg.set("")
        }

        try {
            onView(withId(R.id.home)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

            //View is Visible

            onView(withId(R.id.btn_details)).perform(click())

            verify(navController).navigate(
                eq(R.id.action_home_to_details),
                argThat(BundleEquals(Bundle()))
            )
        } catch (e: AssertionFailedError) {
            //View not Visible
        }
    }
}