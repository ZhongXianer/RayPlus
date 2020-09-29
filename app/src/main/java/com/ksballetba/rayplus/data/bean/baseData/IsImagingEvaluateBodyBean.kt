package com.ksballetba.rayplus.data.bean.baseData

import com.google.gson.annotations.SerializedName

data class IsImagingEvaluateBodyBean(
    @SerializedName("is_photo_evaluate")
    val isPhotoEvaluate: Int?
)