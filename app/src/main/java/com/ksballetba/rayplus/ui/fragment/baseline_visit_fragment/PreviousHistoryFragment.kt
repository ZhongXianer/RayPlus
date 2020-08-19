package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.baseLineData.PreviousHistoryBodyBean
import com.ksballetba.rayplus.data.bean.baseLineData.PreviousHistoryResponseBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_previous_history.*

/**
 * A simple [Fragment] subclass.
 */
class PreviousHistoryFragment : Fragment() {

    companion object {
        const val TAG = "PreviousHistoryFragment"
    }

    private lateinit var mViewModel: BaselineVisitViewModel

    private var mSampleId = 0

    private var mBaseIllList = mutableListOf<BaseCheckBean>()

    private var mOtherIll: String? = ""

    private var surgeryIsSet = false
    private var tumorIllIsSet = false
    private var drugAllergyIsSet = false
    private var drugUseIsSet = false

    private lateinit var mPreviousHistoryResponseBean: PreviousHistoryResponseBean


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        initUI()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun loadData() {
        mViewModel.getPreviousHistory(mSampleId).observe(viewLifecycleOwner, Observer {
            mPreviousHistoryResponseBean = it
            if (it.data.surgery != null)
                tv_operation_history.text =
                    if (it.data.surgery != "其他") it.data.surgery else it.data.surgeryOther
            initBaseIllList(it)
            if (it.data.tumorIll != null)
                tv_phymatosis_history.text =
                    if (it.data.tumorIll != "其他") it.data.tumorIll else it.data.tumorIllOther
            if (it.data.drugAllergy != null)
                tv_allergy_history.text =
                    if (it.data.drugAllergy != "其他") it.data.drugAllergy else it.data.drugAllergyOther
            if (it.data.drugUse != null)
                tv_medication_use_history.text =
                    if (it.data.drugUse != "其他") it.data.drugUse else it.data.drugUseOther
            switch_smoke_history.isChecked = it.data.smoke == "on"
            ll_smoke.visibility = if (switch_smoke_history.isChecked) View.VISIBLE else View.GONE
            tv_average_cigarette.text = it.data.smokeSize
            tv_smoke_years.text = it.data.smokeYear
            switch_is_quit_smoke.isChecked = it.data.smokeIsquit == "on"
            ll_quit_smoke.visibility =
                if (switch_is_quit_smoke.isChecked) View.VISIBLE else View.GONE
            tv_quit_smoke_date.text = it.data.smokeQuitTime
            switch_is_relapse_smoke.isChecked = it.data.smokeChemotherapy == "on"
            switch_drink_history.isChecked = it.data.drinking == "on"
            ll_drink.visibility = if (switch_drink_history.isChecked) View.VISIBLE else View.GONE
            if (!it.data.drinkingFrequence.isNullOrEmpty()) {
                tv_drink_frequency.text = getDrinkingFrequency()[it.data.drinkingFrequence.toInt()]
            }
            if (!it.data.drinkingSize.isNullOrEmpty()) {
                tv_drink_capacity.text = getDrinkingSize()[it.data.drinkingSize.toInt()]
            }
            switch_is_quit_drink.isChecked = it.data.drinkingIsQuit == "on"
            ll_quit_drink.visibility =
                if (switch_is_quit_drink.isChecked) View.VISIBLE else View.GONE
            tv_quit_drink_date.text = it.data.drinkingQuitTime
            switch_is_relapse_drink.isChecked = it.data.drinkingChemotherapy == "on"
            tv_patient_height.text =
                if (it.data.height == null) "请设置" else it.data.height.toString()
            tv_patient_weight.text =
                if (it.data.weight == null) "请设置" else it.data.weight.toString()
            tv_patient_body_area.text =
                if (it.data.surfaceArea == null) "请设置" else it.data.surfaceArea.toString()
            tv_ECOG_score.text =
                if (it.data.eCOG == null) "请设置" else it.data.eCOG.toString()
        })
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                initEmptyBaseIllList()
            }
        })
    }

    private fun saveData() {
        var surgery: String? = mPreviousHistoryResponseBean.data.surgery
        var surgeryOther: String? = mPreviousHistoryResponseBean.data.surgeryOther
        if (surgeryIsSet) {
            surgery = parseDefaultContent(tv_operation_history.text.toString())
            surgeryOther = parseDefaultContent(tv_operation_history.text.toString())
            if (surgery == "无" || surgery == "不详") {
                surgeryOther = null
            } else {
                surgery = "其他"
            }
        }
        var baseIll = PreviousHistoryBodyBean.BaseIll(
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
        if (mBaseIllList.size > 0) {
            val baseIll0 = if (mBaseIllList[0].isChecked) "on" else null
            val baseIll1 = if (mBaseIllList[1].isChecked) "on" else null
            val baseIll2 = if (mBaseIllList[2].isChecked) "on" else null
            val baseIll3 = if (mBaseIllList[3].isChecked) "on" else null
            val baseIll4 = if (mBaseIllList[4].isChecked) "on" else null
            val baseIll5 = if (mBaseIllList[5].isChecked) "on" else null
            val baseIll6 = if (mBaseIllList[6].isChecked) "on" else null
            val baseIll7 = if (mBaseIllList[7].isChecked) "on" else null
            val baseIll8 = if (mBaseIllList[8].isChecked) "on" else null
            val baseIll9 = if (mBaseIllList[9].isChecked) "on" else null
            val baseIll10 = if (mBaseIllList[10].isChecked) "on" else null
            val baseIll11 = if (mBaseIllList[11].isChecked) "on" else null
            val baseIll12 = if (mBaseIllList[12].isChecked) "on" else null
            val baseIll13 = if (mBaseIllList[13].isChecked) "on" else null
            val baseOther = mOtherIll
            baseIll = PreviousHistoryBodyBean.BaseIll(
                baseOther,
                baseIll1,
                baseIll13,
                baseIll3,
                baseIll5,
                baseIll6,
                baseIll0,
                baseIll10,
                baseIll4,
                baseIll7,
                baseIll12,
                baseIll8,
                baseIll11,
                baseIll9,
                baseIll2
            )
        }
        var tumorIll: String? = mPreviousHistoryResponseBean.data.tumorIll
        var tumorIllOther: String? = mPreviousHistoryResponseBean.data.tumorIllOther
        if (tumorIllIsSet) {
            tumorIll = parseDefaultContent(tv_phymatosis_history.text.toString())
            tumorIllOther = parseDefaultContent(tv_phymatosis_history.text.toString())
            if (tumorIll == "无" || tumorIll == "不详") {
                tumorIllOther = null
            } else {
                tumorIll = "其他"
            }
        }
        var drugAllergy: String? = mPreviousHistoryResponseBean.data.drugAllergy
        var drugAllergyOther: String? = mPreviousHistoryResponseBean.data.drugAllergyOther
        if (drugAllergyIsSet) {
            drugAllergy = parseDefaultContent(tv_allergy_history.text.toString())
            drugAllergyOther = parseDefaultContent(tv_allergy_history.text.toString())
            if (drugAllergy == "无" || drugAllergy == "不详") {
                drugAllergyOther = null
            } else {
                drugAllergy = "其他"
            }
        }
        var drugUse: String? = mPreviousHistoryResponseBean.data.drugUse
        var drugUseOther: String? = mPreviousHistoryResponseBean.data.drugUseOther
        if (drugUseIsSet) {
            drugUse = parseDefaultContent(tv_medication_use_history.text.toString())
            drugUseOther = parseDefaultContent(tv_medication_use_history.text.toString())
            if (drugUse == "无" || drugUse == "不详") {
                drugUseOther = null
            } else {
                drugUse = "其他"
            }
        }
        val smoke = if (switch_smoke_history.isChecked) "on" else null
        val smokeSize = parseDefaultContent(tv_average_cigarette.text.toString())
        val smokeYear = parseDefaultContent(tv_smoke_years.text.toString())
        val smokeIsQuit = if (switch_is_quit_smoke.isChecked) "on" else null
        val smokeQuitTime = parseDefaultContent(tv_quit_smoke_date.text.toString())
        val smokeChemotherapy = if (switch_is_relapse_smoke.isChecked) "on" else null
        val smokeBean: PreviousHistoryBodyBean.Smoke? = PreviousHistoryBodyBean.Smoke(
            smoke, smokeChemotherapy, smokeIsQuit, smokeQuitTime, smokeSize, smokeYear
        )
        val drinking = if (switch_drink_history.isChecked) "on" else null
        val drinkingFrequenceStr = parseDefaultContent(tv_drink_frequency.text.toString())
        val drinkingFrequence =
            if (drinkingFrequenceStr.isEmpty()) null else drinkingFrequenceStr[0].toString()
        val drinkingSizeStr = parseDefaultContent(tv_drink_capacity.text.toString())
        val drinkingSize = if (drinkingSizeStr.isEmpty()) null else drinkingSizeStr[0].toString()
        val drinkingIsQuit = if (switch_is_quit_drink.isChecked) "on" else null
        val drinkingQuitTime = parseDefaultContent(tv_quit_drink_date.text.toString())
        val drinkingChemotherapy = if (switch_is_relapse_drink.isChecked) "on" else null
        val drinkingBean: PreviousHistoryBodyBean.Drinking? = PreviousHistoryBodyBean.Drinking(
            drinking,
            drinkingChemotherapy,
            drinkingFrequence,
            drinkingIsQuit,
            drinkingQuitTime,
            drinkingSize
        )
        var height: String? = parseDefaultContent(tv_patient_height.text.toString())
        if (height == "") height = null
        var weight: String? = parseDefaultContent(tv_patient_weight.text.toString())
        if (weight == "") weight = null
        var surfaceArea: String? = parseDefaultContent(tv_patient_body_area.text.toString())
        if (surfaceArea == "") surfaceArea = null
        var eCOG: String? = parseDefaultContent(tv_ECOG_score.text.toString())
        if (eCOG == "") eCOG = null
        val previousHistoryBody =
            PreviousHistoryBodyBean(
                baseIll,
                drinkingBean,
                drugAllergy,
                drugAllergyOther,
                drugUse,
                drugUseOther,
                eCOG,
                height,
                smokeBean,
                surfaceArea,
                surgery,
                surgeryOther,
                tumorIll,
                tumorIllOther,
                weight
            )
        mViewModel.editPreviousHistory(mSampleId, previousHistoryBody).observe(viewLifecycleOwner,
            Observer {
                if (it.code == 200) {
                    ToastUtils.showShort("既往史表单修改成功")
                } else {
                    ToastUtils.showShort(it.msg)
                }
            })
    }

    private fun initBaseIllList(bean: PreviousHistoryResponseBean) {
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[0], bean.data.baseIll0 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[1], bean.data.baseIll1 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[2], bean.data.baseIll2 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[3], bean.data.baseIll3 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[4], bean.data.baseIll4 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[5], bean.data.baseIll5 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[6], bean.data.baseIll6 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[7], bean.data.baseIll7 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[8], bean.data.baseIll8 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[9], bean.data.baseIll9 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[10], bean.data.baseIll10 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[11], bean.data.baseIll11 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[12], bean.data.baseIll12 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[13], bean.data.baseIll13 == "on"))
        val illText = StringBuffer()
        for (ill in mBaseIllList) {
            if (ill.isChecked && ill.name != "其他，请描述") {
                illText.append("${ill.name},")
            }
        }
        if (bean.data.baseIllOther != null) {
            mOtherIll = bean.data.baseIllOther
        }
        if (!mBaseIllList[13].isChecked && illText.isNotEmpty()) {
            illText.deleteCharAt(illText.length - 1)
        } else {
            illText.append(mOtherIll)
        }
        tv_diseases_history.text = illText
    }

    private fun initEmptyBaseIllList() {
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[0], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[1], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[2], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[3], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[4], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[5], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[6], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[7], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[8], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[9], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[10], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[11], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[12], false))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[13], false))
    }

    private fun initUI() {
        cl_operation_history.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("外伤及手术史", arrayOf("无", "不详", "有，请详述")) { pos, text ->
                    surgeryIsSet = true
                    if (pos < 2) {
                        tv_operation_history.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("外伤及手术史", "请输入外伤及手术史") {
                            tv_operation_history.text = it
                        }.show()
                    }
                }.show()
        }
        cl_diseases_history.setOnClickListener {
            val title = "基础疾病史"
            val diseases = StringBuffer()
            asCheckboxList(context, title, mBaseIllList, { data, _ ->
                if (data.name == "其他，请描述") {
                    XPopup.Builder(context).asInputConfirm("基础疾病史", "请输入基础疾病史") {
                        mOtherIll = it
                    }.show()
                }
            }, { checkedData ->
                for (d in checkedData) {
                    if (d.isChecked && d.name != "其他，请描述") {
                        diseases.append("${d.name},")
                    }
                }
                if (diseases.isNotEmpty() && mOtherIll.isNullOrEmpty()) {
                    diseases.deleteCharAt(diseases.length - 1)
                }
                diseases.append(mOtherIll)
                tv_diseases_history.text = diseases.toString()
            }).show()
        }
        cl_phymatosis_history.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("肿瘤病史", arrayOf("无", "不详", "有，请详述")) { pos, text ->
                    tumorIllIsSet = true
                    if (pos < 2) {
                        tv_phymatosis_history.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("肿瘤病史", "请输入肿瘤病史") {
                            tv_phymatosis_history.text = it
                        }.show()
                    }
                }.show()
        }
        cl_allergy_history.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("药物过敏史", arrayOf("无", "不详", "有，请详述")) { pos, text ->
                    drugAllergyIsSet = true
                    if (pos < 2) {
                        tv_allergy_history.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("药物过敏史", "请输入药物过敏史") {
                            tv_allergy_history.text = it
                        }.show()
                    }
                }.show()
        }
        cl_medication_use_history.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("药物使用史", arrayOf("无", "不详", "有，请详述")) { pos, text ->
                    drugUseIsSet = true
                    if (pos < 2) {
                        tv_medication_use_history.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("药物使用史", "请输入药物使用史") {
                            tv_medication_use_history.text = it
                        }.show()
                    }
                }.show()
        }
        cl_smoke_history.setOnClickListener {
            switch_smoke_history.isChecked = !switch_smoke_history.isChecked
        }
        switch_smoke_history.setOnCheckedChangeListener { _, isChecked ->
            switch_smoke_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_smoke.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        cl_average_cigarette.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("平均吸烟（支/天）", "请输入平均吸烟") {
                tv_average_cigarette.text = it
            }.show()
        }
        cl_smoke_years.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("吸烟年数", "请输入吸烟年数") {
                tv_smoke_years.text = it
            }.show()
        }
        cl_is_quit_smoke.setOnClickListener {
            switch_is_quit_smoke.isChecked = !switch_is_quit_smoke.isChecked
        }
        switch_is_quit_smoke.setOnCheckedChangeListener { _, isChecked ->
            switch_is_quit_smoke.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_quit_smoke.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        cl_quit_smoke_date.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("戒烟时间", "请输入戒烟时间") {
                tv_quit_smoke_date.text = it
            }.show()
        }
        cl_is_relapse_smoke.setOnClickListener {
            switch_is_relapse_smoke.isChecked = !switch_is_relapse_smoke.isChecked
        }
        switch_is_relapse_smoke.setOnCheckedChangeListener { _, isChecked ->
            switch_is_relapse_smoke.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_drink_history.setOnClickListener {
            switch_drink_history.isChecked = !switch_drink_history.isChecked
        }
        switch_drink_history.setOnCheckedChangeListener { _, isChecked ->
            switch_drink_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_drink.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        cl_drink_frequency.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "饮酒频率",
                    arrayOf("请设置", "几乎不", "每周1-2次", "每周3-4次", "每周5-7次")
                ) { _, text ->
                    tv_drink_frequency.text = text
                }.show()
        }
        cl_drink_capacity.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "每次饮酒量",
                    arrayOf("请设置", "每次少量", "每周微醉", "偶尔大醉", "每次大醉")
                ) { _, text ->
                    tv_drink_capacity.text = text
                }.show()
        }
        cl_is_quit_drink.setOnClickListener {
            switch_is_quit_drink.isChecked = !switch_is_quit_drink.isChecked
        }
        switch_is_quit_drink.setOnCheckedChangeListener { _, isChecked ->
            switch_is_quit_drink.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_quit_drink.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        cl_quit_drink_date.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("戒酒时间", "请输入戒酒时间") {
                tv_quit_drink_date.text = it
            }.show()
        }
        cl_is_relapse_drink.setOnClickListener {
            switch_is_relapse_drink.isChecked = !switch_is_relapse_smoke.isChecked
        }
        switch_is_relapse_drink.setOnCheckedChangeListener { _, isChecked ->
            switch_is_relapse_drink.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_patient_height.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("身高（cm）", "请输入身高") {
                tv_patient_height.text = it
            }.show()
        }
        cl_patient_weight.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("体重（kg）", "请输入体重") {
                tv_patient_weight.text = it
            }.show()
        }
        cl_patient_body_area.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("体表面积（㎡）", "请输入体表面积") {
                tv_patient_body_area.text = it
            }.show()
        }
        cl_ECOG_score.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("ECOG评分", "请输入评分") {
                tv_ECOG_score.text = it
            }.show()
        }
        fab_save_previous_history.setOnClickListener {
            saveData()
        }
    }
}
