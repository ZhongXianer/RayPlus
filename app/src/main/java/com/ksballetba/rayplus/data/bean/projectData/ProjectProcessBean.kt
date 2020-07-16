package com.ksballetba.rayplus.data.bean.projectData

data class ProjectProcessBean(
    val code: Int?,
    val `data`: Data?,
    val msg: String?
) {
    data class Data(
        val now: Int?,
        val total: Int?
    )
}