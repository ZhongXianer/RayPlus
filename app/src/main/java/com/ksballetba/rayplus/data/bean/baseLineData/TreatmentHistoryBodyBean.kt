package com.ksballetba.rayplus.data.bean.baseLineData


import com.google.gson.annotations.SerializedName

data class TreatmentHistoryBodyBean(
    @SerializedName("is_change")
    val isChange:Int?,
    @SerializedName("is_gene")
    val isGene: Int?,
    @SerializedName("biopsy_method")
    val biopsyMethod: String?, // 其他
    @SerializedName("biopsy_method_other")
    val biopsyMethodOther: String?, // 其他活检
    @SerializedName("biopsy_type")
    val biopsyType: String?, // 2
    @SerializedName("biopsy_type_other")
    val biopsyTypeOther: String?, // 就是不一样
    @SerializedName("diagnose_existence")
    val diagnoseExistence: Int?, // 2
    @SerializedName("diagnose_method")
    val diagnoseMethod: DiagnoseMethod?,
    @SerializedName("diagnose_number")
    val diagnoseNumber: Int?, // 2
    @SerializedName("genetic_method")
    val geneticMethod: Int?, // 3
    @SerializedName("genetic_mutation_type")
    val geneticMutationType: GeneticMutationType?,
    @SerializedName("genetic_specimen")
    val geneticSpecimen: String?, // 3
    @SerializedName("genetic_specimen_other")
    val geneticSpecimenOther: String?, // 转移到了哪
    val id: Int?,
    @SerializedName("is_biopsy_again")
    val isBiopsyAgain: Int?, // 0
    @SerializedName("last_front_best_efficacy")
    val lastFrontBestEfficacy: Int?, // 0
    @SerializedName("last_front_part")
    val lastFrontPart: LastFrontPart?,
    @SerializedName("last_front_time")
    val lastFrontTime: String?, // 2020-01-14
    val msi: Int?, // 3
    @SerializedName("PDL1")
    val pDL1: Int?, // 2
    @SerializedName("start_time")
    val startTime: String?, // 2020-01-23
    val tmb: String?, // 2
    @SerializedName("tmb_other")
    val tmbOther: String? // 1000
) {
    data class DiagnoseMethod(
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
        val diagnoseMethodtargetedtherapyOther: String? // 靶向
    )

    data class GeneticMutationType(
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
        val geneticMutationType0: String? // on
    )

    data class LastFrontPart(
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
        val lastFrontPart2: String? // on
    )
}