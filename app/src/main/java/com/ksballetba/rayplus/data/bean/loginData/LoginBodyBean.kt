package com.ksballetba.rayplus.data.bean.loginData


import com.google.gson.annotations.SerializedName

/**
 * 本类是登录信息数据类
 */
data class LoginBodyBean(
    /*登录账号*/
    @SerializedName("account")
    val userAccount: String,
    /*登录密码*/
    @SerializedName("password")
    val userPassword: String,
    /*登录类型*/
    @SerializedName("type")
    val type: String,
    @SerializedName("system_id")
    val systemId: Int
)