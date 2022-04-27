package com.joocoding.android.app.githubsearch


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joocoding.android.app.githubsearch.databinding.ItemRepositoryBinding
import com.joocoding.android.app.githubsearch.model.response.RepositoryResponse

class MainAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<RepositoryResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainViewHolder)
            holder.bind(datas[position])

    }


}

class MainViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: RepositoryResponse) {
        binding.tvRvName.text = item.name
        binding.tvRvDescription.text = item.language
        Glide.with(itemView).load(item.owner.avatarUrl).into(binding.imgRvPhoto)

    }

}