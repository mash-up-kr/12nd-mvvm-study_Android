package com.test.mvvmstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvmstudy.data.SearchResultDetail
import com.test.mvvmstudy.databinding.ItemSearchListBinding

class SearchResultAdapter :
    ListAdapter<SearchResultDetail, SearchResultAdapter.SearchViewHolder>(diffUtil) {

    lateinit var clickListener : (SearchResultDetail)->Unit

    class SearchViewHolder(private val binding: ItemSearchListBinding, private val clickListener : (SearchResultDetail)->Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var resultItem: SearchResultDetail

        init {
            itemView.setOnClickListener {
                clickListener(resultItem)
            }
        }

        fun bind(item: SearchResultDetail) {
            resultItem = item
            binding.searchData = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SearchResultDetail>() {
            override fun areItemsTheSame(oldItem: SearchResultDetail, newItem: SearchResultDetail): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchResultDetail, newItem: SearchResultDetail): Boolean {
                return oldItem == newItem
            }
        }
    }
}