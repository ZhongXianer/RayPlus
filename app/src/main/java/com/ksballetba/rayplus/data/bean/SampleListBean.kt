package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class SampleListBean(
    val code: Int, // 0
    val count: Int, // 2000000
    val `data`: List<Data>,
    val msg: String // success
) {
    data class Data(
        val age: Int, // 0
        val date: String, // 2019-10-23
        @SerializedName("group_id")
        val groupId: Int, // 1
        @SerializedName("group_name")
        val groupName: String, // 安罗替尼
        @SerializedName("id_num")
        val idNum: String, // 111000000000000000
        @SerializedName("in_group_time")
        val inGroupTime: String, // 2019-10-02
        @SerializedName("interview_status")
        val interviewStatus: String, // 访视2
        @SerializedName("is_submit")
        val isSubmit: Any?, // null
        @SerializedName("last_interview_time")
        val lastInterviewTime: String,
        @SerializedName("lock_status")
        val lockStatus: Any?, // null
        @SerializedName("next_interview_time")
        val nextInterviewTime: String,
        @SerializedName("patient_ids")
        val patientIds: String, // 0001
        @SerializedName("patient_name")
        val patientName: String, // 刘轲
        @SerializedName("project_id")
        val projectId: Int, // 1
        @SerializedName("research_center_id")
        val researchCenterId: Int, // 2
        @SerializedName("research_center_ids")
        val researchCenterIds: String, // 襄阳市第一人民医院
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        val sex: String, // 男
        @SerializedName("sign_time")
        val signTime: String, // 2019-10-08
        @SerializedName("user_id")
        val userId: Int // 1
    )
}