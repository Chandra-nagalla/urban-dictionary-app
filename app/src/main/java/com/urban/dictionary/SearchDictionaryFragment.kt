package com.urban.dictionary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urban.dictionary.adapter.SearchAdapter
import com.urban.dictionary.model.SearchItem
import com.urban.dictionary.repository.DictionaryRepositoryImpl

class SearchDictionaryFragment : Fragment(), TextWatcher {

    private lateinit var viewModel: SearchDictionaryViewModel
    private lateinit var searchText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var searchItemList: List<SearchItem>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        progressBar = view.findViewById(R.id.urban_progressbar)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(::searchItemClick)
        viewModel.fetchList.observe(viewLifecycleOwner, Observer {
            searchItemList = it
            sortSearchData(THUMBS_UP)
            recyclerView.adapter = adapter
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                false -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun afterTextChanged(editable: Editable?) {
        viewModel.startSearchByTerm(editable.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.thumbs_up -> {
                sortSearchData(THUMBS_UP)
            }

            R.id.thumbs_down -> {
                sortSearchData(THUMBS_DOWN)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private fun sortSearchData(sortedBy: Int) {
        var data = searchItemList
        when (sortedBy) {
            THUMBS_DOWN -> {
                data = searchItemList.sortedByDescending { it.thumbs_down }
            }
            THUMBS_UP -> {
                data = searchItemList.sortedByDescending { it.thumbs_up }
            }
        }

        adapter.setData(data)
    }

    private fun searchItemClick(searchItem: SearchItem) {
        findNavController().navigate(
            R.id.urban_search_details_fragment,
            bundleOf(Pair("Item", searchItem))
        )
    }


    companion object {
        const val THUMBS_UP = 0
        const val THUMBS_DOWN = 1
    }

}