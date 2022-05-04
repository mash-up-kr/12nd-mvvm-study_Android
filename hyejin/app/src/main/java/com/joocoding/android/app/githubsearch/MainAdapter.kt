package com.joocoding.android.app.githubsearch


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joocoding.android.app.githubsearch.databinding.ItemRepositoryBinding
import com.joocoding.android.app.githubsearch.model.response.Repository

class MainAdapter(
    private var datas: List<Repository> = emptyList(), private val clickEvent: (Repository) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            clickEvent
        )
    }

    fun setItem(newItem: List<Repository>) {
        datas = newItem
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(datas[position])

    }


}

class MainViewHolder(
    private val binding: ItemRepositoryBinding,
    private val clickEvent: (Repository) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Repository) {
        binding.tvRvName.text = item.name
        binding.tvRvDescription.text = item.language
        Glide.with(itemView).load(item.owner.avatarUrl).into(binding.imgRvPhoto)
        binding.root.setOnClickListener { clickEvent(item) }

    }

}