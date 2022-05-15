package com.example.mvvmstudy.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmstudy.data.Repository
import com.example.mvvmstudy.databinding.ItemRepositoryBinding
import androidx.recyclerview.widget.ListAdapter

class MainAdapter(private val itemClickedListener: (Repository) -> Unit) :
    ListAdapter<Repository, MainAdapter.ViewHolder>(repositoryDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem, itemClickedListener)
    }

    class ViewHolder(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository, itemClickedListener: (Repository) -> Unit) {
            binding.repository = repository
            itemView.setOnClickListener {
                itemClickedListener(repository)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        private val repositoryDiffUtil = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }


}
