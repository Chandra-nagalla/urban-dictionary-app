package com.urban.dictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.urban.dictionary.model.BusinessObject
import com.urban.dictionary.model.DictionaryResponse
import com.urban.dictionary.repository.DictionaryRepository
import com.urban.dictionary.service.ServiceResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class SearchDictionaryViewModelTest {
    private lateinit var viewModel: SearchDictionaryViewModel
    private val dictionaryRepository = mockk<DictionaryRepository>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        coEvery { dictionaryRepository.fetchSearchData(any()) } returns (mockSearchResult() as ServiceResult<BusinessObject>)
        viewModel = SearchDictionaryViewModel()
        viewModel.init(dictionaryRepository)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should make service call if term has some value`() =
        runBlockingTest {
            viewModel.apply {
                startSearchByTerm("chandra")
                coVerify { dictionaryRepository.fetchSearchData(any()) }
            }
        }

    @Test
    fun `live data should be empty when empty string passed`() {
        val emptyString = ""
        viewModel.startSearchByTerm(emptyString)
        Assert.assertEquals(emptyList<DictionaryResponse>(), viewModel.fetchList.value)
    }

    private fun mockSearchResult(): ServiceResult<DictionaryResponse> {
        val result = Gson().fromJson(SEARCH_TERM_RESPONSE, DictionaryResponse::class.java)
        return ServiceResult.Success(result)
    }

    companion object {
        const val SEARCH_TERM_RESPONSE =
            "{\"list\":[{\"definition\":\"The only [proper] [response] to something that [makes absolutely no sense]\",\"permalink\":\"http://wat.urbanup.com/3322419\",\"thumbs_up\":3672,\"sound_urls\":[],\"author\":\"watwat\",\"word\":\"wat\",\"defid\":3322419,\"current_vote\":\"\",\"written_on\":\"2008-09-04T00:00:00.000Z\",\"example\":\"1: If all the animals on the [equator] were capable of [flattery], Halloween and Easter would fall on the same day. 2: wat 1: Wow your cock is almost as big as my dad's. 2: wat 1: I accidentially a whole [coke bottle] 2: You accidentially what? 1: A whole coke bottle 2: wat\",\"thumbs_down\":424}]}"
    }
}


