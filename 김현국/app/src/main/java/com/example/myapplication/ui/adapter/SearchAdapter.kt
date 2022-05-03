package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemSearchBinding
import com.example.myapplication.ui.model.PresenterRepository

/**
 * @author 김현국
 * @created 2022/04/25
 */
class SearchAdapter(
    onItemClick: OnItemClick
) :
    ListAdapter<PresenterRepository, SearchAdapter.RepoViewHolder>(diffUtil) {

    interface OnItemClick {
        fun onItemClick(repository: PresenterRepository)
    }
    var onItemClick: OnItemClick = onItemClick

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PresenterRepository>() {
            override fun areItemsTheSame(
                oldItem: PresenterRepository,
                newItem: PresenterRepository
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PresenterRepository,
                newItem: PresenterRepository
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {

        return RepoViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(getItem(position))
        }
    }

    class RepoViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: PresenterRepository) {
            binding.tvMainName.text = repo.name
            if (repo.language != null) {
                binding.tvMainLanguage.text = repo.language
            }
            Glide.with(binding.ivMainImage)
                .load(repo.owner.image).into(binding.ivMainImage)
        }
    }
}
