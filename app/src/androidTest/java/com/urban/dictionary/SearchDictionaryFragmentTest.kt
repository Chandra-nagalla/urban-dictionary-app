package com.urban.dictionary

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchDictionaryFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<SearchDictionaryFragment>()
    }


    @Test
    fun viewShouldGone() {
        onView(withId(R.id.urban_progressbar))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}