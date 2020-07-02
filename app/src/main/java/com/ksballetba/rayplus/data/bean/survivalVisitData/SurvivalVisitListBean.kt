package com.ksballetba.rayplus.data.bean.survivalVisitData


import com.google.gson.annotations.SerializedName

data class SurvivalVisitListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("die_reason")
        val dieReason: Int, // 1
        @SerializedName("die_time")
        val dieTime: String, // 2019-12-17
        @SerializedName("has_other_treatment")
        val hasOtherTreatment: Int?, // false
        @SerializedName("interview_id")
        val interviewId: Int, // 4
        @SerializedName("interview_time")
        val interviewTime: String, // 2019-12-10
        @SerializedName("interview_way")
        val interviewWay: Int, // 2
        @SerializedName("last_time_survival")
        val lastTimeSurvival: String, // 2019-12-23
        @SerializedName("OS_method")
        val oSMethod: Int, // 8
        @SerializedName("other_method")
        val otherMethod: String, // 无
        @SerializedName("other_reason")
        val otherReason: String, // 老死
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        @SerializedName("status_confirm_time")
        val statusConfirmTime: String, // 2019-12-27
        @SerializedName("survival_status")
        val survivalStatus: Int, // 1
        @SerializedName("is_submit")
        val isSubmit: Int
    )
}