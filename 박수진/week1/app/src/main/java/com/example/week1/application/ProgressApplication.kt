package com.example.week1.application

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.example.week1.R
import com.example.week1.databinding.DialogProgressBinding

class ProgressApplication : Application() {
    private lateinit var baseApplication: ProgressApplication
    private lateinit var progressDialog: AppCompatDialog

    fun getInstance(): ProgressApplication = baseApplication

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    fun progressON(activity: Activity?) {
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

    fun progressOFF() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}