package com.ksballetba.rayplus.data.bean.baseLineData


import com.google.gson.annotations.SerializedName

data class FirstVisitProcessResponseBean(
    val code: Int,
    val data: Data,
    val msg: String
) {
    data class Data(
        @SerializedName("is_gene")
        val isGene: Int,
        @SerializedName("biopsy_method")
        val biopsyMethod: String?, // 其他
        @SerializedName("biopsy_method_other")
        val biopsyMethodOther: String, // 其他活检
        @SerializedName("clinical_symptoms[不详]")
        val clinicalSymptoms9: String, // on
        @SerializedName("clinical_symptoms[体重下降]")
        val clinicalSymptoms8: String, // on
        @SerializedName("clinical_symptoms[其他]")
        val clinicalSymptoms10: String, // on
        @SerializedName("clinical_symptoms[其他]_other")
        val clinicalSymptomsOther: String?, // halo
        @SerializedName("clinical_symptoms[发热]")
        val clinicalSymptoms3: String, // on
        @SerializedName("clinical_symptoms[咳嗽]")
        val clinicalSymptoms0: String, // on
        @SerializedName("clinical_symptoms[咳痰]")
        val clinicalSymptoms1: String, // on
        @SerializedName("clinical_symptoms[咳血]")
        val clinicalSymptoms2: String, // on
        @SerializedName("clinical_symptoms[喘气]")
        val clinicalSymptoms6: String, // on
        @SerializedName("clinical_symptoms[消瘦]")
        val clinicalSymptoms7: String, // on
        @SerializedName("clinical_symptoms[胸痛]")
        val clinicalSymptoms5: String, // on
        @SerializedName("clinical_symptoms[胸闷]")
        val clinicalSymptoms4: String, // on
        @SerializedName("gene_mutation_type[ALK]")
        val geneMutationTypeALK: String, // on
        @SerializedName("gene_mutation_type[ALK]_other")
        val geneMutationTypeALKOther: String, // ALK描述
        @SerializedName("gene_mutation_type[BRAF]")
        val geneMutationTypeBRAF: String, // on
        @SerializedName("gene_mutation_type[EGFR]")
        val geneMutationTypeEGFR: String, // on
        @SerializedName("gene_mutation_type[EGFR]_other")
        val geneMutationTypeEGFROther: String, // EGFR描述
        @SerializedName("gene_mutation_type[ERBB2]")
        val geneMutationTypeERBB2: String, // on
        @SerializedName("gene_mutation_type[Her-2]")
        val geneMutationTypeHer2: String, // on
        @SerializedName("gene_mutation_type[KRAS]")
        val geneMutationTypeKRAS: String, // on
        @SerializedName("gene_mutation_type[RET]")
        val geneMutationTypeRET: String, // on
        @SerializedName("gene_mutation_type[ROS-1]")
        val geneMutationTypeROS1: String, // on
        @SerializedName("gene_mutation_type[TP53]")
        val geneMutationTypeTP53: String, // on
        @SerializedName("gene_mutation_type[cMET]")
        val geneMutationTypecMET: String, // on
        @SerializedName("gene_mutation_type[不详]")
        val geneMutationType1: String, // on
        @SerializedName("gene_mutation_type[无突变]")
        val geneMutationType2: String, // on
        @SerializedName("gene_mutation_type[未测]")
        val geneMutationType0: String, // on
        @SerializedName("genetic_testing_method")
        val geneticTestingMethod: Int?, // 1
        @SerializedName("genetic_testing_specimen")
        val geneticTestingSpecimen: String?, // 转移灶组织
        @SerializedName("genetic_testing_specimen_other")
        val geneticTestingSpecimenOther: String, // 其他转移灶
        val msi: Int?, // 3
        val pdl1: Int?, // 5
        @SerializedName("sample_id")
        val sampleId: Int, // 7
        val tmb: String?, // 其他
        @SerializedName("tmb_other")
        val tmbOther: String, // 100
        @SerializedName("transfer_site[其他骨]")
        val transferSite9: String,
        @SerializedName("transfer_site[其他]")
        val transferSite10: String, // on
        @SerializedName("transfer_site[其他]_other")
        val transferSiteOther: String?, // 其他部位
        @SerializedName("transfer_site[四肢骨]")
        val transferSite6: String, // on
        @SerializedName("transfer_site[对侧肺门淋巴结]")
        val transferSite1: String, // on
        @SerializedName("transfer_site[无]")
        val transferSite0: String, // on
        @SerializedName("transfer_site[肝]")
        val transferSite7: String, // on
        @SerializedName("transfer_site[肺内]")
        val transferSite3: String, // on
        @SerializedName("transfer_site[肾上腺]")
        val transferSite8: String, // on
        @SerializedName("transfer_site[脊柱骨]")
        val transferSite5: String, // on
        @SerializedName("transfer_site[脑]")
        val transferSite4: String, // on
        @SerializedName("transfer_site[锁骨上淋巴结肺内]")
        val transferSite2: String, // on
        @SerializedName("tumor_part")
        val tumorPart: Int?, // 0
        @SerializedName("tumor_pathological_type")
        val tumorPathologicalType: String?, // 混合型癌
        @SerializedName("tumor_pathological_type_other")
        val tumorPathologicalTypeOther: String // 其他癌
    )
}

