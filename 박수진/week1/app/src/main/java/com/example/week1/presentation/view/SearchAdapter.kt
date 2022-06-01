package com.example.week1.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1.data.model.GithubRepo
import com.example.week1.databinding.SearchItemBinding

class SearchAdapter (
    private val itemClick: (GithubRepo) -> Unit
) : ListAdapter<GithubRepo, SearchAdapter.RepoViewHolder>(GithubRepoDiffUtil) {

    init {
        setHasStableIds(true)
    }

    class RepoViewHolder(
        private val binding: SearchItemBinding,
        private val itemClick: (GithubRepo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepo) {
            with(binding) {
                Glide.with(root)
                    .load(repo.owner.avatarUrl)
                    .into(repoImg)
                repoName.text = repo.name
                repoLang.text = repo.language
                root.setOnClickListener { itemClick(repo) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClick
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
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