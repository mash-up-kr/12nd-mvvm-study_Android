package com.example.week1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1.databinding.RepoItemBinding
import com.example.week1.model.GithubRepo

class GithubRepoAdapter :
    ListAdapter<GithubRepo, GithubRepoAdapter.RepoViewHolder>(GithubRepoDiffUtil) {

    init {
        setHasStableIds(true)
    }

    class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepo) {
            with(binding) {
                Glide.with(root)
                    .load(repo.owner.avatar_url)
                    .into(repoImg)
                repoName.text = repo.name
                repoLang.text = repo.language
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    private companion object GithubRepoDiffUtil : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }
    }

}