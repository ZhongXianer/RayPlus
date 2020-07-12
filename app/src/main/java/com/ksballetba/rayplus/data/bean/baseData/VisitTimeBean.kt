package com.ksballetba.rayplus.data.bean.baseData


import com.google.gson.annotations.SerializedName

data class VisitTimeBean(
    @SerializedName("code")
    val code: Int?,
//    @SerializedName("cycle_time")
//    val cycleTime: String?, // 2019-12-13
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("data")
    val data: Data?
) {
    data class Data(
        @SerializedName("cycle_time")
        val cycleTime: String?
    )
}