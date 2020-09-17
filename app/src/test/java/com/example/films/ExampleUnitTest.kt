package com.example.films

import android.app.Application
import com.example.films.viewmodel.MovieDataViewModel
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    //val mdvm = MovieDataViewModel(Mockito.mock(Application::class.java))

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_filterReturnMultipleElement() {


    }

    @Test
    fun test_filterReturnOneElement() {
        //mdvm.filter("any")

    }

    @Test
    fun test_filterEmpty() {


    }
}