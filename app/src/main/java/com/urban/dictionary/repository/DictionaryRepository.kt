package com.urban.dictionary.repository

import com.urban.dictionary.model.BusinessObject
import com.urban.dictionary.service.ServiceResult

interface DictionaryRepository {
    suspend fun fetchSearchData(search: String): ServiceResult<BusinessObject>
}