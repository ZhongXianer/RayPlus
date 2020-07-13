package com.ksballetba.rayplus.data.bean.baseData

import com.google.gson.annotations.SerializedName

data class InvestigatorSignatureBodyBean(
    val code: Int,
    val `data`: Data?,
    val msg: String
) {
    data class Data(
        @SerializedName("file_path")
        val filePath: String?,
        @SerializedName("research_center_id")
        val researchCenterId: Int?,
        @SerializedName("user_id")
        val userId: Int?,
        @SerializedName("user_name")
        val userName: String?
    )
}