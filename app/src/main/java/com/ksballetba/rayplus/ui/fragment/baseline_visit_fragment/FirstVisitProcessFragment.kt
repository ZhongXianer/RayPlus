package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.baseLineData.FirstVisitProcessBodyBean
import com.ksballetba.rayplus.data.bean.baseLineData.FirstVisitProcessResponseBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_treatment_history.*
import kotlinx.android.synthetic.main.fragment_first_visit_process.*
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_PD_L1_expression
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_biopsy_way
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_genetic_mutation_type
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_genetic_test_sample
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_genetic_test_way
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_microsatellite_instability
import kotlinx.android.synthetic.main.fragment_first_visit_process.cl_tumor_mutation_load
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_PD_L1_expression
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_biopsy_way
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_genetic_mutation_type
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_genetic_test_sample
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_genetic_test_way
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_microsatellite_instability
import kotlinx.android.synthetic.main.fragment_first_visit_process.tv_tumor_mutation_load

/**
 * A simple [Fragment] subclass.
 */
class FirstVisitProcessFragment : Fragment() {

    companion object {
        const val TAG = "FirstVisitProcessFragment"
    }

    private lateinit var mViewModel: BaselineVisitViewModel

    private var mSampleId = 0

    private var mClinicalSymptomsList = mutableListOf<BaseCheckBean>()
    private var mGeneMutationTypeList = mutableListOf<BaseCheckBean>()
    private var mTransferSiteList = mutableListOf<BaseCheckBean>()

    private var mOtherGeneMutationTypeEGFR: String? = ""
    private var mOtherGeneMutationTypeALK: String? = ""
    private var mOtherTransferSite: String? = ""
    private var mOtherClinicalSymptoms: String? = ""

    private var biopsyMethodIsSet = false
    private var tumorPathologicalTypeIsSet = false
    private var geneticTestingSpecimenIsSet = false
    private var tmbIsSet = false
    private lateinit var mFirstVisitProcessResponseBean: FirstVisitProcessResponseBean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_visit_process, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun loadData() {
        mViewModel.getFirstVisitProcess(mSampleId).observe(viewLifecycleOwner, Observer {
            mFirstVisitProcessResponseBean = it
            initClinicalSymptoms(it)
            tv_lesion.text =
                if (it.data.tumorPart == null) "请设置" else getTumorPart()[it.data.tumorPart]
            initTransferSite(it)
            if (it.data.biopsyMethod != null)
                tv_biopsy_way.text =
                    if (it.data.biopsyMethod == "其他") it.data.biopsyMethodOther else it.data.biopsyMethod
            if (it.data.tumorPathologicalType != null)
                tv_tumor_type.text =
                    if (it.data.tumorPathologicalType == "混合型癌") it.data.tumorPathologicalTypeOther else it.data.tumorPathologicalType
            if (it.data.geneticTestingSpecimen != null)
                tv_genetic_test_sample.text =
                    if (it.data.geneticTestingSpecimen == "转移灶组织") it.data.geneticTestingSpecimenOther else it.data.geneticTestingSpecimen
            tv_genetic_test_way.text =
                if (it.data.geneticTestingMethod == null) "请设置" else getGeneticTestingMethod()[it.data.geneticTestingMethod]
            initGeneMutationType(it)
            if (it.data.pdl1 != null)
                tv_PD_L1_expression.text =
                    if (it.data.pdl1 == null) "" else getPD_L1Expression()[it.data.pdl1]
            if (it.data.tmb != null)
                tv_tumor_mutation_load.text =
                    if (it.data.tmb == "其他") it.data.tmbOther else it.data.tmb
            if (it.data.msi != null)
                tv_microsatellite_instability.text =
                    if (it.data.msi == null) "请设置" else getMSI()[it.data.msi]
        })
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                initEmptyClinicalSymptoms()
                initEmptyTransferSite()
                initEmptyGeneMutationType()
            }
        })
    }

    private fun saveData() {
        var clinicalSymptoms = FirstVisitProcessBodyBean.ClinicalSymptoms(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (mClinicalSymptomsList.size != 0) {
            val clinicalSymptoms0 = if (mClinicalSymptomsList[0].isChecked) "on" else null
            val clinicalSymptoms1 = if (mClinicalSymptomsList[1].isChecked) "on" else null
            val clinicalSymptoms2 = if (mClinicalSymptomsList[2].isChecked) "on" else null
            val clinicalSymptoms3 = if (mClinicalSymptomsList[3].isChecked) "on" else null
            val clinicalSymptoms4 = if (mClinicalSymptomsList[4].isChecked) "on" else null
            val clinicalSymptoms5 = if (mClinicalSymptomsList[5].isChecked) "on" else null
            val clinicalSymptoms6 = if (mClinicalSymptomsList[6].isChecked) "on" else null
            val clinicalSymptoms7 = if (mClinicalSymptomsList[7].isChecked) "on" else null
            val clinicalSymptoms8 = if (mClinicalSymptomsList[8].isChecked) "on" else null
            val clinicalSymptoms9 = if (mClinicalSymptomsList[9].isChecked) "on" else null
            val clinicalSymptoms10 = if (mClinicalSymptomsList[10].isChecked) "on" else null
            val clinicalSymptomsOther = mOtherClinicalSymptoms
            clinicalSymptoms = FirstVisitProcessBodyBean.ClinicalSymptoms(
                clinicalSymptoms9,
                clinicalSymptoms8,
                clinicalSymptomsOther,
                clinicalSymptoms10,
                clinicalSymptoms3,
                clinicalSymptoms0,
                clinicalSymptoms1,
                clinicalSymptoms2,
                clinicalSymptoms6,
                clinicalSymptoms7,
                clinicalSymptoms5,
                clinicalSymptoms4
            )
        }
        val tumorPartStr = parseDefaultContent(tv_lesion.text.toString())
        val tumorPart = if (tumorPartStr.isEmpty()) null else getTumorPart().indexOf(tumorPartStr)
        var transferSite = FirstVisitProcessBodyBean.TransferSite(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (mTransferSiteList.size != 0) {
            val transferSite0 = if (mTransferSiteList[0].isChecked) "on" else null
            val transferSite1 = if (mTransferSiteList[1].isChecked) "on" else null
            val transferSite2 = if (mTransferSiteList[2].isChecked) "on" else null
            val transferSite3 = if (mTransferSiteList[3].isChecked) "on" else null
            val transferSite4 = if (mTransferSiteList[4].isChecked) "on" else null
            val transferSite5 = if (mTransferSiteList[5].isChecked) "on" else null
            val transferSite6 = if (mTransferSiteList[6].isChecked) "on" else null
            val transferSite7 = if (mTransferSiteList[7].isChecked) "on" else null
            val transferSite8 = if (mTransferSiteList[8].isChecked) "on" else null
            val transferSite9 = if (mTransferSiteList[9].isChecked) "on" else null
            val transferSiteOther = mOtherTransferSite
            transferSite = FirstVisitProcessBodyBean.TransferSite(
                transferSite9,
                transferSiteOther,
                transferSite6,
                transferSite1,
                transferSite0,
                transferSite7,
                transferSite3,
                transferSite8,
                transferSite5,
                transferSite4,
                transferSite2
            )
        }
        var biopsyMethod: String?
        var biopsyMethodOther: String?
        if (biopsyMethodIsSet) {
            biopsyMethod = parseDefaultContent(tv_biopsy_way.text.toString())
            biopsyMethodOther = parseDefaultContent(tv_biopsy_way.text.toString())
            if (getBiopsyMethod().contains(biopsyMethod)) {
                biopsyMethodOther = null
            } else {
                biopsyMethod = "其他"
            }
        } else {
            biopsyMethod = mFirstVisitProcessResponseBean.data.biopsyMethod
            biopsyMethodOther = mFirstVisitProcessResponseBean.data.biopsyMethodOther
        }
        var tumorPathologicalType: String?
        var tumorPathologicalTypeOther: String?
        if (tumorPathologicalTypeIsSet) {
            tumorPathologicalType = parseDefaultContent(tv_tumor_type.text.toString())
            tumorPathologicalTypeOther = parseDefaultContent(tv_tumor_type.text.toString())
            if (getTumorPathologicalType().contains(tumorPathologicalType)) {
                tumorPathologicalTypeOther = null
            } else {
                tumorPathologicalType = "混合型癌"
            }
        } else {
            tumorPathologicalType = mFirstVisitProcessResponseBean.data.tumorPathologicalType
            tumorPathologicalTypeOther =
                mFirstVisitProcessResponseBean.data.tumorPathologicalTypeOther
        }
        var geneticTestingSpecimen: String?
        var geneticTestingSpecimenOther: String?
        if (geneticTestingSpecimenIsSet) {
            geneticTestingSpecimen = parseDefaultContent(tv_genetic_test_sample.text.toString())
            geneticTestingSpecimenOther =
                parseDefaultContent(tv_genetic_test_sample.text.toString())
            if (getGeneticTestingSpecimen().contains(geneticTestingSpecimen)) {
                geneticTestingSpecimenOther = null
            } else {
                geneticTestingSpecimen = "转移灶组织"
            }
        } else {
            geneticTestingSpecimen = mFirstVisitProcessResponseBean.data.geneticTestingSpecimen
            geneticTestingSpecimenOther =
                mFirstVisitProcessResponseBean.data.geneticTestingSpecimenOther
        }
        val geneticTestingMethodStr = parseDefaultContent(tv_genetic_test_way.text.toString())
        val geneticTestingMethod =
            if (geneticTestingMethodStr.isEmpty()) null else getGeneticTestingMethod().indexOf(
                geneticTestingMethodStr
            )
        var geneMutationType = FirstVisitProcessBodyBean.GeneMutationType(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (mGeneMutationTypeList.size != 0) {
            val geneMutationType0 = if (mGeneMutationTypeList[0].isChecked) "on" else null
            val geneMutationType1 = if (mGeneMutationTypeList[1].isChecked) "on" else null
            val geneMutationType2 = if (mGeneMutationTypeList[2].isChecked) "on" else null
            val geneMutationTypeROS1 = if (mGeneMutationTypeList[3].isChecked) "on" else null
            val geneMutationTypecMET = if (mGeneMutationTypeList[4].isChecked) "on" else null
            val geneMutationTypeBRAF = if (mGeneMutationTypeList[5].isChecked) "on" else null
            val geneMutationTypeKRAS = if (mGeneMutationTypeList[6].isChecked) "on" else null
            val geneMutationTypeHer2 = if (mGeneMutationTypeList[7].isChecked) "on" else null
            val geneMutationTypeRET = if (mGeneMutationTypeList[8].isChecked) "on" else null
            val geneMutationTypeERBB2 = if (mGeneMutationTypeList[9].isChecked) "on" else null
            val geneMutationTypeTP53 = if (mGeneMutationTypeList[10].isChecked) "on" else null
            val geneMutationTypeEGFR = if (mGeneMutationTypeList[11].isChecked) "on" else null
            val geneMutationTypeALK = if (mGeneMutationTypeList[12].isChecked) "on" else null
            val geneMutationTypeEGFROther = mOtherGeneMutationTypeEGFR
            val geneMutationTypeALKOther = mOtherGeneMutationTypeALK
            geneMutationType = FirstVisitProcessBodyBean.GeneMutationType(
                geneMutationTypeALK,
                geneMutationTypeALKOther,
                geneMutationTypeBRAF,
                geneMutationTypeEGFR,
                geneMutationTypeEGFROther,
                geneMutationTypeERBB2,
                geneMutationTypeHer2,
                geneMutationTypeKRAS,
                geneMutationTypeRET,
                geneMutationTypeROS1,
                geneMutationTypeTP53,
                geneMutationTypecMET,
                geneMutationType1,
                geneMutationType2,
                geneMutationType0
            )
        }
        val pdl1Str = parseDefaultContent(tv_PD_L1_expression.text.toString())
        val pdl1 = if (pdl1Str.isEmpty()) null else getPD_L1Expression().indexOf(pdl1Str)
        var tmb: String?
        var tmbOther: String?
        if (tmbIsSet) {
            tmb = parseDefaultContent(tv_tumor_mutation_load.text.toString())
            tmbOther = parseDefaultContent(tv_tumor_mutation_load.text.toString())
            if (tmb == "未测" || tmb == "不详") {
                tmbOther = null
            } else {
                tmb = "其他"
            }
        } else {
            tmb = mFirstVisitProcessResponseBean.data.tmb
            tmbOther = mFirstVisitProcessResponseBean.data.tmbOther
        }

        val msiStr = parseDefaultContent(tv_microsatellite_instability.text.toString())
        val msi = if (msiStr.isEmpty()) null else getMSI().indexOf(msiStr)
        val firstVisitProcessBodyBean =
            FirstVisitProcessBodyBean(
                biopsyMethod,
                biopsyMethodOther,
                clinicalSymptoms,
                geneMutationType,
                geneticTestingMethod,
                geneticTestingSpecimen,
                geneticTestingSpecimenOther,
                msi,
                pdl1,
                tmb,
                tmbOther,
                transferSite,
                tumorPart,
                tumorPathologicalType,
                tumorPathologicalTypeOther
            )
        mViewModel.editFirstVisitProcess(mSampleId, firstVisitProcessBodyBean)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("初诊过程表单修改成功")
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
    }

    private fun initClinicalSymptoms(bean: FirstVisitProcessResponseBean) {
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[0],
                bean.data.clinicalSymptoms0 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[1],
                bean.data.clinicalSymptoms1 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[2],
                bean.data.clinicalSymptoms2 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[3],
                bean.data.clinicalSymptoms3 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[4],
                bean.data.clinicalSymptoms4 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[5],
                bean.data.clinicalSymptoms5 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[6],
                bean.data.clinicalSymptoms6 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[7],
                bean.data.clinicalSymptoms7 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[8],
                bean.data.clinicalSymptoms8 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[9],
                bean.data.clinicalSymptoms9 == "on"
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[10],
                bean.data.clinicalSymptoms10 == "on"
            )
        )
        val symptomsText = StringBuffer()
        mClinicalSymptomsList.forEach {
            if (it.isChecked && it.name != "其他") {
                symptomsText.append("${it.name},")
            }
        }
        if (bean.data.clinicalSymptomsOther != null) {
            mOtherClinicalSymptoms = bean.data.clinicalSymptomsOther
        }
        if (symptomsText.isNotEmpty() && !mClinicalSymptomsList[10].isChecked) {
            symptomsText.deleteCharAt(symptomsText.length - 1)
        } else {
            symptomsText.append(mOtherClinicalSymptoms)
        }
        tv_diseases_history.text = symptomsText
    }

    private fun initGeneMutationType(bean: FirstVisitProcessResponseBean) {
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[0],
                bean.data.geneMutationType0 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[1],
                bean.data.geneMutationType1 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[2],
                bean.data.geneMutationType2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[3],
                bean.data.geneMutationTypeROS1 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[4],
                bean.data.geneMutationTypecMET == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[5],
                bean.data.geneMutationTypeBRAF == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[6],
                bean.data.geneMutationTypeKRAS == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[7],
                bean.data.geneMutationTypeHer2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[8],
                bean.data.geneMutationTypeRET == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[9],
                bean.data.geneMutationTypeERBB2 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[10],
                bean.data.geneMutationTypeTP53 == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[11],
                bean.data.geneMutationTypeEGFR == "on"
            )
        )
        mGeneMutationTypeList.add(
            BaseCheckBean(
                getGeneMutationType()[12],
                bean.data.geneMutationTypeALK == "on"
            )
        )
        val geneMutationTypeText = StringBuffer()
        mGeneMutationTypeList.forEach {
            if (it.isChecked) {
                geneMutationTypeText.append("${it.name},")
            }
        }
        mOtherGeneMutationTypeEGFR = bean.data.geneMutationTypeEGFROther
        mOtherGeneMutationTypeALK = bean.data.geneMutationTypeALKOther
        if (!mOtherGeneMutationTypeEGFR.isNullOrBlank()) {
            geneMutationTypeText.append("EGFR描述:$mOtherGeneMutationTypeEGFR,")
        }
        if (!mOtherGeneMutationTypeALK.isNullOrBlank()) {
            geneMutationTypeText.append("ALK描述:$mOtherGeneMutationTypeALK")
        }
        tv_genetic_mutation_type.text = geneMutationTypeText
    }

    private fun initTransferSite(bean: FirstVisitProcessResponseBean) {
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[0], bean.data.transferSite0 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[1], bean.data.transferSite1 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[2], bean.data.transferSite2 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[3], bean.data.transferSite3 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[4], bean.data.transferSite4 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[5], bean.data.transferSite5 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[6], bean.data.transferSite6 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[7], bean.data.transferSite7 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[8], bean.data.transferSite8 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[9], bean.data.transferSite9 == "on"))
        val transferSiteText = StringBuffer()
        mTransferSiteList.forEach {
            if (it.isChecked && it.name != "其他") {
                transferSiteText.append("${it.name},")
            }
        }
        if (bean.data.transferSiteOther != null) {
            mOtherTransferSite = bean.data.transferSiteOther
        }
        if (transferSiteText.isNotEmpty() && !mTransferSiteList[9].isChecked) {
            transferSiteText.deleteCharAt(transferSiteText.length - 1)
        } else {
            transferSiteText.append(mOtherTransferSite)
        }
        tv_transfer_area.text = transferSiteText
    }

    private fun initEmptyClinicalSymptoms() {
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[0],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[1],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[2],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[3],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[4],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[5],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[6],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[7],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[8],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[9],
                false
            )
        )
        mClinicalSymptomsList.add(
            BaseCheckBean(
                getClinicalSymptomsList()[10],
                false
            )
        )
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

    private fun initEmptyTransferSite() {
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[0], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[1], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[2], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[3], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[4], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[5], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[6], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[7], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[8], false))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[9], false))
    }

    private fun initUI() {
        cl_diseases_history.setOnClickListener {
            val title = "初诊临床症状"
            val diseases = StringBuffer()
            asCheckboxList(context, title, mClinicalSymptomsList, { data, _ ->
                if (data.name == "其他") {
                    XPopup.Builder(context).asInputConfirm("初诊临床症状", "请输入其他初诊临床症状") {
                        mOtherClinicalSymptoms = it
                    }.show()
                }
            }, { checkedData ->
                checkedData.forEach {
                    if (it.isChecked && it.name != "其他") {
                        diseases.append("${it.name},")
                    }
                }
                if (diseases.isNotEmpty() && mOtherClinicalSymptoms.isNullOrEmpty()) {
                    diseases.deleteCharAt(diseases.length - 1)
                }
                diseases.append(mOtherClinicalSymptoms)
                tv_diseases_history.text = diseases.toString()
            }).show()
        }
        cl_lesion.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("病灶部位", getTumorPart()) { _, text ->
                    tv_lesion.text = text
                }.show()
        }
        cl_transfer_area.setOnClickListener {
            val transferSite = StringBuffer()
            asCheckboxList(context, "转移部位", mTransferSiteList, { data, _ ->
                if (data.name == "其他") {
                    XPopup.Builder(context).asInputConfirm("转移部位", "请输入其他转移部位") {
                        mOtherTransferSite = it
                    }.show()
                }
            }, { checkedData ->
                checkedData.forEach {
                    if (it.isChecked && it.name != "其他") {
                        transferSite.append("${it.name},")
                    }
                }
                if (transferSite.isNotEmpty() && mOtherTransferSite.isNullOrBlank()) {
                    transferSite.deleteCharAt(transferSite.length - 1)
                }
                transferSite.append(mOtherTransferSite)
                tv_transfer_area.text = transferSite
            }).show()
        }
        cl_biopsy_way.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "活检方式",
                arrayOf("无", "手术", "胸腔镜", "纵膈镜", "经皮肺穿刺", "纤支镜", "E-BUS", "EUS-FNA", "淋巴结活检", "其他")
            ) { pos, text ->
                biopsyMethodIsSet = true
                if (pos < 9) {
                    tv_biopsy_way.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("活检方式", "请输入活检方式") {
                        tv_biopsy_way.text = it
                    }.show()
                }
            }.show()
        }
        cl_tumor_type.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "肿瘤病理类型",
                arrayOf("腺癌", "鳞癌", "小细胞肺癌", "大细胞癌", "神经内分泌癌", "肉瘤", "分化差的癌", "混合型癌")
            ) { pos, text ->
                tumorPathologicalTypeIsSet = true
                if (pos < 7) {
                    tv_tumor_type.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("肿瘤病理类型", "请输入混合型癌描述") {
                        tv_tumor_type.text = it
                    }.show()
                }
            }.show()
        }
        cl_genetic_test_sample.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("基因检测标本", arrayOf("无", "外周血", "原发灶组织", "转移灶组织")) { pos, text ->
                    geneticTestingSpecimenIsSet = true
                    if (pos < 3) {
                        tv_genetic_test_sample.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("基因检测标本", "请输入转移灶组织描述") {
                            tv_genetic_test_sample.text = it
                        }.show()
                    }
                }.show()
        }
        cl_genetic_test_way.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("基因检测方法", getGeneticTestingMethod()) { _, text ->
                    tv_genetic_test_way.text = text
                }.show()
        }
        cl_genetic_mutation_type.setOnClickListener {
            val geneMutationType = StringBuffer()
            asCheckboxList(context, "基因突变类型", mGeneMutationTypeList, { data, _ ->
                if (data.name == "EGFR" || data.name == "ALK") {
                    XPopup.Builder(context).asInputConfirm("基因突变类型", "请输入${data.name}描述") {
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
            XPopup.Builder(context)
                .asCenterList("PD-L1表达", getPD_L1Expression()) { pos, text ->
                    tv_PD_L1_expression.text = text
                }.show()
        }
        cl_tumor_mutation_load.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("肿瘤突变负荷(TMB)", arrayOf("未测", "不详", "数量（个突变/Mb）")) { pos, text ->
                    tmbIsSet = true
                    if (pos < 2) {
                        tv_tumor_mutation_load.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("肿瘤突变负荷(TMB)", "请输入tmb数量") {
                            tv_tumor_mutation_load.text = it
                        }.show()
                    }
                }.show()
        }
        cl_microsatellite_instability.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("微卫星不稳定性(MSI)", getMSI()) { _, text ->
                    tv_microsatellite_instability.text = text
                }.show()
        }
        fab_save_first_visit_process.setOnClickListener {
            saveData()
        }
    }
}
