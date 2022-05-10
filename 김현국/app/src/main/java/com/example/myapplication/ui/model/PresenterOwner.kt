package com.example.myapplication.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author 김현국
 * @created 2022/05/02
 */
@Parcelize
data class PresenterOwner(
    val login: String,
    val image: String
) : Parcelable
