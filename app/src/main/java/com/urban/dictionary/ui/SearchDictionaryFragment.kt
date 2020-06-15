package com.urban.dictionary.ui

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
import com.urban.dictionary.R
import com.urban.dictionary.adapter.SearchDictionaryAdapter
import com.urban.dictionary.model.SearchItem
import com.urban.dictionary.repository.DictionaryRepositoryImpl
import com.urban.dictionary.utils.SEARCH_ITEM_DETAILS
import com.urban.dictionary.utils.THUMBS_DOWN
import com.urban.dictionary.utils.THUMBS_UP

class SearchDictionaryFragment : Fragment(), TextWatcher {

    private lateinit var viewModel: SearchDictionaryViewModel
    private lateinit var searchText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchDictionaryAdapter
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
        configureViewModel()
        configureUI(view)
    }

    /**
     * view setup and configuration
     * @param view used to setup widgets
     */
    private fun configureUI(view: View) {
        view.apply {
            searchText = findViewById(R.id.urban_search_editText)
            searchText.addTextChangedListener(this@SearchDictionaryFragment)
            recyclerView = findViewById(R.id.urban_search_recycler)
            progressBar = findViewById(R.id.urban_progressbar)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = SearchDictionaryAdapter(::searchItemClick)
        }

    }

    /**
     * view model configuration and initialization with repository
     */
    private fun configureViewModel() {
        viewModel = ViewModelProvider(this).get(SearchDictionaryViewModel::class.java)
        viewModel.init(DictionaryRepositoryImpl())

        viewModel.fetchList.observe(viewLifecycleOwner, Observer {
            searchItemList = it
            viewModel.sortSearchData(searchItemList, THUMBS_UP, ::setDataToAdapter)
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
                viewModel.sortSearchData(searchItemList, THUMBS_UP, ::setDataToAdapter)
            }

            R.id.thumbs_down -> {
                viewModel.sortSearchData(searchItemList, THUMBS_DOWN, ::setDataToAdapter)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * higher order function which sorts the search item list
     * @param sortedData search item list
     */
    private fun setDataToAdapter(sortedData: List<SearchItem>) {
        adapter.setData(sortedData)
    }

    /**
     * higher order function which trigger when tap of search item in recycler view
     * @param searchItem search item of specific cell in recycler view
     */
    private fun searchItemClick(searchItem: SearchItem) {
        findNavController().navigate(
            R.id.urban_search_details_fragment,
            bundleOf(Pair(SEARCH_ITEM_DETAILS, searchItem))
        )
    }

}