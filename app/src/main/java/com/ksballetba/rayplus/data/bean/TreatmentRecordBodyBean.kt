package com.ksballetba.rayplus.data.bean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TreatmentRecordBodyBean(
    val description: String?, // 1天3包
    @SerializedName("end_time")
    val endTime: String?, // 2019-12-18
    @SerializedName("medicine_name")
    val medicineName: String?, // 板蓝根
    @SerializedName("start_time")
    val startTime: String?, // 2019-12-04
    @SerializedName("treatment_name")
    val treatmentName: String, // 吃药
    @SerializedName("treatment_record_id")
    val treatmentRecordId: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(description)
        writeString(endTime)
        writeString(medicineName)
        writeString(startTime)
        writeString(treatmentName)
        writeValue(treatmentRecordId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TreatmentRecordBodyBean> =
            object : Parcelable.Creator<TreatmentRecordBodyBean> {
                override fun createFromParcel(source: Parcel): TreatmentRecordBodyBean =
                    TreatmentRecordBodyBean(source)

                override fun newArray(size: Int): Array<TreatmentRecordBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}