package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class TreatmentRecordListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("cycle_id")
        val cycleId: Int, // 7
        val description: String?, // null
        @SerializedName("end_time")
        val endTime: String?, // null
        @SerializedName("medicine_name")
        val medicineName: String?, // null
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        @SerializedName("start_time")
        val startTime: String?, // null
        @SerializedName("treatment_name")
        val treatmentName: String, // 第4周期用药
        @SerializedName("treatment_record_id")
        val treatmentRecordId: Int // 4
    )
}