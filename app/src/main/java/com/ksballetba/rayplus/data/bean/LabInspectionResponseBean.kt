package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class LabInspectionResponseBean(
    @SerializedName("ALB_rank")
    val aLBRank: Int?, // 2
    @SerializedName("ALB_val")
    val aLBVal: Int?, // 321
    @SerializedName("ALT_rank")
    val aLTRank: Int?, // 4
    @SerializedName("ALT_val")
    val aLTVal: Int?, // 123
    @SerializedName("AST_rank")
    val aSTRank: Int?, // 1
    @SerializedName("AST_val")
    val aSTVal: Int?, // 321
    @SerializedName("BUN_rank")
    val bUNRank: Int?, // 4
    @SerializedName("BUN_val")
    val bUNVal: Int?, // 321
    @SerializedName("CEA_rank")
    val cEARank: Int?, // 1
    @SerializedName("CEA_val")
    val cEAVal: Int?, // 123
    @SerializedName("Cr_rank")
    val crRank: Int?, // 3
    @SerializedName("Cr_val")
    val crVal: Int?, // 321
    @SerializedName("cycle_id")
    val cycleId: Int?, // 5
    @SerializedName("DBIL_rank")
    val dBILRank: Int?, // 2
    @SerializedName("DBIL_val")
    val dBILVal: Int?, // 312
    @SerializedName("Glu_rank")
    val gluRank: Int?, // 4
    @SerializedName("Glu_val")
    val gluVal: Int?, // 321
    @SerializedName("Hb_rank")
    val hbRank: Int?, // 3
    @SerializedName("Hb_val")
    val hbVal: Int?, // 12
    @SerializedName("NSE_rank")
    val nSERank: Int?, // 2
    @SerializedName("NSE_val")
    val nSEVal: Int?, // 123
    @SerializedName("PRO_rank")
    val pRORank: Int?, // 4
    @SerializedName("PRO_val")
    val pROVal: Int?, // 2
    @SerializedName("PT_rank")
    val pTRank: Int?, // 2
    @SerializedName("PT_val")
    val pTVal: Int?, // 123
    @SerializedName("Plt_rank")
    val pltRank: Int?, // 2
    @SerializedName("Plt_val")
    val pltVal: Int?, // 123
    @SerializedName("RBC_B_rank")
    val rBCBRank: Int?, // 1
    @SerializedName("RBC_B_val")
    val rBCBVal: Int?, // 132
    @SerializedName("RBC_P_rank")
    val rBCPRank: Int?, // 3
    @SerializedName("RBC_P_val")
    val rBCPVal: Int?, // 123
    @SerializedName("SCC_rank")
    val sCCRank: Int?, // 1
    @SerializedName("SCC_val")
    val sCCVal: Int?, // 123
    @SerializedName("sample_id")
    val sampleId: Int?, // 5
    @SerializedName("TBIL_rank")
    val tBILRank: Int?, // 1
    @SerializedName("TBIL_val")
    val tBILVal: Int?, // 213
    val time: String, // 2019-12-04
    @SerializedName("WBC_B_rank")
    val wBCBRank: Int?, // 1
    @SerializedName("WBC_B_val")
    val wBCBVal: Int?, // 12132100
    @SerializedName("WBC_P_rank")
    val wBCPRank: Int?, // 3
    @SerializedName("WBC_P_val")
    val wBCPVal: Int? // 123
)