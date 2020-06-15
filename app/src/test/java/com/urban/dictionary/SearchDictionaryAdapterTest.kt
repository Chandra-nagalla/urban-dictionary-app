package com.urban.dictionary

import com.urban.dictionary.adapter.SearchDictionaryAdapter
import com.urban.dictionary.model.SearchItem
import io.mockk.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test


class SearchDictionaryAdapterTest {

    private val searchAdapter = spyk(
        SearchDictionaryAdapter(mockk())
    )

    @Test
    fun `getItemCount returns the size of data`() {
        val testData = listOf(getSearchItem())
        every { searchAdapter.notifyDataSetChanged() } just runs
        searchAdapter.setData(testData)
        assertThat(testData.size, `is`(searchAdapter.itemCount))
        verify { searchAdapter.notifyDataSetChanged() }
    }

    @Test
    fun `onBindViewHolder should call bind on the view holder`() {
        val testData = getSearchItem()
        val testListData = listOf(getSearchItem())
        val mockViewHolder = mockk<SearchDictionaryAdapter.SearchItemViewHolder>()
        every { mockViewHolder.bindView(any()) } just runs
        every { searchAdapter.notifyDataSetChanged() } just runs
        searchAdapter.setData(testListData)
        searchAdapter.onBindViewHolder(mockViewHolder, 0)
        verify { mockViewHolder.bindView(testData) }
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

