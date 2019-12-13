package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ResearchCenterBean(
    @SerializedName("research_center_id")
    val researchCenterId: Int, // 1
    @SerializedName("research_center_ids")
    val researchCenterIds: String, // 同济医院
    @SerializedName("research_description")
    val researchDescription: String // 本研究中心暂无描述
)