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
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun init(newsRepository: DictionaryRepository) {
        this.dictionaryRepository = newsRepository
    }

    fun startSearchByTerm(search: String) {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = true
            when (
                val result =
                    dictionaryRepository.fetchSearchData(search)
                ) {
                is ServiceResult.Error -> {
                    _isLoading.value = false
                }
                is ServiceResult.Success -> {
                    _isLoading.value = false
                    val response = result.data as DictionaryResponse
                    _readListData.value = response.list
                }
            }
        }
    }

}