package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class LoginResponseBean(
    val code: Int, // 200
    val `data`: Data,
    val msg: String // ok
) {
    data class Data(
        @SerializedName("ALTER_can_export")
        val aLTERCanExport: Int, // 1
        val token: String, // eyJleHAiOjE1OTQ0MzM2MzgsImlhdCI6MTU5MTg0MTYzOCwiYWxnIjoiSFM1MTIifQ.eyJhdXRocyI6eyIwIjp7IjkiOiIxIiwiMiI6IjEifSwiMSI6eyI4IjoiMSIsIjEzIjoiMSIsIjUiOiIxIiwiNiI6IjEiLCI3IjoiMSIsIjMiOiIxIiwiOSI6IjEiLCI0IjoiMSJ9fSwidXNlcl9pZCI6OH0.qEhoqkx2Z_1F6v5eKi-pmzmCjb93umvAVKnNGVwW1r4ZNZNkcphSCjO8P4RPKPvCtizG4JlqVK1-zpE2ZeO5-w
        val userInfo: UserInfo
    ) {
        data class UserInfo(
            val account: String, // 王金林
            val department: String, // 研究中心
            val email: Any?, // null
            val id: Int, // 8
            @SerializedName("id_card")
            val idCard: Any?, // null
            @SerializedName("is_super")
            val isSuper: Int, // 0
            val name: String, // 王金林
            val office: String, // 肿瘤科
            val phone: String, // 13098810396
            @SerializedName("research_center_id")
            val researchCenterId: Int, // 1
            @SerializedName("research_center_name")
            val researchCenterName: String, // 同济医院
            @SerializedName("system_admin")
            val systemAdmin: List<SystemAdmin>,
            val title: String // 研究员
        ) {
            data class SystemAdmin(
                @SerializedName("is_admin")
                val isAdmin: Int, // 0
                @SerializedName("system_id")
                val systemId: Int // 1
            )
        }
    }
}