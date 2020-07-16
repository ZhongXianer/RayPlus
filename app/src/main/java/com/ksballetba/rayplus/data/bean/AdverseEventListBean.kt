package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class AdverseEventListBean(
    val code: Int, // 0
    val count: Int?, // 200000
    val `data`: List<Data?>?,
    val msg: String
) {
    data class Data(
        @SerializedName("adverse_event_id")
        val adverseEventId: Int?, // 3
        @SerializedName("adverse_event_name")
        val adverseEventName: String?, // 头疼
        @SerializedName("cycle_id")
        val cycleId: Int?, // 7
        @SerializedName("die_time")
        val dieTime: String?, // 2019-12-16
        @SerializedName("is_server_event")
        val isServerEvent: String?, // 严重不良事件
        @SerializedName("is_using_medicine")
        val isUsingMedicine: Int?, // true
        val measure: String?, // 剂量不变
        @SerializedName("medicine_measure")
        val medicineMeasure: Int?, // 1
        @SerializedName("medicine_relation")
        val medicineRelation: String?, // 肯定有关
        @SerializedName("other_SAE_state")
        val otherSAEState: String?, // 无
        val recover: String?, // 症状消失
        @SerializedName("recover_time")
        val recoverTime: String?, // 2019-10-31
        @SerializedName("report_time")
        val reportTime: String?, // 2019-12-03
        @SerializedName("report_type")
        val reportType: Int?, // 0
        @SerializedName("SAE_diagnose")
        val sAEDiagnose: String?, // 无
        @SerializedName("SAE_recover")
        val sAERecover: Int?, // 1
        @SerializedName("SAE_relations")
        val sAERelations: Int?, // 0
        @SerializedName("SAE_start_time")
        val sAEStartTime: String?, // 2019-12-31
        @SerializedName("SAE_state")
        val sAEState: Int?, // 8
        @SerializedName("sample_id")
        val sampleId: Int?, // 5
        @SerializedName("start_time")
        val startTime: String?, // 2019-10-20
        @SerializedName("toxicity_classification")
        val toxicityClassification: Int?, // 0
        var needDeleted: Boolean?
    )
}