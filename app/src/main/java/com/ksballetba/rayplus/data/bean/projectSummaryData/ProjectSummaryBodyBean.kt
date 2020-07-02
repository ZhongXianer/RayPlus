package com.ksballetba.rayplus.data.bean.projectSummaryData


import com.google.gson.annotations.SerializedName

data class ProjectSummaryBodyBean(
    @SerializedName("best_effect")
    val bestEffect: Int?, // 3
    @SerializedName("is_stop")
    val isStop: Int?, // 0
    @SerializedName("last_time_drug")
    val lastTimeDrug: String?, // 2019-12-19
    @SerializedName("OS")
    val oS: String?, // 123
    @SerializedName("other_reasons")
    val otherReasons: String?, // 123
    @SerializedName("PFS")
    val pFS: String?, // 123
    @SerializedName("reason_stop_drug")
    val reasonStopDrug: Int?, // 7
    val relay: String?, // 123
    @SerializedName("treatment_times")
    val treatmentTimes: Int? // 1
)