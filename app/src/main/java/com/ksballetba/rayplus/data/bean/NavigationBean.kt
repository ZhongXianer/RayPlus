package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class NavigationBean(
    val children: List<Children>,
    val href: String, // /summary_html
    val icon: String, // fa-stop-circle
    val spread: Boolean, // true
    val title: String // 项目总结
) {
    data class Children(
        @SerializedName("cycle_number")
        val cycleNumber: Int, // 3
        val href: String, // /treatment_record_html?cycle_id=9
        val icon: String,
        val spread: Boolean, // true
        val title: String // 访视3
    )
}