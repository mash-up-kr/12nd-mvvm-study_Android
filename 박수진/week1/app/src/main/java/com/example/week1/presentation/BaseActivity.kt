package com.example.week1.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.example.week1.R
import com.example.week1.databinding.DialogProgressBinding

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: AppCompatDialog

    fun onProgress() {
        val binding = DialogProgressBinding.inflate(LayoutInflater.from(this))

        progressDialog = AppCompatDialog(this).apply {
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