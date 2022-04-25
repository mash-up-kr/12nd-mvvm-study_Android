package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    RecyclerView.Adapter<RepoAdapter.ViewHolder>(),
    SearchAdapterContract.View,
    SearchAdapterContract.Model {

    private var repos: List<Repository>? = emptyList()
    private var onItemClick: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repos!![position])
        holder.itemView.setOnClickListener {
            onItemClick!!.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return repos!!.size
    }

    class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repository) {
            binding.tvMainName.text = repo.name
            if (repo.language != null) {
                binding.tvMainLanguage.text = repo.language
            }
            Glide.with(binding.ivMainImage)
                .load(repo.owner.image).into(binding.ivMainImage)
        }
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun setOnClickListener(clickListener: OnItemClick) {
        onItemClick = clickListener
    }

    override fun setData(repos: List<Repository>) {
        this.repos = repos
    }
}
