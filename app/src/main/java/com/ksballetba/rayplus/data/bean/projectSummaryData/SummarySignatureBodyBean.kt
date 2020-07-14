package com.ksballetba.rayplus.data.bean.projectSummaryData

import com.google.gson.annotations.SerializedName

data class SummarySignatureBodyBean(
    val code: Int?,
    val `data`: Data?,
    val msg: String?
) {
    data class Data(
        val inspector: Inspector?,
        val si: Si?
    ) {
        data class Inspector(
            @SerializedName("file_path")
            val filePath: String?,
            @SerializedName("research_center_id")
            val researchCenterId: Int?,
            @SerializedName("user_id")
            val userId: Int?,
            @SerializedName("user_name")
            val userName: String?
        )

        data class Si(
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
}