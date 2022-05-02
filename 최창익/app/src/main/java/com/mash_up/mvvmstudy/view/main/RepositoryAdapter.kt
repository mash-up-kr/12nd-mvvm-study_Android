package com.mash_up.mvvmstudy.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mash_up.mvvmstudy.databinding.ItemListBinding
import com.mash_up.mvvmstudy.repository.model.Repository

class RepositoryAdapter(val onClickItem: (Repository) -> Unit) :
    ListAdapter<Repository, RepositoryAdapter.RepositoryViewHolder>(repositoryDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.apply {
            bind(currentItem)
            binding.root.setOnClickListener {
                onClickItem(currentItem)
            }
        }
    }

    class RepositoryViewHolder(
        val binding: ItemListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.repository = repository
        }
    }

    companion object {
        private val repositoryDiffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }
}