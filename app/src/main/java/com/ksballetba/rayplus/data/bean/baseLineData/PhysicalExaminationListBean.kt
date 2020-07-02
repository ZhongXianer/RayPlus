package com.ksballetba.rayplus.data.bean.baseLineData


import com.google.gson.annotations.SerializedName

data class PhysicalExaminationListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("breath_frequency")
        val breathFrequency: Int, // 36
        @SerializedName("heart_rate")
        val heartRate: Int, // 45
        val maxpressure: Int, // 73
        val minpressure: Int, // 24
        @SerializedName("report_id")
        val reportId: Int, // 4
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        val temperature: Float, // 36
        val time: String // 2019-12-21
    )
}