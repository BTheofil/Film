package com.example.films

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.films.view.MainActivity
import com.example.films.view.MovieRecyclerAdapter
import com.example.films.viewmodel.MovieDataViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.StringBuilder
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isListFragmentVisible() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_writeToCharAndClick() {

        val rndString = "abcdefgh"
        var sb: StringBuilder
        sb.append(rndString.toCharArray(0, Random.nextInt(rndString.length)))

        val colors = arrayOf("red", "blue", "yellow")
        val randomColor = colors.random()

        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText(randomColor))

        onView(withId(R.id.recycler_view)).perform()

        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))

    }
}