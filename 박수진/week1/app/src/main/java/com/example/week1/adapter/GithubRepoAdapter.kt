package com.example.week1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1.databinding.RepoItemBinding
import com.example.week1.model.GithubRepo

class GithubRepoAdapter(
    private var repoList: List<GithubRepo>
    ): RecyclerView.Adapter<GithubRepoAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(private val binding: RepoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepo) {
            Glide.with(binding.root)
                .load(repo.owner.avatar_url)
                .into(binding.repoImg)
            binding.repoName.text = repo.name
            binding.repoLang.text = repo.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }
}