package com.ksballetba.rayplus.data.bean.loginData

data class LoginBodyBean(
    val account: String?,
    val password: String?,
    val system_ids: List<Int?>?,
    val type: String?
)