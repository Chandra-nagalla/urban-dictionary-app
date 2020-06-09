package com.urban.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urban.dictionary.model.DictionaryResponse
import com.urban.dictionary.model.SearchItem
import com.urban.dictionary.repository.DictionaryRepository
import com.urban.dictionary.service.ServiceResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchDictionaryViewModel : ViewModel() {

    private lateinit var dictionaryRepository: DictionaryRepository
    private var _readListData = MutableLiveData<List<SearchItem>>()
    val fetchList: LiveData<List<SearchItem>> = _readListData

    fun init(newsRepository: DictionaryRepository) {
        this.dictionaryRepository = newsRepository
    }

    fun startSearchByTerm(search: String) {
        viewModelScope.launch {
            delay(1000)
            when (
                val result =
                    dictionaryRepository.fetchSearchData(search)
                ) {
                is ServiceResult.Error -> {

                }
                is ServiceResult.Success -> {
                    val response = result.data as DictionaryResponse
                    _readListData.value = response.list
                }
            }
        }
    }

}