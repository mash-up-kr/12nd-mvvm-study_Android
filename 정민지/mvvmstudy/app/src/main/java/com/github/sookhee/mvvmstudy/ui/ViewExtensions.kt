package com.github.sookhee.mvvmstudy.ui

import android.view.View

/**
 *  ViewExtensions.kt
 *
 *  Created by Minji Jeong on 2022/05/11
 *  Copyright © 2022 MashUp All rights reserved.
 */

fun View.showIf(isShow: Boolean) {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}