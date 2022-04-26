package com.mash_up.mvvmstudy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mash_up.mvvmstudy.databinding.ItemListBinding
import com.mash_up.mvvmstudy.model.MainUiModel

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<MainUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int =
        items.size

    fun submitList(items: List<MainUiModel>) {
        this.items.addAll(items)
    }

    class MainViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainUiModel) {
            binding.data = item
        }
    }
}