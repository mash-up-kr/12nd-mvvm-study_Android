package com.example.myapplication.ui.view.detail

import android.os.Bundle
import android.view.View
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

    private fun initUI() = with(binding) {
        detailUserFollowingAdapter = DetailUserFollowingAdapter()
        detailUserFollowerAdapter = DetailUserFollowerAdapter()

        Glide.with(ivDetailImage).load(args.image).into(ivDetailImage)
        tvDetailRepoName.text = args.name
        tvDetailStars.text =
            StringBuilder().append("\u2605").append(args.stars).append(" stars").toString()
        tvDetailDescriptionSmall.text = args.description
        tvDetailLanguageSmall.text = args.language
        tvDetailLastUpdateSmall.text = args.lastUpdate
        tvDetailUserName.text = args.login

        rvDetailUserFollower.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDetailUserFollower.adapter = detailUserFollowerAdapter

        rvDetailUserFollowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDetailUserFollowing.adapter = detailUserFollowingAdapter

        model.getUserFollowing(args.login)
        model.getUserFollower(args.login)
    }

    private fun initObserve() = with(model) {
        userFollowerList.observe(viewLifecycleOwner, userFollowerListObserver)
        userFollowingList.observe(viewLifecycleOwner, userFollowingListObserver)
    }

    private val userFollowerListObserver = Observer<Results<List<PresenterOwner>>> { result ->
        when (result) {
            is Results.Success -> {
                detailUserFollowerAdapter.submitList(result.value)
            }
            is Results.Failure -> {
                showToast(result.message ?: "예상치 못한 오류 발생")
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
                showToast(result.message ?: "예상치 못한 오류 발생")
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
