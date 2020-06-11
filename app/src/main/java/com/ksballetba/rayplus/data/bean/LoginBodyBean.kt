package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class LoginBodyBean(
    @SerializedName("account")
    val userAccount: String,
    @SerializedName("password")
    val userPassword: String,
    @SerializedName("type")
    val type: String
)