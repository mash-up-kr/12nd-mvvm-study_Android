package com.mash_up.mvvmstudy.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mash_up.mvvmstudy.databinding.ItemListBinding
import com.mash_up.mvvmstudy.repository.model.Repository

class MainAdapter(val onClickItem: (Repository) -> Unit) :
    ListAdapter<Repository, MainAdapter.RepositoryViewHolder>(repositoryDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem
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
        val binding: ItemListBinding,
        val onClickItem: (Repository) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var repository: Repository

        init {
            binding.root.setOnClickListener {
                onClickItem(repository)
            }
        }

        fun bind(repository: Repository) {
            this.repository = repository
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