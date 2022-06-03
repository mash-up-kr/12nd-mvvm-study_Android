package com.example.myapplication.presenter.view.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.adapter.DetailUserFollowerAdapter
import com.example.myapplication.presenter.adapter.DetailUserFollowingAdapter
import com.example.myapplication.util.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args by navArgs<DetailFragmentArgs>()
    private val model: DetailViewModel by viewModels()
    private val detailUserFollowingAdapter by lazy { DetailUserFollowingAdapter() }
    private val detailUserFollowerAdapter by lazy { DetailUserFollowerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initObserve()
        initUI()
    }

    private fun initUI() = with(binding) {
        rvDetailUserFollower.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDetailUserFollower.adapter = detailUserFollowerAdapter

        rvDetailUserFollowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDetailUserFollowing.adapter = detailUserFollowingAdapter

        with(args.presenterRepository) {
            ivDetailImage.loadImage(owner.image)
            tvDetailRepoName.text = name
            tvDetailStars.text =
                StringBuilder().append("\u2605").append(stars).append(" stars").toString()
            tvDetailDescriptionSmall.text = description
            tvDetailLanguageSmall.text = language
            tvDetailLastUpdateSmall.text = lastUpdated
            tvDetailUserName.text = owner.login

            model.getUserFollow(owner.login)
        }
    }

    private fun initObserve() = with(viewLifecycleOwner.lifecycleScope) {
        with(model) {
            launch {
                viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    userFollowingList.collect { state ->
                        when (state) {
                            is UiState.Success -> {
                                detailUserFollowingAdapter.submitList(state.data)
                            }
                            is UiState.Loading -> {
                            }
                            is UiState.Error -> {
                                showToast(state.error)
                            }
                            is UiState.Empty -> {
                            }
                        }
                    }
                }
            }

            launch {
                viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    userFollowerList.collect { state ->
                        when (state) {
                            is UiState.Success -> {
                                detailUserFollowerAdapter.submitList(state.data)
                            }
                            is UiState.Loading -> {
                            }
                            is UiState.Error -> {
                                showToast(state.error)
                            }
                            is UiState.Empty -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setListener() = with(binding) {
        ivDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
