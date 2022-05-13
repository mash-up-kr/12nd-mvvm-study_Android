package com.example.githubexample.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexample.databinding.RecyclerviewItemBinding
import com.example.githubexample.entities.GithubResult

class GithubAdapter(
    private val itemClick: (GithubResult.Item) -> Unit
) : PagingDataAdapter<GithubResult.Item, GithubAdapter.GithubViewHolder>(githubDiffUtil) {
    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        return GithubViewHolder(parent, itemClick)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GithubViewHolder(private val parent: ViewGroup, private val itemClick: (GithubResult.Item) -> Unit) : RecyclerView.ViewHolder(
        RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding: RecyclerviewItemBinding = DataBindingUtil.bind(itemView) ?: throw IllegalStateException("fail to bind")
        private lateinit var item: GithubResult.Item

        init {
            itemView.setOnClickListener {
                itemClick(item)
            }
        }

        fun bind(item: GithubResult.Item) {
            this.item = item
            binding.run {
                github = item
                executePendingBindings()
            }
        }

    }

    companion object {
        private val githubDiffUtil = object : DiffUtil.ItemCallback<GithubResult.Item>() {
            override fun areItemsTheSame(oldItem: GithubResult.Item, newItem: GithubResult.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubResult.Item, newItem: GithubResult.Item): Boolean {
                return oldItem == newItem
            }

        }
    }

}
