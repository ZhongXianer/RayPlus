package com.ksballetba.rayplus.data.bean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImagingEvaluationBodyBean(
    @SerializedName("evaluate_id")
    val evaluateId: Int?,
    val method: String?, // 其他
    @SerializedName("method_other")
    val methodOther: String?, // 切片
    val part: String?, // 西瓜
    val time: String?, // 2019-12-25
    @SerializedName("tumor_long")
    val tumorLong: Float?, // 10
    @SerializedName("tumor_short")
    val tumorShort: Float? // 22
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Float::class.java.classLoader) as Float?,
        source.readValue(Float::class.java.classLoader) as Float?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(evaluateId)
        writeString(method)
        writeString(methodOther)
        writeString(part)
        writeString(time)
        writeValue(tumorLong)
        writeValue(tumorShort)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImagingEvaluationBodyBean> =
            object : Parcelable.Creator<ImagingEvaluationBodyBean> {
                override fun createFromParcel(source: Parcel): ImagingEvaluationBodyBean =
                    ImagingEvaluationBodyBean(source)

                override fun newArray(size: Int): Array<ImagingEvaluationBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}