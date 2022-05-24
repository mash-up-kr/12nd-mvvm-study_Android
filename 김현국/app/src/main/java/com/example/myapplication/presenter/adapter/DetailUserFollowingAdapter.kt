package com.example.myapplication.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFollowBinding
import com.example.myapplication.presenter.model.PresenterOwner
import com.example.myapplication.util.loadImage

/**
 * @author 김현국
 * @created 2022/05/03
 */
class DetailUserFollowingAdapter :
    ListAdapter<PresenterOwner, DetailUserFollowingAdapter.UserFollowingViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PresenterOwner>() {
            override fun areItemsTheSame(
                oldItem: PresenterOwner,
                newItem: PresenterOwner
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: PresenterOwner,
                newItem: PresenterOwner
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFollowingViewHolder {
        return UserFollowingViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserFollowingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserFollowingViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: PresenterOwner) = with(binding) {
            ivDetailFollowImage.loadImage(user.image)
            tvDetailFollowName.text = user.login
        }
    }
}
