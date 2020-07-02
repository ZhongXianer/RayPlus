package com.ksballetba.rayplus.data.bean


import com.google.gson.annotations.SerializedName

data class LabInspectionBodyBean(
    @SerializedName("ALB_rank")
    val aLBRank: Int?, // 2
    @SerializedName("ALB_val")
    val aLBVal: Float?, // 321
    @SerializedName("ALT_rank")
    val aLTRank: Int?, // 4
    @SerializedName("ALT_val")
    val aLTVal: Float?, // 123
    @SerializedName("AST_rank")
    val aSTRank: Int?, // 1
    @SerializedName("AST_val")
    val aSTVal: Float?, // 321
    @SerializedName("BUN_rank")
    val bUNRank: Int?, // 4
    @SerializedName("BUN_val")
    val bUNVal: Float?, // 321
    @SerializedName("CEA_rank")
    val cEARank: Int?, // 1
    @SerializedName("CEA_val")
    val cEAVal: Float?, // 123
    @SerializedName("Cr_rank")
    val crRank: Int?, // 3
    @SerializedName("Cr_val")
    val crVal: Float?, // 321
    @SerializedName("DBIL_rank")
    val dBILRank: Int?, // 2
    @SerializedName("DBIL_val")
    val dBILVal: Float?, // 312
    @SerializedName("Glu_rank")
    val gluRank: Int?, // 4
    @SerializedName("Glu_val")
    val gluVal: Float?, // 321
    @SerializedName("Hb_rank")
    val hbRank: Int?, // 3
    @SerializedName("Hb_val")
    val hbVal: Float?, // 12
    @SerializedName("NSE_rank")
    val nSERank: Int?, // 2
    @SerializedName("NSE_val")
    val nSEVal: Float?, // 123
    @SerializedName("PRO_rank")
    val pRORank: Int?, // 4
    @SerializedName("PRO_val")
    val pROVal: Float?, // 2
    @SerializedName("PT_rank")
    val pTRank: Int?, // 2
    @SerializedName("PT_val")
    val pTVal: Float?, // 123
    @SerializedName("Plt_rank")
    val pltRank: Int?, // 2
    @SerializedName("Plt_val")
    val pltVal: Float?, // 123
    @SerializedName("RBC_B_rank")
    val rBCBRank: Int?, // 1
    @SerializedName("RBC_B_val")
    val rBCBVal: Float?, // 132
    @SerializedName("RBC_P_rank")
    val rBCPRank: Int?, // 3
    @SerializedName("RBC_P_val")
    val rBCPVal: Float?, // 123
    @SerializedName("SCC_rank")
    val sCCRank: Int?, // 1
    @SerializedName("SCC_val")
    val sCCVal: Float?, // 123
    @SerializedName("TBIL_rank")
    val tBILRank: Int?, // 1
    @SerializedName("TBIL_val")
    val tBILVal: Float?, // 213
    val time: String, // 2019-12-04
    @SerializedName("WBC_B_rank")
    val wBCBRank: Int?, // 1
    @SerializedName("WBC_B_val")
    val wBCBVal: Float?, // 12132100
    @SerializedName("WBC_P_rank")
    val wBCPRank: Int?, // 3
    @SerializedName("WBC_P_val")
    val wBCPVal: Float?,
    @SerializedName("K_rank")
    val kRank: Int?,
    @SerializedName("K_val")
    val kVal:Float?,
    @SerializedName("Na_rank")
    val naRank: Int?,
    @SerializedName("Na_val")
    val naVal:Float?,
    @SerializedName("Cl_rank")
    val clRank: Int?,
    @SerializedName("Cl_val")
    val clVal:Float?,
    @SerializedName("P_rank")
    val pRank: Int?,
    @SerializedName("P_val")
    val pVal:Float?
)