package com.example.myapplication.domain.model

/**
 * @author 김현국
 * @created 2022/05/01
 */
data class DomainRepository(
    val id: Int,
    val name: String,
    val owner: DomainOwner,
    val language: String?,
    val stars: Int?,
    val description: String?,
    val lastUpdated: String?
)
