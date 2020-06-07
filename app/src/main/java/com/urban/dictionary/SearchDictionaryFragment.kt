package com.urban.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.urban.dictionary.repository.DictionaryRepositoryImpl

class SearchDictionaryFragment : Fragment() {

    private lateinit var viewModel: SearchDictionaryViewModel
    private lateinit var clickMe: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_dictionary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchDictionaryViewModel::class.java)
        viewModel.init(DictionaryRepositoryImpl())
        clickMe = view.findViewById(R.id.click_me)
        clickMe.setOnClickListener {
            viewModel.startSearchByTerm("karma")
        }
    }

}