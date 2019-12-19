package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class DemographyBean(
    val date: String, // 2019-10-23
    val degree: Int?, // null
    @SerializedName("family_phone")
    val familyPhone: String,
    @SerializedName("hospital_ids")
    val hospitalIds: String, // 123
    @SerializedName("id_num")
    val idNum: String, // 111111111111111111
    val marriage: String, // 其他
    @SerializedName("marriage_other")
    val marriageOther: String,
    val phone: String,
    val race: String, // 其他
    @SerializedName("race_other")
    val raceOther: String,
    val sex: Int, // 0
    val vocation: String, // 其他
    @SerializedName("vocation_other")
    val vocationOther: String,
    val zone: Int? // null
)