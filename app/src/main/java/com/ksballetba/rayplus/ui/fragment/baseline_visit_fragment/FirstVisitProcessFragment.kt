package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.FirstVisitProcessResponseBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_first_visit_process.*

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

    private var mOtherGeneMutationTypeEGFR = ""
    private var mOtherGeneMutationTypeALK = ""
    private var mOtherTransferSite = ""

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

    private fun loadData(){
        mViewModel.getFirstVisitProcess(mSampleId).observe(viewLifecycleOwner, Observer {
            initClinicalSymptoms(it)
            tv_lesion.text = getTumorPart()[it.tumorPart]
            initTransferSite(it)
            tv_biopsy_way.text = if(it.biopsyMethod == "其他") it.biopsyMethodOther else it.biopsyMethod
            tv_tumor_type.text = if(it.tumorPathologicalType == "混合型癌") it.tumorPathologicalTypeOther else it.tumorPathologicalType
            tv_genetic_test_sample.text = if(it.geneticTestingSpecimen == "转移灶组织") it.geneticTestingSpecimenOther else it.geneticTestingSpecimen
            tv_genetic_test_way.text = getGeneticTestingMethod()[it.geneticTestingMethod]
            initGeneMutationType(it)
            tv_PD_L1_expression.text = getPD_L1Expression()[it.pdl1]
            tv_tumor_mutation_load.text = if(it.tmb == "其他") it.tmbOther else it.tmb
            tv_microsatellite_instability.text = getMSI()[it.msi]
        })
    }

    private fun initClinicalSymptoms(bean:FirstVisitProcessResponseBean){
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[0],bean.clinicalSymptoms0 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[1],bean.clinicalSymptoms1 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[2],bean.clinicalSymptoms2 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[3],bean.clinicalSymptoms3 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[4],bean.clinicalSymptoms4 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[5],bean.clinicalSymptoms5 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[6],bean.clinicalSymptoms6 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[7],bean.clinicalSymptoms7 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[8],bean.clinicalSymptoms8 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[9],bean.clinicalSymptoms9 == "on"))
        mClinicalSymptomsList.add(BaseCheckBean(getClinicalSymptomsList()[10],bean.clinicalSymptoms10 == "on"))
        val symptomsText = StringBuffer()
        mClinicalSymptomsList.forEach {
            if(it.isChecked){
                symptomsText.append("${it.name},")
            }
        }
        if(symptomsText.isNotEmpty()){
            symptomsText.deleteCharAt(symptomsText.length-1)
        }
        tv_diseases_history.text = symptomsText
    }

    private fun initGeneMutationType(bean:FirstVisitProcessResponseBean){
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[0],bean.geneMutationType0 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[1],bean.geneMutationType1 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[2],bean.geneMutationType2 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[3],bean.geneMutationTypeROS1 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[4],bean.geneMutationTypecMET == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[5],bean.geneMutationTypeBRAF == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[6],bean.geneMutationTypeKRAS == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[7],bean.geneMutationTypeHer2 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[8],bean.geneMutationTypeRET == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[9],bean.geneMutationTypeERBB2 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[10],bean.geneMutationTypeTP53 == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[11],bean.geneMutationTypeEGFR == "on"))
        mGeneMutationTypeList.add(BaseCheckBean(getGeneMutationType()[12],bean.geneMutationTypeALK == "on"))
        val geneMutationTypeText = StringBuffer()
        mGeneMutationTypeList.forEach {
            if(it.isChecked){
                geneMutationTypeText.append("${it.name},")
            }
        }
        mOtherGeneMutationTypeEGFR = bean.geneMutationTypeEGFROther
        mOtherGeneMutationTypeALK = bean.geneMutationTypeALKOther
        geneMutationTypeText.append("EGFR描述:$mOtherGeneMutationTypeEGFR,")
        geneMutationTypeText.append("ALK描述:$mOtherGeneMutationTypeALK")
        tv_genetic_mutation_type.text = geneMutationTypeText
    }

    private fun initTransferSite(bean:FirstVisitProcessResponseBean){
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[0],bean.transferSite0 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[1],bean.transferSite1 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[2],bean.transferSite2 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[3],bean.transferSite3 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[4],bean.transferSite4 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[5],bean.transferSite5 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[6],bean.transferSite6 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[7],bean.transferSite7 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[8],bean.transferSite8 == "on"))
        mTransferSiteList.add(BaseCheckBean(getTransferSite()[9],bean.transferSite9 == "on"))
        val transferSiteText = StringBuffer()
        mTransferSiteList.forEach {
            if(it.isChecked&&it.name!="其他"){
                transferSiteText.append("${it.name},")
            }
        }
        mOtherTransferSite = bean.transferSiteOther
        if(transferSiteText.isNotEmpty()&&!mTransferSiteList[9].isChecked){
            transferSiteText.append(mOtherTransferSite)
        }else{
            transferSiteText.append(mOtherTransferSite)
        }
        tv_transfer_area.text = transferSiteText
    }

    private fun initUI() {
        cl_diseases_history.setOnClickListener {
            val title = "基础疾病史"
            val diseases = StringBuffer()
            asCheckboxList(context, title, mClinicalSymptomsList, { data, pos ->
            }, { checkedData ->
                checkedData.forEach {
                    if(it.isChecked){
                        diseases.append("${it.name},")
                    }
                }
                if (diseases.isNotEmpty()) {
                    diseases.deleteCharAt(diseases.length - 1)
                }
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
            asCheckboxList(context,"转移部位",mTransferSiteList,{ data, pos ->
                if (data.name == "其他") {
                    XPopup.Builder(context).asInputConfirm("转移部位", "请输入其他转移部位") {
                        mOtherTransferSite = it
                    }.show()
                }
            },{checkedData ->
                checkedData.forEach {
                    if(it.isChecked&&it.name!="其他"){
                        transferSite.append("${it.name},")
                    }
                }
                if(transferSite.isNotEmpty()&&mOtherTransferSite.isBlank()){
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
                .asCenterList("基因检测方法", getGeneticTestingMethod()) { pos, text ->
                    tv_genetic_test_sample.text = text
                }.show()
        }
        cl_genetic_mutation_type.setOnClickListener {
            val geneMutationType = StringBuffer()
            asCheckboxList(context,"基因突变类型",mTransferSiteList,{ data, pos ->
                if (data.name == "EGFR"||data.name =="ALK") {
                    XPopup.Builder(context).asInputConfirm("基因突变类型", "请输入${data.name}描述") {
                        when(data.name){
                            "EGFR"->{
                                mOtherGeneMutationTypeEGFR = it
                            }
                            "ALK"->{
                                mOtherGeneMutationTypeALK = it
                            }
                        }
                    }.show()
                }
            },{checkedData ->
                checkedData.forEach {
                    if(it.isChecked){
                        geneMutationType.append("${it.name},")
                    }
                }
                geneMutationType.append("EGFR描述:$mOtherGeneMutationTypeEGFR,")
                geneMutationType.append("ALK描述:$mOtherGeneMutationTypeALK")
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
                    if(pos<2){
                        tv_tumor_mutation_load.text = text
                    }else{
                        XPopup.Builder(context).asInputConfirm("肿瘤突变负荷(TMB)", "请输入tmb数量") {
                            tv_tumor_mutation_load.text = it
                        }.show()
                    }
                }.show()
        }
        cl_microsatellite_instability.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("微卫星不稳定性(MSI)", getMSI()) { pos, text ->
                    tv_microsatellite_instability.text = text
                }.show()
        }
    }
}
