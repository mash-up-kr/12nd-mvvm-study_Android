package com.test.mvvmstudy.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.mvvmstudy.R
import com.test.mvvmstudy.databinding.FragmentSearchDetailBinding
import com.test.mvvmstudy.data.ResultDetail

class SearchDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailBinding.inflate(inflater, container, false)

        setInitData()

        return binding.root
    }

    private fun setInitData() {
        val searchData = arguments?.getSerializable("searchData") as ResultDetail
        binding.searchData = searchData
        Glide.with(binding.root).load(searchData.owner.imgUrl).into(binding.profileImg)
    }

}