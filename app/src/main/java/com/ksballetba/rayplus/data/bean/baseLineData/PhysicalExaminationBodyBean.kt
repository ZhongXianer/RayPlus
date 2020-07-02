package com.ksballetba.rayplus.data.bean.baseLineData


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PhysicalExaminationBodyBean(
    @SerializedName("breath_frequency")
    val breathFrequency: Int?, // 36
    @SerializedName("heart_rate")
    val heartRate: Int?, // 45
    val maxpressure: Int?, // 73
    val minpressure: Int?, // 24
    @SerializedName("report_id")
    val reportId: Int?,
    val temperature: Float?, // 36
    val time: String? // 2019-12-21
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Float::class.java.classLoader) as Float?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(breathFrequency)
        writeValue(heartRate)
        writeValue(maxpressure)
        writeValue(minpressure)
        writeValue(reportId)
        writeValue(temperature)
        writeString(time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhysicalExaminationBodyBean> =
            object : Parcelable.Creator<PhysicalExaminationBodyBean> {
                override fun createFromParcel(source: Parcel): PhysicalExaminationBodyBean =
                    PhysicalExaminationBodyBean(
                        source
                    )

                override fun newArray(size: Int): Array<PhysicalExaminationBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}