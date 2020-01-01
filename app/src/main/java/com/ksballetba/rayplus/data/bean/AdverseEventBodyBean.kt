package com.ksballetba.rayplus.data.bean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AdverseEventBodyBean(
    @SerializedName("adverse_event_id")
    val adverseEventId: Int?, // 3
    @SerializedName("adverse_event_name")
    val adverseEventName: String?, // 手疼
    @SerializedName("die_time")
    val dieTime: String?, // 2019-12-18
    @SerializedName("is_server_event")
    val isServerEvent: Int?, // 1
    @SerializedName("is_using_medicine")
    val isUsingMedicine: Int?, // 1
    val measure: Int?, // 4
    @SerializedName("medicine_measure")
    val medicineMeasure: Int?, // 1
    @SerializedName("medicine_relation")
    val medicineRelation: Int?, // 0
    @SerializedName("other_SAE_state")
    val otherSAEState: String?, // 无
    val recover: String?, // 5
    @SerializedName("recover_time")
    val recoverTime: String?, // 2019-12-17
    @SerializedName("report_time")
    val reportTime: String?, // 2019-12-16
    @SerializedName("report_type")
    val reportType: Int?, // 0
    @SerializedName("SAE_diagnose")
    val sAEDiagnose: String?, // 无
    @SerializedName("SAE_recover")
    val sAERecover: Int?, // 1
    @SerializedName("SAE_relation")
    val sAERelation: Int?, // 1
    @SerializedName("SAE_start_time")
    val sAEStartTime: String?, // 2019-12-04
    @SerializedName("SAE_state")
    val sAEState: Int?, // 1
    @SerializedName("start_time")
    val startTime: String?, // 2019-12-17
    @SerializedName("toxicity_classification")
    val toxicityClassification: Int? // 1
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(adverseEventId)
        writeString(adverseEventName)
        writeString(dieTime)
        writeValue(isServerEvent)
        writeValue(isUsingMedicine)
        writeValue(measure)
        writeValue(medicineMeasure)
        writeValue(medicineRelation)
        writeString(otherSAEState)
        writeString(recover)
        writeString(recoverTime)
        writeString(reportTime)
        writeValue(reportType)
        writeString(sAEDiagnose)
        writeValue(sAERecover)
        writeValue(sAERelation)
        writeString(sAEStartTime)
        writeValue(sAEState)
        writeString(startTime)
        writeValue(toxicityClassification)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AdverseEventBodyBean> =
            object : Parcelable.Creator<AdverseEventBodyBean> {
                override fun createFromParcel(source: Parcel): AdverseEventBodyBean =
                    AdverseEventBodyBean(source)

                override fun newArray(size: Int): Array<AdverseEventBodyBean?> = arrayOfNulls(size)
            }
    }
}