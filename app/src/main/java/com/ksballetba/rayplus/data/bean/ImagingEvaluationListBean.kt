package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ImagingEvaluationListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("cycle_id")
        val cycleId: Int, // 5
        @SerializedName("evaluate_id")
        val evaluateId: Int, // 6
        val method: String, // 神奇的方法
        val part: String, // 腹部
        @SerializedName("photo_src")
        val photoSrc: Any?, // null
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        val time: String, // 2019-12-25
        @SerializedName("tumor_long")
        val tumorLong: Float, // 11
        @SerializedName("tumor_short")
        val tumorShort: Float // 15
    )
}