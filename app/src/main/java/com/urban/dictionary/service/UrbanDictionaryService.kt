package com.urban.dictionary.service

import com.urban.dictionary.model.DictionaryResponse
import com.urban.dictionary.utils.API_HEADER
import com.urban.dictionary.utils.API_KEY
import com.urban.dictionary.utils.API_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UrbanDictionaryService {

    @GET("define")
    @Headers(
        API_HEADER,
        API_KEY
    )
    suspend fun getSearchByTerm(@Query(API_QUERY) term: String): Response<DictionaryResponse>
}