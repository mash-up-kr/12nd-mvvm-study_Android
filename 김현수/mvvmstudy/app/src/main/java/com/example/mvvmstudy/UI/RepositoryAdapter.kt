package com.example.mvvmstudy.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.Repository

class RepositoryAdapter(private val context: Context) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>(){
    private val repos = mutableListOf<Repository?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_repository,parent,false)
        return ViewHolder(view)
    }

    fun setRepository(repo : List<Repository>){
        repos.apply{
            clear()
            addAll(repo)
        }
        notifyDataSetChanged()
    }
    fun addRepository(repo : List<Repository>){
        repos.addAll(repo)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        repos[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = repos?.size

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        private val view : View = v
        private val img = view.findViewById<ImageView>(R.id.repo_img)
        private val language = view.findViewById<TextView>(R.id.repo_language)
        private val name = view.findViewById<TextView>(R.id.repo_repo)

        fun bind(item:Repository){
//            val pos = adapterPosition
            Glide.with(img).load(item.owner.avatar_Url).into(img)
            language.text = item.language
            name.text = item.name
        }
    }

}