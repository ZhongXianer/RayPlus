package com.ksballetba.rayplus.data.bean.treatmentVisitData


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MainPhysicalSignBodyBean(
    @SerializedName("end_time")
    val endTime: String?,
    val existence: Int?,
    @SerializedName("is_using_medicine")
    val isUsingMedicine: Int?,
    @SerializedName("main_symptom_id")
    val mainSymptomId: Int?,
    val measure: Int?,
    @SerializedName("medicine_relation")
    val medicineRelation: Int?,
    @SerializedName("start_time")
    val startTime: String?,
    @SerializedName("symptom_description")
    val symptomDescription: String?,
    @SerializedName("symptom_description_other")
    val symptomDescriptionOther: String?,
    @SerializedName("toxicity_classification")
    val toxicityClassification: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(endTime)
        writeValue(existence)
        writeValue(isUsingMedicine)
        writeValue(mainSymptomId)
        writeValue(measure)
        writeValue(medicineRelation)
        writeString(startTime)
        writeString(symptomDescription)
        writeString(symptomDescriptionOther)
        writeValue(toxicityClassification)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MainPhysicalSignBodyBean> =
            object : Parcelable.Creator<MainPhysicalSignBodyBean> {
                override fun createFromParcel(source: Parcel): MainPhysicalSignBodyBean =
                    MainPhysicalSignBodyBean(source)

                override fun newArray(size: Int): Array<MainPhysicalSignBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}