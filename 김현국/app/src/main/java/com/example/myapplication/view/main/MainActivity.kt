package com.example.myapplication.view.main

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presenter.main.MainContract
import com.example.myapplication.presenter.main.MainPresenter
import com.example.myapplication.view.search.SearchFragment

/**
 * @author 김현국
 * @created 2022/04/22
 */
class MainActivity :
    BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }
    ),
    MainContract.View {
    private val navHostFragment by lazy { (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment) }
    private val navController by lazy { navHostFragment.navController }
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainPresenter.takeView(this)
        setListener()
    }

    private fun setListener() {
        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    passQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    fun passQuery(query: String) {
        val frag = navHostFragment.childFragmentManager.fragments[0]
        if (frag is SearchFragment) {
            frag.passData(query)
        }
    }

    override fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
