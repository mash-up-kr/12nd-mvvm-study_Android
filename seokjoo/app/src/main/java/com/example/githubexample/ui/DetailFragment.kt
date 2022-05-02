package com.example.githubexample.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.githubexample.R
import com.example.githubexample.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
        binding.github = safeArgs.github
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        _binding = null
    }
}
