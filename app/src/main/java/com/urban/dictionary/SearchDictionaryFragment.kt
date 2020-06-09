package com.urban.dictionary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urban.dictionary.adapter.SearchAdapter
import com.urban.dictionary.repository.DictionaryRepositoryImpl

class SearchDictionaryFragment : Fragment(), TextWatcher {

    private lateinit var viewModel: SearchDictionaryViewModel
    private lateinit var searchText: EditText
    private lateinit var recyclerView: RecyclerView

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
        searchText = view.findViewById(R.id.urban_search_editText)
        searchText.addTextChangedListener(this)
        recyclerView = view.findViewById(R.id.urban_search_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.fetchList.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = SearchAdapter(it)
        })
    }

    override fun afterTextChanged(editable: Editable?) {
        viewModel.startSearchByTerm(editable.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}