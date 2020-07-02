package com.ksballetba.rayplus.data.bean.baseLineData


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TreatmentHistoryListBean(
    val code: Int, // 0
    val count: Int, // 200000
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        @SerializedName("biopsy_method")
        val biopsyMethod: String?, // 其他
        @SerializedName("biopsy_method_other")
        val biopsyMethodOther: String?, // 其他
        @SerializedName("biopsy_type")
        val biopsyType: String?, // 2
        @SerializedName("biopsy_type_other")
        val biopsyTypeOther: String?, // 2
        @SerializedName("diagnose_existence")
        val diagnoseExistence: Int?, // 2
        @SerializedName("diagnose_method[chemotherapy]")
        val diagnoseMethodchemotherapy: String?, // on
        @SerializedName("diagnose_method[chemotherapy]_other")
        val diagnoseMethodchemotherapyOther: String?, // 化疗
        @SerializedName("diagnose_method[immunotherapy]")
        val diagnoseMethodimmunotherapy: String?, // on
        @SerializedName("diagnose_method[immunotherapy]_other")
        val diagnoseMethodimmunotherapyOther: String?, // 免疫
        @SerializedName("diagnose_method[operation]")
        val diagnoseMethodoperation: String?, // on
        @SerializedName("diagnose_method[operation]_other")
        val diagnoseMethodoperationOther: String?, // 手术
        @SerializedName("diagnose_method[othertherapy]")
        val diagnoseMethodothertherapy: String?, // on
        @SerializedName("diagnose_method[othertherapy]_other")
        val diagnoseMethodothertherapyOther: String?, // 其他
        @SerializedName("diagnose_method[radiotherapy]")
        val diagnoseMethodradiotherapy: String?, // on
        @SerializedName("diagnose_method[radiotherapy]_other")
        val diagnoseMethodradiotherapyOther: String?, // 放疗
        @SerializedName("diagnose_method[targetedtherapy]")
        val diagnoseMethodtargetedtherapy: String?, // on
        @SerializedName("diagnose_method[targetedtherapy]_other")
        val diagnoseMethodtargetedtherapyOther: String?, // 靶向
        @SerializedName("diagnose_number")
        val diagnoseNumber: Int?, // 2
        @SerializedName("genetic_method")
        val geneticMethod: Int?, // 3
        @SerializedName("genetic_mutation_type[ALK]")
        val geneticMutationTypeALK: String?, // on
        @SerializedName("genetic_mutation_type[ALK]_other")
        val geneticMutationTypeALKOther: String?, // ALK描述
        @SerializedName("genetic_mutation_type[BRAF]")
        val geneticMutationTypeBRAF: String?, // on
        @SerializedName("genetic_mutation_type[EGFR]")
        val geneticMutationTypeEGFR: String?, // on
        @SerializedName("genetic_mutation_type[EGFR]_other")
        val geneticMutationTypeEGFROther: String?, // EGFR描述
        @SerializedName("genetic_mutation_type[ERBB2]")
        val geneticMutationTypeERBB2: String?, // on
        @SerializedName("genetic_mutation_type[Her-2]")
        val geneticMutationTypeHer2: String?, // on
        @SerializedName("genetic_mutation_type[KRAS]")
        val geneticMutationTypeKRAS: String?, // on
        @SerializedName("genetic_mutation_type[RET]")
        val geneticMutationTypeRET: String?, // on
        @SerializedName("genetic_mutation_type[ROS-1]")
        val geneticMutationTypeROS1: String?, // on
        @SerializedName("genetic_mutation_type[TP53]")
        val geneticMutationTypeTP53: String?, // on
        @SerializedName("genetic_mutation_type[cMET]")
        val geneticMutationTypecMET: String?, // on
        @SerializedName("genetic_mutation_type[不详]")
        val geneticMutationType1: String?, // on
        @SerializedName("genetic_mutation_type[无突变]")
        val geneticMutationType2: String?, // on
        @SerializedName("genetic_mutation_type[未测]")
        val geneticMutationType0: String?, // on
        @SerializedName("genetic_specimen")
        val geneticSpecimen: String?, // 其他
        @SerializedName("genetic_specimen_other")
        val geneticSpecimenOther: String?, // 3
        @SerializedName("is_biopsy_again")
        val isBiopsyAgain: Int?, // false
        @SerializedName("last_front_best_efficacy")
        val lastFrontBestEfficacy: Int?, // 0
        @SerializedName("last_front_part[primary_focus]")
        val lastFrontPartprimaryFocus: String?, // on
        @SerializedName("last_front_part[其他]")
        val lastFrontPart9: String?, // on
        @SerializedName("last_front_part[其他]_other")
        val lastFrontPartOther: String?, // 其他部位
        @SerializedName("last_front_part[四肢骨]")
        val lastFrontPart6: String?, // on
        @SerializedName("last_front_part[对侧肺门淋巴结]")
        val lastFrontPart1: String?, // on
        @SerializedName("last_front_part[肝]")
        val lastFrontPart7: String?, // on
        @SerializedName("last_front_part[肺内]")
        val lastFrontPart3: String?, // on
        @SerializedName("last_front_part[肾上腺]")
        val lastFrontPart8: String?, // on
        @SerializedName("last_front_part[脊柱骨]")
        val lastFrontPart5: String?, // on
        @SerializedName("last_front_part[脑]")
        val lastFrontPart4: String?, // on
        @SerializedName("last_front_part[锁骨上淋巴结肺内]")
        val lastFrontPart2: String?, // on
        @SerializedName("last_front_time")
        val lastFrontTime: String?, // 2020-01-14
        val msi: Int?, // 3
        @SerializedName("PDL1")
        val pDL1: Int?, // 2
        @SerializedName("sample_id")
        val sampleId: Int?, // 7
        @SerializedName("start_time")
        val startTime: String?, // 2020-01-23
        val tmb: String?, // 其他
        @SerializedName("tmb_other")
        val tmbOther: String?,// 2
        val id: Int
    ) : Parcelable {
        constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(biopsyMethod)
            writeString(biopsyMethodOther)
            writeString(biopsyType)
            writeString(biopsyTypeOther)
            writeValue(diagnoseExistence)
            writeString(diagnoseMethodchemotherapy)
            writeString(diagnoseMethodchemotherapyOther)
            writeString(diagnoseMethodimmunotherapy)
            writeString(diagnoseMethodimmunotherapyOther)
            writeString(diagnoseMethodoperation)
            writeString(diagnoseMethodoperationOther)
            writeString(diagnoseMethodothertherapy)
            writeString(diagnoseMethodothertherapyOther)
            writeString(diagnoseMethodradiotherapy)
            writeString(diagnoseMethodradiotherapyOther)
            writeString(diagnoseMethodtargetedtherapy)
            writeString(diagnoseMethodtargetedtherapyOther)
            writeValue(diagnoseNumber)
            writeValue(geneticMethod)
            writeString(geneticMutationTypeALK)
            writeString(geneticMutationTypeALKOther)
            writeString(geneticMutationTypeBRAF)
            writeString(geneticMutationTypeEGFR)
            writeString(geneticMutationTypeEGFROther)
            writeString(geneticMutationTypeERBB2)
            writeString(geneticMutationTypeHer2)
            writeString(geneticMutationTypeKRAS)
            writeString(geneticMutationTypeRET)
            writeString(geneticMutationTypeROS1)
            writeString(geneticMutationTypeTP53)
            writeString(geneticMutationTypecMET)
            writeString(geneticMutationType1)
            writeString(geneticMutationType2)
            writeString(geneticMutationType0)
            writeString(geneticSpecimen)
            writeString(geneticSpecimenOther)
            writeValue(isBiopsyAgain)
            writeValue(lastFrontBestEfficacy)
            writeString(lastFrontPartprimaryFocus)
            writeString(lastFrontPart9)
            writeString(lastFrontPartOther)
            writeString(lastFrontPart6)
            writeString(lastFrontPart1)
            writeString(lastFrontPart7)
            writeString(lastFrontPart3)
            writeString(lastFrontPart8)
            writeString(lastFrontPart5)
            writeString(lastFrontPart4)
            writeString(lastFrontPart2)
            writeString(lastFrontTime)
            writeValue(msi)
            writeValue(pDL1)
            writeValue(sampleId)
            writeString(startTime)
            writeString(tmb)
            writeString(tmbOther)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Data> = object : Parcelable.Creator<Data> {
                override fun createFromParcel(source: Parcel): Data =
                    Data(
                        source
                    )

                override fun newArray(size: Int): Array<Data?> = arrayOfNulls(size)
            }
        }
    }
}