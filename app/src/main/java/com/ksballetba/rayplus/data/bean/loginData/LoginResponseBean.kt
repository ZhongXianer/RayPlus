package com.ksballetba.rayplus.data.bean.loginData


data class LoginResponseBean(
    val code: Int,
    val `data`: Data,
    val msg: String
) {
    data class Data(
        val tokens: List<Token>,
        val userInfo: UserInfo
    ) {
        data class Token(
            val project_id: Int,
            val token: String
        )

        data class UserInfo(
            val account: String,
            val department: String,
            val email: Any?,
            val id: Int,
            val id_card: Any?,
            val is_super: Int,
            val name: String,
            val office: String,
            val phone: Any?,
            val research_center_id: Int,
            val research_center_name: String,
            val system_admin: List<SystemAdmin>,
            val title: String
        ) {
            data class SystemAdmin(
                val is_admin: Int,
                val system_id: Int
            )
        }
    }
}