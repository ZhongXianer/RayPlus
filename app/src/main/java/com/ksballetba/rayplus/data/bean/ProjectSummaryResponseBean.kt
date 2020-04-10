package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ProjectSummaryResponseBean(
    @SerializedName("best_effect")
    val bestEffect: Int, // 3
    @SerializedName("inspector_signature")
    val inspectorSignature: Any?, // null
    @SerializedName("inspector_signature_time")
    val inspectorSignatureTime: Any?, // null
    @SerializedName("is_stop")
    val isStop: Int?, // false
    @SerializedName("last_time_drug")
    val lastTimeDrug: String, // 2019-12-19
    @SerializedName("OS")
    val oS: String, // 123
    @SerializedName("other_reasons")
    val otherReasons: String, // 123
    @SerializedName("PFS")
    val pFS: String, // 123
    @SerializedName("principal_signature")
    val principalSignature: Any?, // null
    @SerializedName("principal_signature_time")
    val principalSignatureTime: Any?, // null
    @SerializedName("reason_stop_drug")
    val reasonStopDrug: Int, // 7
    val relay: String, // 123
    @SerializedName("sample_id")
    val sampleId: Int, // 5
    @SerializedName("si_signature")
    val siSignature: Any?, // null
    @SerializedName("si_signature_time")
    val siSignatureTime: Any?, // null
    @SerializedName("treatment_times")
    val treatmentTimes: Int // 1
)