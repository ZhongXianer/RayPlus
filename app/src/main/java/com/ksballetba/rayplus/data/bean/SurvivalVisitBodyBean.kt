package com.ksballetba.rayplus.data.bean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SurvivalVisitBodyBean(
    @SerializedName("die_reason")
    val dieReason: Int?, // 1
    @SerializedName("die_time")
    val dieTime: String?, // 2019-12-17
    @SerializedName("has_other_treatment")
    val hasOtherTreatment: Int?, // 0
    @SerializedName("interview_id")
    val interviewId: Int?, // 1
    @SerializedName("interview_time")
    val interviewTime: String?, // 2019-12-10
    @SerializedName("interview_way")
    val interviewWay: Int?, // 2
    @SerializedName("last_time_survival")
    val lastTimeSurvival: String?, // 2019-12-23
    @SerializedName("OS_method")
    val oSMethod: Int?, // 8
    @SerializedName("other_method")
    val otherMethod: String?, // 无
    @SerializedName("other_reason")
    val otherReason: String?, // 老死
    @SerializedName("status_confirm_time")
    val statusConfirmTime: String?, // 2019-12-27
    @SerializedName("survival_status")
    val survivalStatus: Int?,// 1
    @SerializedName("is_submit")
    val isSubmit: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(dieReason)
        writeString(dieTime)
        writeValue(hasOtherTreatment)
        writeValue(interviewId)
        writeString(interviewTime)
        writeValue(interviewWay)
        writeString(lastTimeSurvival)
        writeValue(oSMethod)
        writeString(otherMethod)
        writeString(otherReason)
        writeString(statusConfirmTime)
        writeValue(survivalStatus)
        writeValue(isSubmit)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SurvivalVisitBodyBean> =
            object : Parcelable.Creator<SurvivalVisitBodyBean> {
                override fun createFromParcel(source: Parcel): SurvivalVisitBodyBean =
                    SurvivalVisitBodyBean(source)

                override fun newArray(size: Int): Array<SurvivalVisitBodyBean?> = arrayOfNulls(size)
            }
    }
}