package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class BaseResponseBean(
    val code: Int, // 200
    val data: String?,
    val msg: String
)