package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ProjectListBean(
    val code: Int, // 0
    val count: Int, // 20000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        val description: String, // 晚期非小细胞肺癌患者安罗替尼治疗有效性与安全性观察研究
        @SerializedName("group_name")
        val groupName: String, // 安罗替尼, 安罗替尼 + TKI, 安罗替尼 + 化疗, 安罗替尼 + 免疫
        val now: Int, // 2
        val phone: String, // 13212760751
        val principal: String, // 褚倩
        @SerializedName("project_id")
        val projectId: Int, // 1
        @SerializedName("project_ids")
        val projectIds: String, // ESCO-YOUNG 1901
        @SerializedName("research_center_id")
        val researchCenterId: Int, // 1
        @SerializedName("research_center_ids")
        val researchCenterIds: String, // 同济医院
        val total: Int // 1000
    )
}