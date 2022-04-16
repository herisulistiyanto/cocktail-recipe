package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.nonalcohol

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.andro.indieschool.cocktailapp.R
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NonAlcoholFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var testFragmentScenario: FragmentScenario<NonAlcoholFragment>

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        testFragmentScenario = launchFragmentInContainer(themeResId = R.style.Theme_CocktailApp)
        testFragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun navigate_to_details_fragment() {
        onView(withId(R.id.home_rv_non_alcohol))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
            )
        assertEquals(navController.currentDestination?.id, R.id.drinkDetailsFragment)
    }
}