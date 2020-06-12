package com.urban.dictionary.repository

import com.urban.dictionary.model.BusinessObject
import com.urban.dictionary.model.DictionaryResponse
import com.urban.dictionary.service.RetrofitCallHandler
import com.urban.dictionary.service.RetrofitService
import com.urban.dictionary.service.ServiceResult
import com.urban.dictionary.service.UrbanDictionaryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DictionaryRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val newsService: UrbanDictionaryService = RetrofitService.createService(
        UrbanDictionaryService::class.java
    )
) : DictionaryRepository {

    override suspend fun fetchSearchData(search: String): ServiceResult<BusinessObject> {
        val result = withContext(ioDispatcher) {
            delay(1000)
            RetrofitCallHandler.processCall {
                newsService.getSearchByTerm(search)
            }
        }

        return when (result) {
            is ServiceResult.Success -> transformResponseToCheckInTravelPlanObject(result.data)
            is ServiceResult.Error -> result
        }
    }

    private fun transformResponseToCheckInTravelPlanObject(response: DictionaryResponse): ServiceResult<BusinessObject> {
        response.apply {

            response.let {
                return ServiceResult.Success(it)
            }

            return ServiceResult.Error(Exception())
        }
    }

}