package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class ProjectListBean(
    val code: Int, // 0
    val count: Int, // 2000000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        val description: String, // ???????????????????????????
        @SerializedName("group_name")
        val groupName: String, // ????, ???? + TKI, ???? + ??, ???? + ??
        val link: String, //  <a href='research_scheme'>???????? 
        val now: Int, // 1
        val phone: String, // ?????xxx
        val principal: String, // ???xxx
        @SerializedName("project_id")
        val projectId: Int, // 1
        @SerializedName("project_ids")
        val projectIds: String, // ESCO-YOUNG 1901
        @SerializedName("research_center_id")
        val researchCenterId: Int, // 1
        @SerializedName("research_center_ids")
        val researchCenterIds: String, // ????
        val total: Int // 1000
    )
}