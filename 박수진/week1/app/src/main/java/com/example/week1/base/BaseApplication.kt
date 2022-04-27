package com.example.week1.base

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.example.week1.R
import com.example.week1.databinding.DialogProgressBinding

class BaseApplication : Application() {
    private lateinit var baseApplication: BaseApplication
    private lateinit var progressDialog: AppCompatDialog

    fun getInstance(): BaseApplication = baseApplication

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    fun onProgress(activity: Activity?) {
        val binding = DialogProgressBinding.inflate(LayoutInflater.from(this))

        progressDialog = AppCompatDialog(activity).apply {
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(binding.root)
            show()
        }

        with(binding) {
            Glide.with(root)
                .load(R.drawable.ponyo)
                .into(progressImg)
        }
    }

    fun offProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}