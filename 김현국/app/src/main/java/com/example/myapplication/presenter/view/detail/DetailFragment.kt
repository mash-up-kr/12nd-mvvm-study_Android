package com.example.myapplication.presenter.view.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.servicelocator.RepositoryServiceLocator
import com.example.myapplication.servicelocator.UseCaseServiceLocator
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.adapter.DetailUserFollowerAdapter
import com.example.myapplication.presenter.adapter.DetailUserFollowingAdapter
import com.example.myapplication.util.loadImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args by navArgs<DetailFragmentArgs>()
    private val repo by lazy { args.presenterRepository }
    private val detailUserFollowingAdapter: DetailUserFollowingAdapter by lazy { DetailUserFollowingAdapter() }
    private val detailUserFollowerAdapter: DetailUserFollowerAdapter by lazy { DetailUserFollowerAdapter() }
    private val detailUserRepository by lazy { RepositoryServiceLocator.provideDetailUserRepository() }
    private val model: DetailViewModel by viewModels {
        DetailViewModelFactory(
            this,
            UseCaseServiceLocator.provideGetUserFollowerUseCase(detailUserRepository = detailUserRepository),
            UseCaseServiceLocator.provideGetUserFollowingUseCase(detailUserRepository = detailUserRepository)
        )
    }

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

        ivDetailImage.loadImage(repo.owner.image)
        tvDetailRepoName.text = repo.name
        tvDetailStars.text =
            StringBuilder().append("\u2605").append(repo.stars).append(" stars").toString()
        tvDetailDescriptionSmall.text = repo.description
        tvDetailLanguageSmall.text = repo.language
        tvDetailLastUpdateSmall.text = repo.lastUpdated
        tvDetailUserName.text = repo.owner.login

        with(model) {
            if (!savedStateHandle.contains(USER_NAME)) {
                getUserFollowing(repo.owner.login)
                getUserFollower(repo.owner.login)
            }
        }
    }

    private fun initObserve() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
            with(model) {
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
                    }
                }
            }
        }
        launch {
            with(model) {
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
