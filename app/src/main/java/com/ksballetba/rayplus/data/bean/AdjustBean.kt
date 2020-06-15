package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

/**
 *
 */
data class AdjustBean(
    @SerializedName("adjust_percent")
    val adjustPercent: String, // 123
    @SerializedName("adjust_reason")
    val adjustReason: String, // 123
    val adjustment: String // 1
)