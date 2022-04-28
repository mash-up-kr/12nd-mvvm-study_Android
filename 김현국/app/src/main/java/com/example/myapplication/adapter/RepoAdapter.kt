package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemSearchBinding
import com.example.myapplication.model.Repository
import com.example.myapplication.presenter.search.OnItemClick
import com.example.myapplication.presenter.search.SearchAdapterContract

/**
 * @author 김현국
 * @created 2022/04/25
 */
class RepoAdapter() :
    ListAdapter<Repository, RepoAdapter.RepoViewHolder>(diffUtil),
    SearchAdapterContract.View,
    SearchAdapterContract.Model {

    lateinit var onItemClick: OnItemClick

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {

        return RepoViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(private val binding: ItemSearchBinding, itemClick: OnItemClick) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            this.itemView.setOnClickListener {
                itemClick.onItemClick(layoutPosition)
            }
        }

        fun bind(repo: Repository) {
            binding.tvMainName.text = repo.name
            if (repo.language != null) {
                binding.tvMainLanguage.text = repo.language
            }
            Glide.with(binding.ivMainImage)
                .load(repo.owner.image).into(binding.ivMainImage)
        }
    }

    override fun setOnClickListener(clickListener: OnItemClick) {
        onItemClick = clickListener
    }

    override fun setData(repos: List<Repository>) {
        submitList(repos.toMutableList())
    }
}
