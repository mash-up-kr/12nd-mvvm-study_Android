package com.github.sookhee.mvvmstudy.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sookhee.mvvmstudy.databinding.ItemGithubRepositoryBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel

/**
 *  RepositoryAdapter.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class RepositoryAdapter :
    ListAdapter<GithubRepositoryModel, RepositoryAdapter.RepositoryViewHolder>(diffUtil) {
    var onItemClick: ((GithubRepositoryModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return RepositoryViewHolder(
            ItemGithubRepositoryBinding.inflate(inflater, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class RepositoryViewHolder(
        private val binding: ItemGithubRepositoryBinding,
        private val onItemClick: ((GithubRepositoryModel) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepositoryModel) {
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

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<GithubRepositoryModel>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryModel,
                newItem: GithubRepositoryModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubRepositoryModel,
                newItem: GithubRepositoryModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
