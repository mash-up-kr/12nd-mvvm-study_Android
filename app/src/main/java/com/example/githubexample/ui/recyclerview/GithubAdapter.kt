package com.example.githubexample.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubexample.databinding.RecyclerviewItemBinding
import com.example.githubexample.entities.GithubResult

class GithubAdapter : ListAdapter<GithubResult.Item, GithubAdapter.GithubViewHolder>(githubDiffUtil) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        return GithubViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    class GithubViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(
        RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {
        private val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.bind(itemView)

        fun bind(item: GithubResult.Item) {
            binding.tvRepositoryName.text = item.name
            binding.tvRepositoryLanguage.text = item.language
            Glide.with(itemView.context)
                .load(item.owner.avatarUrl)
                .into(binding.ivGithubImage)
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
