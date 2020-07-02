package com.ksballetba.rayplus.data.bean.baseLineData


import com.google.gson.annotations.SerializedName

data class PreviousHistoryBodyBean(
    @SerializedName("base_ill")
    val baseIll: BaseIll,
    val drinking: Drinking?,
    @SerializedName("drug_allergy")
    val drugAllergy: String?, // 其他
    @SerializedName("drug_allergy_other")
    val drugAllergyOther: String?, // 板蓝根
    @SerializedName("drug_use")
    val drugUse: String?, // 其他
    @SerializedName("drug_use_other")
    val drugUseOther: String?, // 板蓝根
    @SerializedName("ECOG")
    val eCOG: Int?, // 2
    val height: Int?, // 180
    val smoke: Smoke?,
    @SerializedName("surface_area")
    val surfaceArea: Int?, // 200
    val surgery: String?, // 其他
    @SerializedName("surgery_other")
    val surgeryOther: String?,
    @SerializedName("tumor_ill")
    val tumorIll: String?, // 其他
    @SerializedName("tumor_ill_other")
    val tumorIllOther: String?, // 脑
    val weight: Int? // 80
) {
    data class BaseIll(
        @SerializedName("base_ill_other")
        val baseIllOther: String?, // 其他疾病
        @SerializedName("base_ill[不详]")
        val baseIll1: String?, // on
        @SerializedName("base_ill[其他]")
        val baseIll13: String?, // on
        @SerializedName("base_ill[冠心病]")
        val baseIll3: String?, // on
        @SerializedName("base_ill[慢性阻塞性肺疾病]")
        val baseIll5: String?, // on
        @SerializedName("base_ill[支气管哮喘]")
        val baseIll6: String?, // on
        @SerializedName("base_ill[无]")
        val baseIll0: String?, // on
        @SerializedName("base_ill[病毒性肝炎]")
        val baseIll10: String?, // on
        @SerializedName("base_ill[糖尿病]")
        val baseIll4: String?, // on
        @SerializedName("base_ill[肺结核]")
        val baseIll7: String?, // on
        @SerializedName("base_ill[肾脏病]")
        val baseIll12: String?, // on
        @SerializedName("base_ill[间质性肺疾病]")
        val baseIll8: String?, // on
        @SerializedName("base_ill[风湿免疫性疾病]")
        val baseIll11: String?, // on
        @SerializedName("base_ill[高脂血症]")
        val baseIll9: String?, // on
        @SerializedName("base_ill[高血压]")
        val baseIll2: String? // on
    )

    data class Drinking(
        val drinking: String?, // on
        @SerializedName("drinking_chemotherapy")
        val drinkingChemotherapy: String?, // on
        @SerializedName("drinking_frequence")
        val drinkingFrequence: String?, // 1
        @SerializedName("drinking_is_quit")
        val drinkingIsQuit: String?, // on
        @SerializedName("drinking_quit_time")
        val drinkingQuitTime: String?, // 2020-01-15
        @SerializedName("drinking_size")
        val drinkingSize: String? // 1
    )

    data class Smoke(
        val smoke: String?, // on
        @SerializedName("smoke_chemotherapy")
        val smokeChemotherapy: String?, // on
        @SerializedName("smoke_isquit")
        val smokeIsquit: String?, // on
        @SerializedName("smoke_quit_time")
        val smokeQuitTime: String?, // 2020-01-07
        @SerializedName("smoke_size")
        val smokeSize: String?, // 1
        @SerializedName("smoke_year")
        val smokeYear: String? // 2
    )
}