package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class LoginBodyBean(
    @SerializedName("user_account")
    val userAccount: String,
    @SerializedName("user_password")
    val userPassword: String
)