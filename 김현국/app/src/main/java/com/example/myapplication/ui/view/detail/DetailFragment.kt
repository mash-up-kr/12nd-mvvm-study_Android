package com.example.myapplication.ui.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.repository.DetailUserRepository
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.ui.Results
import com.example.myapplication.ui.adapter.DetailUserFollowerAdapter
import com.example.myapplication.ui.adapter.DetailUserFollowingAdapter
import com.example.myapplication.ui.model.PresenterOwner

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var detailUserFollowingAdapter: DetailUserFollowingAdapter
    private lateinit var detailUserFollowerAdapter: DetailUserFollowerAdapter
    private lateinit var model: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailUserRepository = DetailUserRepository()
        model = DetailViewModel(detailUserRepository = detailUserRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initObserve()
        initUI()
    }

    private fun initUI() {
        detailUserFollowingAdapter = DetailUserFollowingAdapter()
        detailUserFollowerAdapter = DetailUserFollowerAdapter()

        Glide.with(binding.ivDetailImage).load(args.image).into(binding.ivDetailImage)
        binding.tvDetailRepoName.text = args.name
        binding.tvDetailStars.text =
            StringBuilder().append("\u2605").append(args.stars).append(" stars").toString()
        binding.tvDetailDescriptionSmall.text = args.description
        binding.tvDetailLanguageSmall.text = args.language
        binding.tvDetailLastUpdateSmall.text = args.lastUpdate
        binding.tvDetailUserName.text = args.login

        binding.rvDetailUserFollower.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDetailUserFollower.adapter = detailUserFollowerAdapter

        binding.rvDetailUserFollowing.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDetailUserFollowing.adapter = detailUserFollowingAdapter

        model.getUserFollowing(args.login)
        model.getUserFollower(args.login)
    }

    private fun initObserve() {
        model.userFollowerList.observe(viewLifecycleOwner, userFollowerListObserver)
        model.userFollowingList.observe(viewLifecycleOwner, userFollowingListObserver)
    }

    private val userFollowerListObserver = Observer<Results<List<PresenterOwner>>> { result ->
        when (result) {
            is Results.Success -> {
                detailUserFollowerAdapter.submitList(result.value)
            }
            is Results.Failure -> {
                Toast.makeText(this.requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
            is Results.Loading -> {
            }
        }
    }

    private val userFollowingListObserver = Observer<Results<List<PresenterOwner>>> { result ->
        when (result) {
            is Results.Success -> {
                detailUserFollowingAdapter.submitList(result.value)
            }
            is Results.Failure -> {
                if (result.message != null)
                    showToast(result.message)
            }
            is Results.Loading -> {
            }
        }
    }

    private fun setListener() {
        binding.ivDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
