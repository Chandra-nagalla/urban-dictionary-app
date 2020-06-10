package com.urban.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.urban.dictionary.R
import com.urban.dictionary.model.SearchItem

class SearchAdapter(
    private val searchItemClick: (searchItem: SearchItem) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var dataList = listOf<SearchItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }


    fun setData(data: List<SearchItem>) {
        this.dataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var cardView = view.findViewById<CardView>(R.id.card_view)
        private val wordText = view.findViewById<TextView>(R.id.item_word_text)
        private val definitionText = view.findViewById<TextView>(R.id.item_definition_text)
        private val thumbsUpText = view.findViewById<TextView>(R.id.item_thumbsUp_text)
        private val thumbsDownText = view.findViewById<TextView>(R.id.item_thumbsDown_text)

        fun bindView(item: SearchItem) {
            wordText.text = item.word
            definitionText.text = item.definition
            thumbsUpText.text = item.thumbs_up.toString()
            thumbsDownText.text = item.thumbs_down.toString()

            cardView.setOnClickListener {
                searchItemClick(item)
            }
        }


    }

}