package com.github.sookhee.mvvmstudy.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sookhee.mvvmstudy.databinding.ItemGithubRepositoryBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel

/**
 *  MainAdapter.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainAdapter : RecyclerView.Adapter<MainAdapter.RepositoryViewHolder>() {
    private var githubRepositoryList: List<GithubRepositoryModel> = listOf()
    var onItemClick: ((GithubRepositoryModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return RepositoryViewHolder(
            ItemGithubRepositoryBinding.inflate(inflater, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(githubRepositoryList[position])
    }

    override fun getItemCount(): Int = githubRepositoryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<GithubRepositoryModel>) {
        githubRepositoryList = newItems
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(
        private val binding: ItemGithubRepositoryBinding,
        private val onItemClick: ((GithubRepositoryModel) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: GithubRepositoryModel) {
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }

            binding.repositoryName.text = item.name
            binding.repositoryLanguage.text = item.language

            Glide.with(binding.root)
                .load(item.profileImage)
                .into(binding.ownerProfileImage)
        }
    }
}
