package com.example.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.myapplication.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

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

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(0)
            .tag("PRETTY_LOGGER")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }
        )
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }
}
