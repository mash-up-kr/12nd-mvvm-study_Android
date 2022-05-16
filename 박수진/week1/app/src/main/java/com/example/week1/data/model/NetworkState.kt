package com.example.week1.data.model

enum class NetworkState(val msg: String) {
    LOADED("Success"),
    LOADING("Running"),
    ERROR("Something went wrong")
}