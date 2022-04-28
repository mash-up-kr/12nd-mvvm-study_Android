package com.example.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @author 김현국
 * @created 2022/04/25
 */

abstract class BaseActivity<VB : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initPresenter()
    }

    abstract fun initPresenter()
}
