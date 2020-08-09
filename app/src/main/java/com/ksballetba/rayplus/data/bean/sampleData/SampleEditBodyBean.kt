package com.ksballetba.rayplus.data.bean.sampleData


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SampleEditBodyBean(
    val date: String?, // 2019-12-02
    @SerializedName("group_id")
    val groupId: Int, // 1
    @SerializedName("id_num")
    val idNum: String, // 452501111111111111
    @SerializedName("in_group_time")
    val inGroupTime: String, // 2019-12-25
    @SerializedName("patient_ids")
    val patientIds: String, // AA-11
    @SerializedName("patient_name")
    val patientName: String, // 李雷
//    @SerializedName("project_id")
//    val projectId: Int?,
    @SerializedName("research_center_id")
    val researchCenterId: Int?, // 1
    @SerializedName("sample_id")
    val sampleId: Int? = null, // null
    val sex: Int, // 0
    @SerializedName("sign_time")
    val signTime: String // 2019-12-02
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()?:"",
        source.readInt(),
        source.readString()?:"",
        source.readString()?:"",
        source.readString()?:"",
        source.readString()?:"",
//        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readInt(),
        source.readString()?:""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(date)
        writeInt(groupId)
        writeString(idNum)
        writeString(inGroupTime)
        writeString(patientIds)
        writeString(patientName)
//        writeValue(projectId)
        writeValue(researchCenterId)
        writeValue(sampleId)
        writeInt(sex)
        writeString(signTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SampleEditBodyBean> =
            object : Parcelable.Creator<SampleEditBodyBean> {
                override fun createFromParcel(source: Parcel): SampleEditBodyBean =
                    SampleEditBodyBean(
                        source
                    )

                override fun newArray(size: Int): Array<SampleEditBodyBean?> = arrayOfNulls(size)
            }
    }
}