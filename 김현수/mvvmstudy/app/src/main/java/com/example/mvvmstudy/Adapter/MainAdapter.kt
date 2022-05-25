package com.example.mvvmstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmstudy.data.Repository
import com.example.mvvmstudy.databinding.ItemRepositoryBinding

class MainAdapter(private val itemClickedListener: (Repository) -> Unit) :
    ListAdapter<Repository, MainAdapter.ViewHolder>(repositoryDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickedListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }

    class ViewHolder(
        private val binding: ItemRepositoryBinding,
        private val itemClickedListener: (Repository) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                binding.githubRepository?.run {
                    itemClickedListener(this)
                }
            }
        }

        fun bind(repository: Repository) {
            binding.githubRepository = repository
            binding.executePendingBindings()
        }

    }

    companion object {
        private val repositoryDiffUtil = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }

}
