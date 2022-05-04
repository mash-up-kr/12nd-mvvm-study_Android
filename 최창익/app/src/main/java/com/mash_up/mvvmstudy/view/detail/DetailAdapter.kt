package com.mash_up.mvvmstudy.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mash_up.mvvmstudy.databinding.*
import com.mash_up.mvvmstudy.repository.model.DetailFeed

class DetailAdapter :
    ListAdapter<DetailFeed, RecyclerView.ViewHolder>(detailFeedDiffCallback) {
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DetailFeed.Profile -> 0
            is DetailFeed.Description -> 1
            is DetailFeed.Language -> 2
            is DetailFeed.LastUpdated -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            0 -> ProfileViewHolder(ItemProfileBinding.inflate(inflater, parent, false))
            1 -> DescriptionViewHolder(ItemDescriptionBinding.inflate(inflater, parent, false))
            2 -> LanguageViewHolder(ItemLanguageBinding.inflate(inflater, parent, false))
            else -> LastUpdatedViewHolder(ItemLastUpdatedBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ProfileViewHolder -> holder.bind(item as DetailFeed.Profile)
            is LanguageViewHolder -> holder.bind(item as DetailFeed.Language)
            is DescriptionViewHolder -> holder.bind(item as DetailFeed.Description)
            is LastUpdatedViewHolder -> holder.bind(item as DetailFeed.LastUpdated)
        }
    }

    class ProfileViewHolder(
        private val binding: ItemProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: DetailFeed.Profile) {
            binding.profile = profile
        }
    }

    class LanguageViewHolder(
        private val binding: ItemLanguageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: DetailFeed.Language) {
            binding.language = profile
        }
    }

    class DescriptionViewHolder(
        private val binding: ItemDescriptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: DetailFeed.Description) {
            binding.description = profile
        }
    }

    class LastUpdatedViewHolder(
        private val binding: ItemLastUpdatedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: DetailFeed.LastUpdated) {
            binding.lastUpdated = profile
        }
    }

    companion object {
        //TODO: Sealed Class 간 비교는 어떻게 하지?
        private val detailFeedDiffCallback = object : DiffUtil.ItemCallback<DetailFeed>() {
            override fun areItemsTheSame(oldItem: DetailFeed, newItem: DetailFeed): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: DetailFeed, newItem: DetailFeed): Boolean =
                oldItem == newItem
        }
    }
}