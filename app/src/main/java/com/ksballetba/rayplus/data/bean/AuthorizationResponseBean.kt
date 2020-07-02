package com.ksballetba.rayplus.data.bean

data class AuthorizationResponseBean(
    val code: Int,
    val msg: String,
    val data: List<String?>
)