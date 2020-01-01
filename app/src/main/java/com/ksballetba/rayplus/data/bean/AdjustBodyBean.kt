package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class AdjustBodyBean(
    @SerializedName("adjustment_status")
    val adjustmentStatus: AdjustmentStatus
) {
    data class AdjustmentStatus(
        @SerializedName("adjust_percent")
        val adjustPercent: String, // 123
        @SerializedName("adjust_reason")
        val adjustReason: String, // 123
        val adjustment: String // 1
    )
}