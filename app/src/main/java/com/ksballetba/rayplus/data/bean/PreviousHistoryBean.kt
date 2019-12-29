package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class PreviousHistoryBean(
    @SerializedName("base_ill")
    val baseIll: BaseIll,
    val drinking: Drinking,
    @SerializedName("drug_allergy")
    val drugAllergy: String, // 不详
    @SerializedName("drug_allergy_other")
    val drugAllergyOther: String,
    @SerializedName("drug_use")
    val drugUse: String, // 其他
    @SerializedName("drug_use_other")
    val drugUseOther: String, // 25
    @SerializedName("ECOG")
    val eCOG: Int, // 456
    val height: Int, // 45
    val smoke: Smoke,
    @SerializedName("surface_area")
    val surfaceArea: Int, // 666
    val surgery: String, // 其他
    @SerializedName("surgery_other")
    val surgeryOther: String, // 456546
    @SerializedName("tumor_ill")
    val tumorIll: String, // 不详
    @SerializedName("tumor_ill_other")
    val tumorIllOther: String,
    val weight: Int // 45
) {
    data class BaseIll(
        @SerializedName("base_ill_other")
        val baseIllOther: String, // 456456
        @SerializedName("base_ill[无]")
        val baseIll1: String, // on
        @SerializedName("base_ill[不详]")
        val baseIll2: String, // on
        @SerializedName("base_ill[高血压]")
        val baseIll3: String, // on
        @SerializedName("base_ill[冠心病]")
        val baseIll4: String, // on
        @SerializedName("base_ill[糖尿病]")
        val baseIll5: String, // on
        @SerializedName("base_ill[慢性阻塞性肺疾病]")
        val baseIll6: String, // on
        @SerializedName("base_ill[支气管哮喘]")
        val baseIll7: String, // on
        @SerializedName("base_ill[肺结核]")
        val baseIll8: String, // on
        @SerializedName("base_ill[间质性肺疾病]")
        val baseIll9: String, // on
        @SerializedName("base_ill[高脂血症]")
        val baseIll10: String, // on
        @SerializedName("base_ill[病毒性肝炎]")
        val baseIll11: String, // on
        @SerializedName("base_ill[风湿免疫性疾病]")
        val baseIll12: String, //
        @SerializedName("base_ill[肾脏病]")
        val baseIll13: String //
    )

    data class Drinking(
        val drinking: String, // on
        @SerializedName("drinking_chemotherapy")
        val drinkingChemotherapy: String, // on
        @SerializedName("drinking_frequence")
        val drinkingFrequence: String, // 1
        @SerializedName("drinking_is_quit")
        val drinkingIsQuit: String, // on
        @SerializedName("drinking_quit_time")
        val drinkingQuitTime: String, // 2019-12-10
        @SerializedName("drinking_size")
        val drinkingSize: String // 1
    )

    data class Smoke(
        val smoke: String, // on
        @SerializedName("smoke_chemotherapy")
        val smokeChemotherapy: String, // on
        @SerializedName("smoke_isquit")
        val smokeIsquit: String, // on
        @SerializedName("smoke_quit_time")
        val smokeQuitTime: String, // 2019-12-25
        @SerializedName("smoke_size")
        val smokeSize: String, // 45
        @SerializedName("smoke_year")
        val smokeYear: String // 45
    )
}