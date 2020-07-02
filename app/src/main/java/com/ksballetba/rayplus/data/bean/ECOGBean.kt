package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ECOGBean(
    val code: Int?,
    val msg:String?,
    val data:Data?
){
    data class Data(
        @SerializedName("ECOG")
        val eCOG: String? // 1
    )
}
