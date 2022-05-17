package com.mashup.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.mvvm.data.model.Repository
import com.mashup.mvvm.databinding.ItemRepositoryBinding
import com.mashup.mvvm.extensions.loadImage

class RepositoryAdapter(
    private val onClickRepositoryListener: (Repository?) -> Unit
) : ListAdapter<Repository, RepositoryViewHolder>(repositoryDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = RepositoryViewHolder(
        ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onClickRepositoryListener
    )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBindRepository(getItem(position))
    }

    companion object {
        private val repositoryDiffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(
                oldItem: Repository, newItem: Repository
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Repository, newItem: Repository
            ) = oldItem == newItem
        }
    }
}

class RepositoryViewHolder(
    private val viewBinding: ItemRepositoryBinding,
    private val onClickRepositoryListener: (Repository?) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var repositoryItem: Repository? = null

    init {
        itemView.setOnClickListener {
            onClickRepositoryListener(repositoryItem)
        }
    }

    fun onBindRepository(repository: Repository) = viewBinding.apply {
        repositoryItem = repository
        imgProfile.loadImage(repository.owner.avatarUrl)
        tvRepositoryName.text = repository.name
        tvRepositoryLanguage.text = repository.language
    }
}