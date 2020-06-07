package com.urban.dictionary.repository

import com.urban.dictionary.model.BusinessObject
import com.urban.dictionary.model.DictionaryResponse
import com.urban.dictionary.service.RetrofitCallHandler
import com.urban.dictionary.service.RetrofitService
import com.urban.dictionary.service.ServiceResult
import com.urban.dictionary.service.UrbanDictionaryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DictionaryRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val newsService: UrbanDictionaryService = RetrofitService.createService(
        UrbanDictionaryService::class.java
    )
) : DictionaryRepository {

    override suspend fun fetchSearchData(search: String): ServiceResult<BusinessObject> {
        val result = withContext(ioDispatcher) {
            RetrofitCallHandler.processCall {
                newsService.getSearchByTerm(search)
            }
        }

        return when (result) {
            is ServiceResult.Success -> result
            is ServiceResult.Error -> result
        }
    }

    internal fun transformResponseToCheckInTravelPlanObject(response: DictionaryResponse): ServiceResult<BusinessObject> {
        response.apply {

            response.list.let {
                //return Result.Success(it)
            }

            return ServiceResult.Error(Exception())
        }
    }

}