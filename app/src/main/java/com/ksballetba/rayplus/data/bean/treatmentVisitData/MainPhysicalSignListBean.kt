package com.ksballetba.rayplus.data.bean.treatmentVisitData

import com.google.gson.annotations.SerializedName

data class MainPhysicalSignListBean(
    val code: Int,
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("cycle_id")
        val cycleId: Int?,
        @SerializedName("end_time")
        val endTime: String?,
        val existence: Int?,
        @SerializedName("i_delete")
        val isDelete: Int?,
        @SerializedName("is_using_medicine")
        val isUsingMedicine: Int?,
        @SerializedName("main_symptom_id")
        val mainSymptomId: Int,
        val measure: Int?,
        @SerializedName("medicine_relation")
        val medicineRelation: Int?,
        @SerializedName("sample_id")
        val sampleId: Int?,
        @SerializedName("start_time")
        val startTime: String?,
        @SerializedName("symptom_description")
        val symptomDescription: String?,
        @SerializedName("symptom_description_other")
        val symptomDescriptionOther:String?,
        @SerializedName("toxicity_classification")
        val toxicityClassification: Int?
    )
}