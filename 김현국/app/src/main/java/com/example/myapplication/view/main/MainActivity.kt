package com.example.myapplication.view.main

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presenter.main.MainContract
import com.example.myapplication.presenter.main.MainPresenter
/**
 * @author 김현국
 * @created 2022/04/22
 */
class MainActivity :
    BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }
    ),
    MainContract.View {
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment).navController }
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
                    val bundle = Bundle()
                    bundle.putString("q", query)
                    navController.navigate(R.id.searchFragment, bundle)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    override fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.dropView()
    }
}
