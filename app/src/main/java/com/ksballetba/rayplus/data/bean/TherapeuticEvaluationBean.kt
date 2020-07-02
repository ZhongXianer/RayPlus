package com.ksballetba.rayplus.data.bean


data class TherapeuticEvaluationBean(
    val code: Int,
    val msg: String,
    val data: Data
) {
    data class Data(
        val evaluation: Int // 1
    )
}

