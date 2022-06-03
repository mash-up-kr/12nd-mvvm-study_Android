package com.example.myapplication.presenter.view.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author 김현국
 * @created 2022/04/22
 */
@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }
    ) {
    private val navHostFragment by lazy { (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment) }
    private val navController by lazy { navHostFragment.navController }
    private val model by viewModels<MainViewModel>()
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListener()
    }

    private fun setListener() = with(binding) {
        svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                model.updateQuery(query)
                imm.hideSoftInputFromWindow(svMain.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_detail -> {
                    abMain.visibility = View.GONE
                }
                R.id.navigation_search -> {
                    abMain.visibility = View.VISIBLE
                }
            }
        }
    }
}
