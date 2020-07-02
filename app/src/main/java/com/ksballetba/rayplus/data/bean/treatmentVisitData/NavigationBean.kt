package com.ksballetba.rayplus.data.bean.treatmentVisitData


import com.google.gson.annotations.SerializedName

data class NavigationBean(
    val code: Int, // 200
    val `data`: List<Data>,
    val msg: String // success
) {
    data class Data(
        @SerializedName("cycle_number")
        val cycleNumber: Int, // 2
        val title: String // 访视2
    )
}