package com.ksballetba.rayplus.data.bean.sampleData


import com.google.gson.annotations.SerializedName

data class SampleListBean(
    val code: Int, // 0
    val count: Int, // 2000000
    val `data`: List<Data>,
    val msg: String, // success
    val total: Int
) {
    data class Data(
        //年龄
        val age: Int, // 0
        //是否签名
        @SerializedName("is_signed")
        val isSigned: String,
        @SerializedName("signature_user_id")
        val signatureUserId: Int,
        //入组日期
        @SerializedName("create_time")
        val createTime: Int?,
        //出生日期
        val date: String, // 2019-10-23
        //患者组别
        @SerializedName("group_id")
        val groupId: Int, // 1
        //组别名称
        @SerializedName("group_name")
        val groupName: String, // 安罗替尼
        //身份证号码
        @SerializedName("id_num")
        val idNum: String, // 111000000000000000
        //入组日期
        @SerializedName("in_group_time")
        val inGroupTime: String, // 2019-10-02
        //随访进度
        @SerializedName("interview_status")
        val interviewStatus: String, // 访视2
        //上一次随访时间
        @SerializedName("last_interview_time")
        val lastInterviewTime: String,
        //预计下一次随访时间
        @SerializedName("next_interview_time")
        val nextInterviewTime: String,
        //患者组别
        @SerializedName("patient_ids")
        val patientIds: String, // 0001
        //患者姓名
        @SerializedName("patient_name")
        val patientName: String, // 刘轲
        //项目id
        @SerializedName("project_id")
        val projectId: Int, // 1
        //中心id
        @SerializedName("research_center_id")
        val researchCenterId: Int, // 2
        //中心名称
        @SerializedName("research_center_ids")
        val researchCenterIds: String, // 襄阳市第一人民医院
        //样本id
        @SerializedName("sample_id")
        val sampleId: Int, // 5
        //性别
        val sex: String, // 男
        //签署日期
        @SerializedName("sign_time")
        val signTime: String, // 2019-10-08
        //状态
        val status: Status,
        //样本状态
        @SerializedName("submit_status")
        val submitStatus: Int,
        //更新时间
        @SerializedName("update_time")
        val updateTime: Int,
        //样本是谁创建的
        @SerializedName("user_id")
        val userId: Int
    ) {
        data class Status(
            @SerializedName("cycle_status")
            val cycleStatus: List<CycleStatus>,
            @SerializedName("interview_status")
            val interviewStatus: List<InterviewStatus>
        )

        data class CycleStatus(
            @SerializedName("cycle_number")
            val cycleNumber: Int,
            @SerializedName("is_submit")
            val isSubmit: Int
        )

        data class InterviewStatus(
            @SerializedName("interview_id")
            val interviewId: Int,
            @SerializedName("is_submit")
            val isSubmit: Int
        )
    }
}