package com.ksballetba.rayplus.data.bean.treatmentVisitData


import com.google.gson.annotations.SerializedName

data class MainPhysicalSignListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("cycle_id")
        val cycleId: Int, // 6
        @SerializedName("end_time")
        val endTime: String, // 2019-12-17
        val existence: Int?, // 消失
        @SerializedName("main_symptom_id")
        val mainSymptomId: Int, // 5
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        @SerializedName("start_time")
        val startTime: String, // 2019-12-03
        @SerializedName("symptom_description")
        val symptomDescription: String // 高血压
    )
}