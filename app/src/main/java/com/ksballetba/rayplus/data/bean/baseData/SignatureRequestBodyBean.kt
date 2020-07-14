package com.ksballetba.rayplus.data.bean.baseData

import com.google.gson.annotations.SerializedName

data class SignatureRequestBodyBean(
    @SerializedName("user_id")
    val userId: String
)