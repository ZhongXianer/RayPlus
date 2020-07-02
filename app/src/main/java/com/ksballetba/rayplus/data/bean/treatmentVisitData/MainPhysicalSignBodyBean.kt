package com.ksballetba.rayplus.data.bean.treatmentVisitData


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MainPhysicalSignBodyBean(
    @SerializedName("end_time")
    val endTime: String?, // 2019-12-17
    val existence: String, // 1
    @SerializedName("main_symptom_id")
    val mainSymptomId: Int?,
    @SerializedName("start_time")
    val startTime: String?, // 2019-12-11
    @SerializedName("symptom_description")
    val symptomDescription: String?, // 其他
    @SerializedName("symptom_description_other")
    val symptomDescriptionOther: String? // 爆炸
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(endTime)
        writeString(existence)
        writeValue(mainSymptomId)
        writeString(startTime)
        writeString(symptomDescription)
        writeString(symptomDescriptionOther)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MainPhysicalSignBodyBean> =
            object : Parcelable.Creator<MainPhysicalSignBodyBean> {
                override fun createFromParcel(source: Parcel): MainPhysicalSignBodyBean =
                    MainPhysicalSignBodyBean(
                        source
                    )

                override fun newArray(size: Int): Array<MainPhysicalSignBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}