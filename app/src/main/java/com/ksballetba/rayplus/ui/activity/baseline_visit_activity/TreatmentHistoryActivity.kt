package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.text.isDigitsOnly
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.baseLineData.TreatmentHistoryBodyBean
import com.ksballetba.rayplus.data.bean.baseLineData.TreatmentHistoryListBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment.TreatmentHistoryFragment.Companion.TREATMENT_HISTORY_BODY
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_treatment_history.*
import org.jetbrains.anko.toast

class TreatmentHistoryActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TreatmentHistoryActivity"
        const val REFRESH_TREATMENT_HISTORY_PAGE = "REFRESH_TREATMENT_HISTORY_PAGE"
    }

    lateinit var mViewModel: BaselineVisitViewModel

    private var mGeneMutationTypeList = mutableListOf<BaseCheckBean>()
    private var mLastFrontPartList = mutableListOf<BaseCheckBean>()

    private var mOtherGeneMutationTypeEGFR: String? = ""
    private var mOtherGeneMutationTypeALK: String? = ""
    private var mOtherLastFrontPart: String? = ""
    private var mId: Int? = null

    private var biopsyMethodIsSet: Boolean = false
    private var biopsyTypeIsSet: Boolean = false
    private var geneticSpecimenIsSet: Boolean = false
    private var tmbIsSet: Boolean = false
    private var isNull: Boolean = false

    private lateinit var mTreatmentHistoryBean: TreatmentHistoryListBean.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_history)
        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        setSupportActionBar(tb_treatment_history)
        mViewModel = getBaselineVisitViewModel(this)
        val treatmentHistoryBody =
            intent.getParcelableExtra<TreatmentHistoryListBean.Data>(TREATMENT_HISTORY_BODY)
        if (treatmentHistoryBody != null) {
            mId = treatmentHistoryBody.id
            mTreatmentHistoryBean = treatmentHistoryBody
            loadData(treatmentHistoryBody)
        } else {
            //初始表格设置为不可见
            isNull = true
            ll_treatment_history_detail.visibility = View.GONE
            initEmptyLastFrontPart()
            initEmptyGeneMutationType()
        }
        th_switch_is_genetic.setOnCheckedChangeListener { _, isChecked ->
            th_switch_is_genetic.setSwitchTextAppearance(
                this,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            th_genetic_view.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        th_switch_is_change.setOnCheckedChangeListener { _, isChecked ->
            th_switch_is_change.setSwitchTextAppearance(
                this,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            th_is_change_view.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        cl_treatment_line.setOnClickListener {
            XPopup.Builder(this).asCenterList("几线治疗", getDiagnoseNumber()) { pos, text ->
                tv_treatment_line.text = text
                when (pos) {
                    0 -> {
                        ll_not_first_line_treatment.visibility = View.GONE
                        tv_first_line_treatment_title.text = "一线治疗"
                    }
                    1 -> {
                        ll_not_first_line_treatment.visibility = View.VISIBLE
                        tv_first_line_treatment_title.text = "二线治疗"
                        tv_last_best_treatment_title.text = "一线治疗最佳疗效"
                        tv_last_treatment_growth_date_title.text = "一线治疗进展时间"
                        tv_last_treatment_growth_part.text = "一线治疗进展部位"
                        tv_treatment_date_title.text = "二线治疗开始时间"
                    }
                    2 -> {
                        ll_not_first_line_treatment.visibility = View.VISIBLE
                        tv_first_line_treatment_title.text = "三线治疗"
                        tv_last_best_treatment_title.text = "二线治疗最佳疗效"
                        tv_last_treatment_growth_date_title.text = "二线治疗进展时间"
                        tv_last_treatment_growth_part_title.text = "二线治疗进展部位"
                        tv_treatment_date_title.text = "三线治疗开始时间"
                    }
                    3 -> {
                        ll_not_first_line_treatment.visibility = View.VISIBLE
                        tv_first_line_treatment_title.text = "四线治疗"
                        tv_last_best_treatment_title.text = "三线治疗最佳疗效"
                        tv_last_treatment_growth_date_title.text = "三线治疗进展时间"
                        tv_last_treatment_growth_part_title.text = "三线治疗进展部位"
                        tv_treatment_date_title.text = "四线治疗开始时间"
                    }
                    4 -> {
                        ll_not_first_line_treatment.visibility = View.VISIBLE
                        tv_first_line_treatment_title.text = "五线治疗"
                        tv_last_best_treatment_title.text = "四线治疗最佳疗效"
                        tv_last_treatment_growth_date_title.text = "四线治疗进展时间"
                        tv_last_treatment_growth_part_title.text = "四线治疗进展部位"
                        tv_treatment_date_title.text = "五线治疗开始时间"
                    }
                }
            }.show()
        }
        cl_first_line_treatment.setOnClickListener {
            XPopup.Builder(this).asCenterList("治疗", getDiagnoseExistence()) { pos, text ->
                if (pos < 2) {
                    ll_treatment_history_detail.visibility = View.GONE
                } else {
                    ll_treatment_history_detail.visibility = View.VISIBLE
                }
                tv_first_line_treatment.text = text
            }.show()
        }
        cl_last_best_treatment.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "治疗最佳疗效",
                arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(PD)", "疗效不详(UK)")
            ) { _, text ->
                tv_last_best_treatment.text = text
            }.show()
        }
        cl_last_treatment_growth_date.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("治疗开始时间", "请输入时间，格式YYYY-MM-DD、YYYY-MM、YYYY") {
                if (checkDate(it))
                    tv_last_treatment_growth_date.text = it
                else ToastUtils.showShort("请重新输入！")
            }.show()
        }
        cl_last_treatment_growth_part.setOnClickListener {
            val transferSite = StringBuffer()
            asCheckboxList(this, "治疗进展部位", mLastFrontPartList, { data, _ ->
                if (data.name == "其他") {
                    XPopup.Builder(this).asInputConfirm("治疗进展部位", "请输入其他治疗进展部位") {
                        mOtherLastFrontPart = it
                    }.show()
                }
            }, { checkedData ->
                checkedData.forEach {
                    if (it.isChecked && it.name != "其他") {
                        transferSite.append("${it.name},")
                    }
                }
                if (transferSite.isNotEmpty() && mOtherLastFrontPart.isNullOrBlank()) {
                    transferSite.deleteCharAt(transferSite.length - 1)
                }
                transferSite.append(mOtherLastFrontPart)
                tv_last_treatment_growth_part.text = transferSite
            }).show()
        }
        cl_is_biopsy_again.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(getString(R.string.is_biopsy_again), arrayOf("是", "否")) { _, text ->
                    tv_is_biopsy_again.text = text
                }.show()
        }
        cl_biopsy_way.setOnClickListener {
            biopsyMethodIsSet = true
            XPopup.Builder(this).asCenterList(
                getString(R.string.biopsy_way),
                arrayOf("无", "手术", "胸腔镜", "纵膈镜", "经皮肺穿刺", "纤支镜", "E-BUS", "EUS-FNA", "淋巴结活检", "其他")
            ) { pos, text ->
                if (pos < 9) {
                    tv_biopsy_way.text = text
                } else {
                    XPopup.Builder(this)
                        .asInputConfirm(getString(R.string.biopsy_way), "请输入其他活检方式") {
                            tv_biopsy_way.text = it
                        }.show()
                }
            }.show()
        }
        cl_biopsy_pathological_type.setOnClickListener {
            biopsyTypeIsSet = true
            XPopup.Builder(this).asCenterList(
                getString(R.string.biopsy_pathological_type),
                arrayOf("无", "与第1次活检病理类型一致", "与第1次活检病理类型不一致")
            ) { pos, text ->
                if (pos < 2) {
                    tv_biopsy_pathological_type.text = text
                } else {
                    XPopup.Builder(this)
                        .asInputConfirm(getString(R.string.biopsy_pathological_type), "请输入其他病理类型") {
                            tv_biopsy_pathological_type.text = it
                        }.show()
                }
            }.show()
        }
        cl_genetic_test_sample.setOnClickListener {
            geneticSpecimenIsSet = true
            XPopup.Builder(this).asCenterList(
                getString(R.string.genetic_test_sample),
                arrayOf("无", "外周血", "原发灶组织", "转移灶组织")
            ) { pos, text ->
                if (pos < 3) {
                    tv_genetic_test_sample.text = text
                } else {
                    XPopup.Builder(this)
                        .asInputConfirm(getString(R.string.genetic_test_sample), "请输入其他基因检测标本") {
                            tv_genetic_test_sample.text = it
                        }.show()
                }
            }.show()
        }
        cl_genetic_test_way.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.genetic_test_way),
                arrayOf("无", "ARMS", "FISH", "二代测序")
            ) { _, text ->
                tv_genetic_test_way.text = text
            }.show()
        }
        cl_genetic_mutation_type.setOnClickListener {
            val geneMutationType = StringBuffer()
            asCheckboxList(this, "基因突变类型", mGeneMutationTypeList, { data, _ ->
                if (data.name == "EGFR" || data.name == "ALK") {
                    XPopup.Builder(this).asInputConfirm("基因突变类型", "请输入${data.name}描述") {
                        when (data.name) {
                            "EGFR" -> {
                                mOtherGeneMutationTypeEGFR = it
                            }
                            "ALK" -> {
                                mOtherGeneMutationTypeALK = it
                            }
                        }
                    }.show()
                }
            }, { checkedData ->
                checkedData.forEach {
                    if (it.isChecked) {
                        geneMutationType.append("${it.name},")
                    }
                }
                if (!mOtherGeneMutationTypeEGFR.isNullOrBlank()) {
                    geneMutationType.append("EGFR描述:$mOtherGeneMutationTypeEGFR,")
                }
                if (!mOtherGeneMutationTypeALK.isNullOrBlank()) {
                    geneMutationType.append("ALK描述:$mOtherGeneMutationTypeALK")
                }
                tv_genetic_mutation_type.text = geneMutationType
            }).show()
        }
        cl_PD_L1_expression.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(
                    "PD-L1表达",
                    arrayOf("未测", "不详", ">50%", "1%-50%", "<1%", "阴性")
                ) { _, text ->
                    tv_PD_L1_expression.text = text
                }.show()
        }
        cl_tumor_mutation_load.setOnClickListener {
            tmbIsSet = true
            XPopup.Builder(this)
                .asCenterList("肿瘤突变负荷(TMB)", arrayOf("未测", "不详", "数量（个突变/Mb）")) { pos, text ->
                    if (pos < 2) {
                        tv_tumor_mutation_load.text = text
                    } else {
                        XPopup.Builder(this).asInputConfirm("肿瘤突变负荷数量", "请输入数量（个突变/Mb）") {
                            tv_tumor_mutation_load.text = it
                        }.show()
                    }
                }.show()
        }
        cl_microsatellite_instability.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(
                    "微卫星不稳定性(MSI)",
                    arrayOf("未测", "不详", "微卫星稳定性", "微卫星不稳定性")
                ) { _, text ->
                    tv_microsatellite_instability.text = text
                }.show()
        }
        cl_treatment_date.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("一线治疗开始时间", "请输入时间，格式YYYY-MM-DD、YYYY-MM、YYYY") {
                if (checkDate(it)) tv_treatment_date.text = it
                else ToastUtils.showShort("请重新输入！")
            }.show()
        }
        cl_operation.setOnClickListener {
            cb_operation.isChecked = !cb_operation.isChecked
        }
        cl_radiotherapy.setOnClickListener {
            cb_radiotherapy.isChecked = !cb_radiotherapy.isChecked
        }
        cl_chemotherapy.setOnClickListener {
            cb_chemotherapy.isChecked = !cb_chemotherapy.isChecked
        }
        cl_targeted_therapy.setOnClickListener {
            cb_targeted_therapy.isChecked = !cb_targeted_therapy.isChecked
        }
        cl_immunotherapy.setOnClickListener {
            cb_immunotherapy.isChecked = !cb_immunotherapy.isChecked
        }
        cl_other_therapy.setOnClickListener {
            cb_other_therapy.isChecked = !cb_other_therapy.isChecked
        }
        cb_operation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("手术", "请输入手术（手术部位及方式）") {
                    tv_operation.text = it
                }.show()
            }
        }
        cb_radiotherapy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("放疗", "请输入放疗（放疗部位及剂量）") {
                    tv_radiotherapy.text = it
                }.show()
            }
        }
        cb_chemotherapy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("化疗", "请输入化疗（药名，使用剂量及频率，副作用）") {
                    tv_chemotherapy.text = it
                }.show()
            }
        }
        cb_targeted_therapy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("靶向治疗", "请输入靶向治疗（药名，使用剂量及频率，副作用）") {
                    tv_targeted_therapy.text = it
                }.show()
            }
        }
        cb_immunotherapy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("免疫治疗", "请输入免疫治疗（药名，使用剂量及频率，副作用）") {
                    tv_immunotherapy.text = it
                }.show()
            }
        }
        cb_other_therapy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XPopup.Builder(this).asInputConfirm("其他治疗", "请输入其他治疗（药名，使用剂量及频率，副作用）") {
                    tv_other_therapy.text = it
                }.show()
            }
        }
        fab_save_treatment_history.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
            addOrEditTreatmentHistory(sampleId)
        }
    }

    private fun addOrEditTreatmentHistory(sampleId: Int) {
        val diagnoseNumberStr = parseDefaultContent(tv_treatment_line.text.toString())
        val diagnoseNumber =
            if (diagnoseNumberStr.isEmpty()) null else getDiagnoseNumber().indexOf(diagnoseNumberStr) + 1
        val diagnoseExistenceStr = parseDefaultContent(tv_first_line_treatment.text.toString())
        val diagnoseExistence =
            if (diagnoseExistenceStr.isEmpty()) null else getDiagnoseExistence().indexOf(
                diagnoseExistenceStr
            )
        val lastFrontBestEfficacyStr = parseDefaultContent(tv_last_best_treatment.text.toString())
        val lastFrontBestEfficacy =
            if (lastFrontBestEfficacyStr.isEmpty()) null else getLastFrontBestEfficacyList().indexOf(
                lastFrontBestEfficacyStr
            )
        val lastFrontTime = parseDefaultContent(tv_last_treatment_growth_date.text.toString())
        val lastFrontPartprimaryFocus = if (mLastFrontPartList[0].isChecked) "on" else null
        val lastFrontPart1 = if (mLastFrontPartList[1].isChecked) "on" else null
        val lastFrontPart2 = if (mLastFrontPartList[2].isChecked) "on" else null
        val lastFrontPart3 = if (mLastFrontPartList[3].isChecked) "on" else null
        val lastFrontPart4 = if (mLastFrontPartList[4].isChecked) "on" else null
        val lastFrontPart5 = if (mLastFrontPartList[5].isChecked) "on" else null
        val lastFrontPart6 = if (mLastFrontPartList[6].isChecked) "on" else null
        val lastFrontPart7 = if (mLastFrontPartList[7].isChecked) "on" else null
        val lastFrontPart8 = if (mLastFrontPartList[8].isChecked) "on" else null
        val lastFrontPart9 = if (mLastFrontPartList[9].isChecked) "on" else null
        val lastFrontPartOther = mOtherLastFrontPart
        val lastFrontPart = TreatmentHistoryBodyBean.LastFrontPart(
            lastFrontPartprimaryFocus,
            lastFrontPart9,
            lastFrontPartOther,
            lastFrontPart6,
            lastFrontPart1,
            lastFrontPart7,
            lastFrontPart3,
            lastFrontPart8,
            lastFrontPart5,
            lastFrontPart4,
            lastFrontPart2
        )
        val isBiopsyAgainStr = parseDefaultContent(tv_is_biopsy_again.text.toString())
        val isBiopsyAgain =
            if (isBiopsyAgainStr.isEmpty()) null else if (isBiopsyAgainStr == "是") 1 else 0
        var biopsyMethod: String?
        var biopsyMethodOther: String?
        if (isNull && !biopsyMethodIsSet) {
            biopsyMethod = null
            biopsyMethodOther = null
        } else if (biopsyMethodIsSet) {
            biopsyMethod = parseDefaultContent(tv_biopsy_way.text.toString())
            biopsyMethodOther = parseDefaultContent(tv_biopsy_way.text.toString())
            if (getBiopsyMethod().contains(biopsyMethod)) {
                biopsyMethodOther = null
            } else {
                biopsyMethod = "其他"
            }
        } else {
            biopsyMethod = mTreatmentHistoryBean.biopsyMethod
            biopsyMethodOther = mTreatmentHistoryBean.biopsyMethodOther
        }
        var biopsyType: String?
        var biopsyTypeOther: String?
        if (isNull && !biopsyTypeIsSet) {
            biopsyType = null
            biopsyTypeOther = null
        } else if (biopsyTypeIsSet) {
            biopsyType = parseDefaultContent(tv_biopsy_pathological_type.text.toString())
            biopsyTypeOther = parseDefaultContent(tv_biopsy_pathological_type.text.toString())
            if (getBiopsyType().contains(biopsyType)) {
                biopsyType = getBiopsyType().indexOf(biopsyType).toString()
                biopsyTypeOther = null
            } else {
                biopsyType = "与第1次活检病理类型不一致"
            }
        } else {
            biopsyType = mTreatmentHistoryBean.biopsyType
            biopsyTypeOther = mTreatmentHistoryBean.biopsyTypeOther
        }
        var geneticSpecimen: String?
        var geneticSpecimenOther: String?
        if (isNull && !geneticSpecimenIsSet) {
            geneticSpecimen = null
            geneticSpecimenOther = null
        } else if (geneticSpecimenIsSet) {
            geneticSpecimen = parseDefaultContent(tv_genetic_test_sample.text.toString())
            geneticSpecimenOther = parseDefaultContent(tv_genetic_test_sample.text.toString())
            if (getGeneticTestingSpecimen().contains(geneticSpecimen)) {
                geneticSpecimenOther = null
            } else {
                geneticSpecimen = "转移灶组织"
            }
        } else {
            geneticSpecimen = mTreatmentHistoryBean.geneticSpecimen
            geneticSpecimenOther = mTreatmentHistoryBean.geneticSpecimenOther
        }
        val geneticMethodStr = parseDefaultContent(tv_genetic_test_way.text.toString())
        val geneticMethod =
            if (geneticMethodStr.isEmpty()) null else getGeneticTestingMethod2().indexOf(
                geneticMethodStr
            )
        val geneticMutationType0 = if (mGeneMutationTypeList[0].isChecked) "on" else null
        val geneticMutationType1 = if (mGeneMutationTypeList[1].isChecked) "on" else null
        val geneticMutationType2 = if (mGeneMutationTypeList[2].isChecked) "on" else null
        val geneticMutationTypeROS1 = if (mGeneMutationTypeList[3].isChecked) "on" else null
        val geneticMutationTypecMET = if (mGeneMutationTypeList[4].isChecked) "on" else null
        val geneticMutationTypeBRAF = if (mGeneMutationTypeList[5].isChecked) "on" else null
        val geneticMutationTypeKRAS = if (mGeneMutationTypeList[6].isChecked) "on" else null
        val geneticMutationTypeHer2 = if (mGeneMutationTypeList[7].isChecked) "on" else null
        val geneticMutationTypeRET = if (mGeneMutationTypeList[8].isChecked) "on" else null
        val geneticMutationTypeERBB2 = if (mGeneMutationTypeList[9].isChecked) "on" else null
        val geneticMutationTypeTP53 = if (mGeneMutationTypeList[10].isChecked) "on" else null
        val geneticMutationTypeEGFR = if (mGeneMutationTypeList[11].isChecked) "on" else null
        val geneticMutationTypeALK = if (mGeneMutationTypeList[12].isChecked) "on" else null
        val geneticMutationTypeEGFROther = mOtherGeneMutationTypeEGFR
        val geneticMutationTypeALKOther = mOtherGeneMutationTypeALK
        val geneticMutationType = TreatmentHistoryBodyBean.GeneticMutationType(
            geneticMutationTypeALK,
            geneticMutationTypeALKOther,
            geneticMutationTypeBRAF,
            geneticMutationTypeEGFR,
            geneticMutationTypeEGFROther,
            geneticMutationTypeERBB2,
            geneticMutationTypeHer2,
            geneticMutationTypeKRAS,
            geneticMutationTypeRET,
            geneticMutationTypeROS1,
            geneticMutationTypeTP53,
            geneticMutationTypecMET,
            geneticMutationType1,
            geneticMutationType2,
            geneticMutationType0
        )
        val pdl1Str = parseDefaultContent(tv_PD_L1_expression.text.toString())
        val pdl1 = if (pdl1Str.isEmpty()) null else getPD_L1Expression().indexOf(pdl1Str)
        var tmb: String?
        var tmbOther: String?
        if (isNull && !tmbIsSet) {
            tmb = null
            tmbOther = null
        } else if (tmbIsSet) {
            tmb = parseDefaultContent(tv_tumor_mutation_load.text.toString())
            tmbOther = parseDefaultContent(tv_tumor_mutation_load.text.toString())
            if (getTMB().contains(tmb)) {
                tmbOther = null
            } else {
                tmb = "其他"
            }
        } else {
            tmb = mTreatmentHistoryBean.tmb
            tmbOther = mTreatmentHistoryBean.tmbOther
        }
        val msiStr = parseDefaultContent(tv_microsatellite_instability.text.toString())
        val msi = if (msiStr.isEmpty()) null else getMSI().indexOf(msiStr)
        val startTime = parseDefaultContent(tv_treatment_date.text.toString())
        val diagnoseMethodoperation = if (cb_operation.isChecked) "on" else null
        val diagnoseMethodoperationOther = parseDefaultContent(tv_operation.text.toString())
        val diagnoseMethodradiotherapy = if (cb_radiotherapy.isChecked) "on" else null
        val diagnoseMethodradiotherapyOther = parseDefaultContent(tv_radiotherapy.text.toString())
        val diagnoseMethodchemotherapy = if (cb_chemotherapy.isChecked) "on" else null
        val diagnoseMethodchemotherapyOther = parseDefaultContent(tv_chemotherapy.text.toString())
        val diagnoseMethodtargetedtherapy = if (cb_targeted_therapy.isChecked) "on" else null
        val diagnoseMethodtargetedtherapyOther =
            parseDefaultContent(tv_targeted_therapy.text.toString())
        val diagnoseMethodimmunotherapy = if (cb_immunotherapy.isChecked) "on" else null
        val diagnoseMethodimmunotherapyOther = parseDefaultContent(tv_immunotherapy.text.toString())
        val diagnoseMethodothertherapy = if (cb_other_therapy.isChecked) "on" else null
        val diagnoseMethodothertherapyOther = parseDefaultContent(tv_other_therapy.text.toString())
        val diagnoseMethod = TreatmentHistoryBodyBean.DiagnoseMethod(
            diagnoseMethodchemotherapy,
            diagnoseMethodchemotherapyOther,
            diagnoseMethodimmunotherapy,
            diagnoseMethodimmunotherapyOther,
            diagnoseMethodoperation,
            diagnoseMethodoperationOther,
            diagnoseMethodothertherapy,
            diagnoseMethodothertherapyOther,
            diagnoseMethodradiotherapy,
            diagnoseMethodradiotherapyOther,
            diagnoseMethodtargetedtherapy,
            diagnoseMethodtargetedtherapyOther
        )
        val isChange = if (th_switch_is_change.isChecked) 1 else 0
        val isGene = if (th_switch_is_genetic.isChecked) 1 else 0
        val treatmentHistoryBodyBean =
            TreatmentHistoryBodyBean(
                isChange,
                isGene,
                biopsyMethod,
                biopsyMethodOther,
                biopsyType,
                biopsyTypeOther,
                diagnoseExistence,
                diagnoseMethod,
                diagnoseNumber,
                geneticMethod,
                geneticMutationType,
                geneticSpecimen,
                geneticSpecimenOther,
                mId,
                isBiopsyAgain,
                lastFrontBestEfficacy,
                lastFrontPart,
                lastFrontTime,
                msi,
                pdl1,
                startTime,
                tmb,
                tmbOther
            )
        mViewModel.editTreatmentHistory(sampleId, treatmentHistoryBodyBean)
            .observe(this, androidx.lifecycle.Observer {
                if (it.code == 200) {
                    toast("治疗史操作成功")
                    val intent = Intent(this, CRFActivity::class.java)
                    intent.action = REFRESH_TREATMENT_HISTORY_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else {
                    toast(it.msg)
                }
            })
    }

    private fun loadData(bean: TreatmentHistoryListBean.Data) {
        if (bean.diagnoseNumber != null) {
            LogUtils.tag(TAG).d(bean)
            tv_treatment_line.text = getDiagnoseNumber()[bean.diagnoseNumber - 1]
            when (bean.diagnoseNumber) {
                1 -> {
                    ll_not_first_line_treatment.visibility = View.GONE
                    tv_first_line_treatment_title.text = "一线治疗"
                    tv_treatment_date_title.text = "一线治疗开始时间"
                }
                2 -> {
                    ll_not_first_line_treatment.visibility = View.VISIBLE
                    tv_first_line_treatment_title.text = "二线治疗"
                    tv_last_best_treatment_title.text = "一线治疗最佳疗效"
                    tv_last_treatment_growth_date_title.text = "一线治疗进展时间"
                    tv_last_treatment_growth_part_title.text = "一线治疗进展部位"
                    tv_treatment_date_title.text = "二线治疗开始时间"
                }
                3 -> {
                    ll_not_first_line_treatment.visibility = View.VISIBLE
                    tv_first_line_treatment_title.text = "三线治疗"
                    tv_last_best_treatment_title.text = "二线治疗最佳疗效"
                    tv_last_treatment_growth_date_title.text = "二线治疗进展时间"
                    tv_last_treatment_growth_part_title.text = "二线治疗进展部位"
                    tv_treatment_date_title.text = "三线治疗开始时间"
                }
                4 -> {
                    ll_not_first_line_treatment.visibility = View.VISIBLE
                    tv_first_line_treatment_title.text = "四线治疗"
                    tv_last_best_treatment_title.text = "三线治疗最佳疗效"
                    tv_last_treatment_growth_date_title.text = "三线治疗进展时间"
                    tv_last_treatment_growth_part_title.text = "三线治疗进展部位"
                    tv_treatment_date_title.text = "四线治疗开始时间"
                }
                5 -> {
                    ll_not_first_line_treatment.visibility = View.VISIBLE
                    tv_first_line_treatment_title.text = "五线治疗"
                    tv_last_best_treatment_title.text = "四线治疗最佳疗效"
                    tv_last_treatment_growth_date_title.text = "四线治疗进展时间"
                    tv_last_treatment_growth_part.text = "四线治疗进展部位"
                    tv_treatment_date_title.text = "五线治疗开始时间"
                }
            }
        }
        th_switch_is_change.isChecked = bean.isChange == 1
        th_is_change_view.visibility =
            if (th_switch_is_change.isChecked) View.VISIBLE else View.GONE
        th_switch_is_genetic.isChecked = bean.isGene == 1
        th_genetic_view.visibility = if (th_switch_is_genetic.isChecked) View.VISIBLE else View.GONE
        if (bean.diagnoseExistence != null) {
            if (bean.diagnoseExistence == -1) ll_treatment_detail.visibility = View.GONE
            else {
                tv_first_line_treatment.text = getDiagnoseExistence()[bean.diagnoseExistence]
                if (bean.diagnoseExistence < 2) {
                    ll_treatment_history_detail.visibility = View.GONE
                } else {
                    ll_treatment_history_detail.visibility = View.VISIBLE
                }
            }
        }
        if (bean.lastFrontBestEfficacy != null) {
            tv_last_best_treatment.text = getLastFrontBestEfficacyList()[bean.lastFrontBestEfficacy]
        }
        tv_last_treatment_growth_date.text = bean.lastFrontTime
        initLastFrontPart(bean)
        if (bean.isBiopsyAgain != null) {
            tv_is_biopsy_again.text = if (bean.isBiopsyAgain == 0) "否" else "是"
        }
        tv_biopsy_way.text =
            if (bean.biopsyMethod == "其他") bean.biopsyMethodOther else bean.biopsyMethod
        if (bean.biopsyType == "与第1次活检病理类型不一致")
            tv_biopsy_pathological_type.text = bean.biopsyTypeOther
        else if (bean.biopsyType != null) tv_biopsy_pathological_type.text =
            getBiopsyType()[bean.biopsyType.toInt()]
        tv_genetic_test_sample.text =
            if (bean.geneticSpecimen == "转移灶组织") bean.geneticSpecimenOther else bean.geneticSpecimen
        if (bean.geneticMethod != null) {
            tv_genetic_test_way.text = getGeneticTestingMethod2()[bean.geneticMethod]
        }
        initGeneMutationType(bean)
        if (bean.pDL1 != null) {
            tv_PD_L1_expression.text = getPD_L1Expression()[bean.pDL1]
        }
        tv_tumor_mutation_load.text = if (bean.tmb == "其他") bean.tmbOther else bean.tmb
        if (bean.msi != null) {
            tv_microsatellite_instability.text = getMSI()[bean.msi]
        }
        tv_treatment_date.text = bean.startTime
        cb_operation.isChecked = bean.diagnoseMethodoperation == "on"
        tv_operation.text = bean.diagnoseMethodoperationOther
        cb_radiotherapy.isChecked = bean.diagnoseMethodradiotherapy == "on"
        tv_radiotherapy.text = bean.diagnoseMethodradiotherapyOther
        cb_chemotherapy.isChecked = bean.diagnoseMethodchemotherapy == "on"
        tv_chemotherapy.text = bean.diagnoseMethodchemotherapyOther
        cb_targeted_therapy.isChecked = bean.diagnoseMethodtargetedtherapy == "on"
        tv_targeted_therapy.text = bean.diagnoseMethodtargetedtherapyOther
        cb_immunotherapy.isChecked = bean.diagnoseMethodimmunotherapy == "on"
        tv_immunotherapy.text = bean.diagnoseMethodimmunotherapyOther
        cb_other_therapy.isChecked = bean.diagnoseMethodothertherapy == "on"
        tv_other_therapy.text = bean.diagnoseMethodothertherapyOther
    }

    private fun initLastFrontPart(bean: TreatmentHistoryListBean.Data) {
        mLastFrontPartList.add(
            BaseCheckBean(
                getLastFrontPart()[0],
                bean.lastFrontPartprimaryFocus == "on"
            )
        )
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[1], bean.lastFrontPart1 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[2], bean.lastFrontPart2 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[3], bean.lastFrontPart3 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[4], bean.lastFrontPart4 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[5], bean.lastFrontPart5 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[6], bean.lastFrontPart6 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[7], bean.lastFrontPart7 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[8], bean.lastFrontPart8 == "on"))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[9], bean.lastFrontPart9 == "on"))
        val lastFrontPartText = StringBuffer()
        mLastFrontPartList.forEach {
            if (it.isChecked && it.name != "其他") {
                lastFrontPartText.append("${it.name},")
            }
        }
        mOtherLastFrontPart = bean.lastFrontPartOther ?: ""
        if (lastFrontPartText.isNotEmpty() && !mLastFrontPartList[9].isChecked) {
            lastFrontPartText.deleteCharAt(lastFrontPartText.length - 1)
        } else {
            lastFrontPartText.append(mOtherLastFrontPart)
        }
        tv_last_treatment_growth_part.text = lastFrontPartText
    }

    private fun initEmptyLastFrontPart() {
        mLastFrontPartList.add(
            BaseCheckBean(
                getLastFrontPart()[0],
                false
            )
        )
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[1], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[2], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[3], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[4], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[5], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[6], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[7], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[8], false))
        mLastFrontPartList.add(BaseCheckBean(getLastFrontPart()[9], false))
    }

    private fun initGeneMutationType(bean: TreatmentHistoryListBean.Data) {
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[0],
                bean.geneticMutationType0 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[1],
                bean.geneticMutationType1 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[2],
                bean.geneticMutationType2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[3],
                bean.geneticMutationTypeROS1 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[4],
                bean.geneticMutationTypecMET == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[5],
                bean.geneticMutationTypeBRAF == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[6],
                bean.geneticMutationTypeKRAS == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[7],
                bean.geneticMutationTypeHer2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[8],
                bean.geneticMutationTypeRET == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[9],
                bean.geneticMutationTypeERBB2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[10],
                bean.geneticMutationTypeTP53 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[11],
                bean.geneticMutationTypeEGFR == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[12],
                bean.geneticMutationTypeALK == "on"
            )
        )
        val geneMutationTypeText = StringBuffer()
        mGeneMutationTypeList.forEach {
            if (it.isChecked) {
                geneMutationTypeText.append("${it.name},")
            }
        }
        mOtherGeneMutationTypeEGFR = bean.geneticMutationTypeEGFROther
        mOtherGeneMutationTypeALK = bean.geneticMutationTypeALKOther
        if (!mOtherGeneMutationTypeEGFR.isNullOrBlank()) {
            geneMutationTypeText.append("EGFR描述:$mOtherGeneMutationTypeEGFR,")
        }
        if (!mOtherGeneMutationTypeALK.isNullOrBlank()) {
            geneMutationTypeText.append("ALK描述:$mOtherGeneMutationTypeALK")
        }
        tv_genetic_mutation_type.text = geneMutationTypeText
    }

    private fun initEmptyGeneMutationType() {
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[0],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[1],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[2],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[3],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[4],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[5],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[6],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[7],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[8],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[9],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[10],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[11],
                false
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[12],
                false
            )
        )
    }

    private fun checkDate(date: String): Boolean {
        val str = date.split('-')
        when (str.size) {
            0 -> {
                return false
            }
            1 -> return str[0].length == 4 && str[0].isDigitsOnly()
            2 -> {
                return str[0].length == 4 && str[1].length == 2 && str[0].isDigitsOnly()
                        && str[1].isDigitsOnly() && str[1].toInt() <= 12
            }
            3 -> {
                return str[0].length == 4 && str[1].length == 2 && str[2].length == 2
                        && str[0].isDigitsOnly() && str[1].isDigitsOnly() && str[2].isDigitsOnly()
                        && str[1].toInt() <= 12 && str[2].toInt() <= 31
            }
        }
        return false
    }
}
