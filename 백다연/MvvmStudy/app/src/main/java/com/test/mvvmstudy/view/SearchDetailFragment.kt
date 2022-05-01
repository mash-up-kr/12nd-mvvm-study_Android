package com.test.mvvmstudy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.mvvmstudy.R
import com.test.mvvmstudy.databinding.FragmentSearchBinding
import com.test.mvvmstudy.databinding.FragmentSearchDetailBinding
import com.test.mvvmstudy.model.ResultDetail

class SearchDetailFragment : Fragment() {

    private lateinit var binding : FragmentSearchDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailBinding.inflate(inflater,container,false)

        setInitData()

        return binding.root
    }
    private fun setInitData() {
        val searchData = arguments?.getSerializable("searchData") as ResultDetail
        binding.searchData = searchData
        Glide.with(binding.root).load(searchData.owner.imgUrl).into(binding.profileImg)
    }

}