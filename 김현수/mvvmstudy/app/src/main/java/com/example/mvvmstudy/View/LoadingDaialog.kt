package com.example.mvvmstudy.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.mvvmstudy.R

class LoadingDialog constructor(context : Context) : Dialog(context) {

    init{
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_loading)
    }

}