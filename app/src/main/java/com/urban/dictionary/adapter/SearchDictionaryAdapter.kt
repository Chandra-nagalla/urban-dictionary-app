package com.urban.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.urban.dictionary.R
import com.urban.dictionary.model.SearchItem

class SearchDictionaryAdapter(
    private val searchItemClick: (searchItem: SearchItem) -> Unit
) :
    RecyclerView.Adapter<SearchDictionaryAdapter.SearchItemViewHolder>() {
    private var dataList = listOf<SearchItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item_details, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }


    fun setData(data: List<SearchItem>) {
        this.dataList = data
        notifyDataSetChanged()
    }

    inner class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val cardView: CardView
        private val wordText: TextView
        private val definitionText: TextView
        private val thumbsUpText: TextView
        private val thumbsDownText: TextView

        init {
            view.apply {
                cardView = findViewById(R.id.card_view)
                wordText = findViewById<TextView>(R.id.item_word_text)
                definitionText = findViewById<TextView>(R.id.item_definition_text)
                thumbsUpText = findViewById<TextView>(R.id.item_thumbsUp_text)
                thumbsDownText = findViewById<TextView>(R.id.item_thumbsDown_text)
            }
        }

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
