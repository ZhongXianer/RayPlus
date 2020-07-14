package com.ksballetba.rayplus.data.bean.baseData

import com.google.gson.annotations.SerializedName

data class ImagingEvaluationFileBodyBean(
    val code: Int,
    val `data`: List<Data?>,
    val msg: String
) {
    data class Data(
        @SerializedName("evaluate_id")
        val evaluateId: Int?,
        @SerializedName("file_ctime")
        val fileCreateTime: String?,
        @SerializedName("file_name")
        val fileName: String?,
        @SerializedName("file_path")
        val filePath: String?,
        @SerializedName("file_size")
        val fileSize: String?
    )
}