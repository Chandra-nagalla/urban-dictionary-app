package com.urban.dictionary

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.urban.dictionary.model.SearchItem
import com.urban.dictionary.ui.SearchItemDetailsFragment
import com.urban.dictionary.utils.SEARCH_ITEM_DETAILS
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchItemDetailsFragmentTest {

    private lateinit var item: SearchItem

    @Before
    fun setUp() {

        item = getSearchItem()
        val bundle = Bundle()
        bundle.putParcelable(SEARCH_ITEM_DETAILS, item)
        launchFragmentInContainer<SearchItemDetailsFragment>(
            fragmentArgs = bundle,
            factory = null
        )

    }

    @Test
    fun test_searchItemDetails() {
        onView(withId(R.id.search_word)).check(matches(withText(item.word)))
        onView(withId(R.id.search_definition)).check(matches(withText(item.definition)))
    }

    private fun getSearchItem(): SearchItem {

        return SearchItem(
            "Starchylde",
            "",
            9792378,
            "Getting what you give~if you're mean, you [get bad] karma~bad things happen. If you're [kindly] & nice, you'll get good karma~[good things] will happen.",
            "When I tried to [spit on] [that bitch's] car, it didn't work & I got [bad karma]. But plenty of other times I've been cool to people & have gotten good karma.",
            "http://karma.urbanup.com/9792378",
            156,
            1223,
            "karma"
        )
    }
}

