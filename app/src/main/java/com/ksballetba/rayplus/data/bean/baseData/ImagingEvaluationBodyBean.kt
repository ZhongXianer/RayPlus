package com.ksballetba.rayplus.data.bean.baseData

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImagingEvaluationBodyBean(
    @SerializedName("evaluate_id")
    var evaluateId: Int?,
    var method: String?,
    @SerializedName("method_other")
    var methodOther: String?,
    var part: String?,
    var time: String?,
    @SerializedName("tumor_desc")
    var tumorDesc: String?,
    @SerializedName("tumor_long")
    var tumorLong: String?,
    @SerializedName("tumor_short")
    var tumorShort: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(evaluateId)
        writeString(method)
        writeString(methodOther)
        writeString(part)
        writeString(time)
        writeString(tumorDesc)
        writeString(tumorLong)
        writeString(tumorShort)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImagingEvaluationBodyBean> =
            object : Parcelable.Creator<ImagingEvaluationBodyBean> {
                override fun createFromParcel(source: Parcel): ImagingEvaluationBodyBean =
                    ImagingEvaluationBodyBean(
                        source
                    )

                override fun newArray(size: Int): Array<ImagingEvaluationBodyBean?> =
                    arrayOfNulls(size)
            }
    }
}