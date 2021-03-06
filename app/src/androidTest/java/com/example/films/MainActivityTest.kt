package com.example.films

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.films.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListFragmentVisible() {
        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_writeToCharAndClick() {

        val rndString = "abcdefgh"
        var sb = StringBuilder()

        onView(withId(R.id.searchview)).perform(click())

        onView(withId(R.id.movie_recycler_view)).perform()

        onView(withId(R.id.fmovie_title)).check(matches(isDisplayed()))

    }
}