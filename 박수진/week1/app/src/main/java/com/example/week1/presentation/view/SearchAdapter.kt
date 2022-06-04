package com.example.week1.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.data.model.GithubRepo
import com.example.week1.databinding.SearchItemBinding

class SearchAdapter (
    private val itemClick: (GithubRepo) -> Unit
) : ListAdapter<GithubRepo, SearchAdapter.SearchViewHolder>(GithubRepoDiffUtil) {

    init {
        setHasStableIds(true)
    }

    class SearchViewHolder(
        private val binding: SearchItemBinding,
        private val itemClick: (GithubRepo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var repo: GithubRepo

        init {
            itemView.setOnClickListener { itemClick(repo) }
        }

        fun bind(repo: GithubRepo) {
            this.repo = repo
            binding.repo = repo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClick
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    private companion object GithubRepoDiffUtil : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }
    }

}