package com.rizalfahlepi.moviecatalogue.ui.activity.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.rizalfahlepi.moviecatalogue.R
import com.rizalfahlepi.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.textViewTitleValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTitleValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewIDValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewIDValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewLanguageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewLanguageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDateValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewPopularityValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewPopularityValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewOverviewValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverviewValue)).check(matches(not(withText(""))))
    }

    @Test
    fun loadTVSeries() {
        onView(withText("TV Series")).perform(click())
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.textViewTitleValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTitleValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewIDValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewIDValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewLanguageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewLanguageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDateValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewPopularityValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewPopularityValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewOverviewValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverviewValue)).check(matches(not(withText(""))))
    }

    @Test
    fun loadFavoriteMovies() {
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorites)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.action_favorite_list)).perform(click())
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.textViewTitleValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTitleValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewIDValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewIDValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewLanguageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewLanguageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDateValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewPopularityValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewPopularityValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewOverviewValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverviewValue)).check(matches(not(withText(""))))
        onView(withId(R.id.action_favorites)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(allOf(withId(R.id.textViewEmpty), isDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTVSeries() {
        onView(withText("TV Series")).perform(click())
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorites)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.action_favorite_list)).perform(click())
        onView(withText("TV Series")).perform(click())
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recyclerViewMovies), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.textViewTitleValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTitleValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewIDValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewIDValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteAverageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewVoteCountValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewLanguageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewLanguageValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDateValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewPopularityValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewPopularityValue)).check(matches(not(withText(""))))
        onView(withId(R.id.textViewOverviewValue)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverviewValue)).check(matches(not(withText(""))))
        onView(withId(R.id.action_favorites)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(allOf(withId(R.id.textViewEmpty), isDisplayed())).check(matches(isDisplayed()))
    }
}