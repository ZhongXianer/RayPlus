package com.ksballetba.rayplus.data.bean.baseData

data class IsImagingEvaluateResponseBean(
    val code: Int,
    val `data`: Data,
    val msg: String
) {
    data class Data(
        val is_photo_evaluate: Int
    )
}