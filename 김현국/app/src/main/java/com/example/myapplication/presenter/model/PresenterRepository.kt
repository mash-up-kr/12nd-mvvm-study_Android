package com.example.myapplication.presenter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author 김현국
 * @created 2022/05/02
 */
@Parcelize
data class PresenterRepository(
    val id: Int,
    val name: String,
    val owner: PresenterOwner,
    val language: String?,
    val stars: Int?,
    val description: String?,
    val lastUpdated: String?
) : Parcelable
