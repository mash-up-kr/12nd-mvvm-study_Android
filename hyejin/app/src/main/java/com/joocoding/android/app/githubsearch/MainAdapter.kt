package com.joocoding.android.app.githubsearch


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joocoding.android.app.githubsearch.databinding.ItemRepositoryBinding
import com.joocoding.android.app.githubsearch.model.response.Repository

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    //외부 데이터에 대한 캡슐화가 안되어있다.
    var datas = mutableListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(datas[position])

    }


}

class MainViewHolder(private val binding: ItemRepositoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Repository) {
        binding.tvRvName.text = item.name
        binding.tvRvDescription.text = item.language
        Glide.with(itemView).load(item.owner.avatarUrl).into(binding.imgRvPhoto)

    }

}