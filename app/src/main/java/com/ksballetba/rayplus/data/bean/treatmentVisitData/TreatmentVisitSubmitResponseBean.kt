package com.ksballetba.rayplus.data.bean.treatmentVisitData

import com.google.gson.annotations.SerializedName

data class TreatmentVisitSubmitResponseBean(
    val code: Int,
    val msg: String,
    val data: List<Data>
) {
    data class Data(
        @SerializedName("cycle_number")
        val cycleNumber: Int,
        @SerializedName("is_submit")
        val submitStatus: Int
    )
}