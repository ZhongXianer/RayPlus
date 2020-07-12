package com.ksballetba.rayplus.data.bean.baseData


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
        @SerializedName("is_delete")
        val isDelete: Int,
        var method: String?, // 神奇的方法
        var part: String?, // 腹部
        @SerializedName("photo_src")
        val photoSrc: Any?, // null
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        var time: String?, // 2019-12-25
        @SerializedName("tumor_desc")
        var tumorDesc: String?,
        @SerializedName("tumor_long")
        var tumorLong: Float?, // 11
        @SerializedName("tumor_short")
        var tumorShort: Float?, // 15
        @SerializedName("upload_file_address")
        val UpLoadFileAddress: Any?
    )
}