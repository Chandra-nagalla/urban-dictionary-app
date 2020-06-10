package com.urban.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.urban.dictionary.model.SearchItem


class SearchItemDetailsFragment : Fragment() {
    private lateinit var searchItemDetails: SearchItem
    private lateinit var searchWord: TextView
    private lateinit var searchDefinition: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getParcelable<SearchItem>("Item")?.apply {
                searchItemDetails = this
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seach_item_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            searchWord = findViewById(R.id.search_word)
            searchWord.text = searchItemDetails.word
            searchDefinition = findViewById(R.id.search_definition)
            searchDefinition.text = searchItemDetails.definition
        }
    }

}