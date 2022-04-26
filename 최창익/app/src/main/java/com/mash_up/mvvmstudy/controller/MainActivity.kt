package com.mash_up.mvvmstudy.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mash_up.mvvmstudy.view.MainAdapter
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding
import com.mash_up.mvvmstudy.model.MainUiModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvMain.adapter = this.adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(
            listOf(
                MainUiModel(
                    title = "123"
                ),
                MainUiModel(
                    title = "456"
                )
            )
        )
    }
}
