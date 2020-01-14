package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.PreviousHistoryBodyBean
import com.ksballetba.rayplus.data.bean.PreviousHistoryResponseBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_previous_history.*
import java.util.Calendar
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel

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

    private var mOtherIll:String? = ""

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
        initUI()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun loadData() {
        mViewModel.getPreviousHistory(mSampleId).observe(viewLifecycleOwner, Observer {
            LogUtils.tag(TAG).d(it)
            tv_operation_history.text = if (it.surgery != "其他") it.surgery else it.surgeryOther
            initBaseIllList(it)
            tv_phymatosis_history.text = if (it.tumorIll != "其他") it.tumorIll else it.tumorIllOther
            tv_allergy_history.text =
                if (it.drugAllergy != "其他") it.drugAllergy else it.drugAllergyOther
            tv_medication_use_history.text = if (it.drugUse != "其他") it.drugUse else it.drugUseOther
            switch_smoke_history.isChecked = it.smoke == "on"
            ll_smoke.visibility = if(switch_smoke_history.isChecked) View.VISIBLE else View.GONE
            tv_average_cigarette.text = it.smokeSize
            tv_smoke_years.text = it.smokeYear
            switch_is_quit_smoke.isChecked = it.smokeIsquit == "on"
            ll_quit_smoke.visibility = if(switch_is_quit_smoke.isChecked) View.VISIBLE else View.GONE
            tv_quit_smoke_date.text = it.smokeQuitTime
            switch_is_relapse_smoke.isChecked = it.smokeChemotherapy == "on"
            switch_drink_history.isChecked = it.drinking == "on"
            ll_drink.visibility = if(switch_drink_history.isChecked) View.VISIBLE else View.GONE
            if(!it.drinkingFrequence.isNullOrEmpty()){
                tv_drink_frequency.text = getDrinkingFrequency()[it.drinkingFrequence.toInt()]
            }
            if(!it.drinkingSize.isNullOrEmpty()) {
                tv_drink_capacity.text = getDrinkingSize()[it.drinkingSize.toInt()]
            }
            switch_is_quit_drink.isChecked = it.drinkingIsQuit == "on"
            ll_quit_drink.visibility = if(switch_is_quit_drink.isChecked) View.VISIBLE else View.GONE
            tv_quit_drink_date.text = it.drinkingQuitTime
            switch_is_relapse_drink.isChecked = it.drinkingChemotherapy == "on"
            tv_patient_height.text = it.height.toString()
            tv_patient_weight.text = it.weight.toString()
            tv_patient_body_area.text = it.surfaceArea.toString()
            tv_ECOG_score.text = it.eCOG.toString()
        })
    }

    private fun saveData() {
        var surgery: String? = parseDefaultContent(tv_operation_history.text.toString())
        var surgeryOther: String? = parseDefaultContent(tv_operation_history.text.toString())
        if (surgery == "无" || surgery == "不详") {
            surgeryOther = null
        } else {
            surgery = "其他"
        }
        var baseIll = PreviousHistoryBodyBean.BaseIll(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)
        if(mBaseIllList.size>0){
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
        var tumorIll: String? = parseDefaultContent(tv_phymatosis_history.text.toString())
        var tumorIllOther: String? = parseDefaultContent(tv_phymatosis_history.text.toString())
        if (tumorIll == "无" || tumorIll == "不详") {
            tumorIllOther = null
        } else {
            tumorIll = "其他"
        }
        var drugAllergy: String? = parseDefaultContent(tv_allergy_history.text.toString())
        var drugAllergyOther: String? = parseDefaultContent(tv_allergy_history.text.toString())
        if (drugAllergy == "无" || drugAllergy == "不详") {
            drugAllergyOther = null
        } else {
            drugAllergy = "其他"
        }
        var drugUse: String? = parseDefaultContent(tv_medication_use_history.text.toString())
        var drugUseOther: String? = parseDefaultContent(tv_medication_use_history.text.toString())
        if (drugUse == "无" || drugUse == "不详") {
            drugUseOther = null
        } else {
            drugUse = "其他"
        }
        val smoke = if (switch_smoke_history.isChecked) "on" else null
        val smokeSize = parseDefaultContent(tv_average_cigarette.text.toString())
        val smokeYear = parseDefaultContent(tv_smoke_years.text.toString())
        val smokeIsQuit = if (switch_is_quit_smoke.isChecked) "on" else null
        val smokeQuitTime = parseDefaultContent(tv_quit_smoke_date.text.toString())
        val smokeChemotherapy = if (switch_is_relapse_smoke.isChecked) "on" else null
        var smokeBean:PreviousHistoryBodyBean.Smoke? = PreviousHistoryBodyBean.Smoke(
            smoke, smokeChemotherapy, smokeIsQuit, smokeQuitTime, smokeSize, smokeYear
        )
        val drinking = if (switch_drink_history.isChecked) "on" else null
        val drinkingFrequenceStr = parseDefaultContent(tv_drink_frequency.text.toString())
        val drinkingFrequence = if(drinkingFrequenceStr.isEmpty()) null else drinkingFrequenceStr[0].toString()
        val drinkingSizeStr = parseDefaultContent(tv_drink_capacity.text.toString())
        val drinkingSize = if(drinkingSizeStr.isEmpty()) null else drinkingSizeStr[0].toString()
        val drinkingIsQuit = if (switch_is_quit_drink.isChecked) "on" else null
        val drinkingQuitTime = parseDefaultContent(tv_quit_drink_date.text.toString())
        val drinkingChemotherapy = if (switch_is_relapse_drink.isChecked) "on" else null
        var drinkingBean:PreviousHistoryBodyBean.Drinking? = PreviousHistoryBodyBean.Drinking(
            drinking,
            drinkingChemotherapy,
            drinkingFrequence,
            drinkingIsQuit,
            drinkingQuitTime,
            drinkingSize
        )
        val height = parseDefaultContent(tv_patient_height.text.toString()).toIntOrNull()
        val weight = parseDefaultContent(tv_patient_weight.text.toString()).toIntOrNull()
        val surfaceArea = parseDefaultContent(tv_patient_body_area.text.toString()).toIntOrNull()
        val eCOG = parseDefaultContent(tv_ECOG_score.text.toString()).toIntOrNull()
        val previousHistoryBody = PreviousHistoryBodyBean(
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
        mViewModel.editPreviousHistory(mSampleId,previousHistoryBody).observe(viewLifecycleOwner,
            Observer {
                if(it.code==200){
                    ToastUtils.showShort("既往史表单修改成功")
                }else{
                    ToastUtils.showShort("既往史表单修改失败")
                }
            })
    }

    private fun initBaseIllList(bean: PreviousHistoryResponseBean) {
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[0], bean.baseIll0 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[1], bean.baseIll1 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[2], bean.baseIll2 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[3], bean.baseIll3 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[4], bean.baseIll4 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[5], bean.baseIll5 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[6], bean.baseIll6 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[7], bean.baseIll7 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[8], bean.baseIll8 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[9], bean.baseIll9 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[10], bean.baseIll10 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[11], bean.baseIll11 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[12], bean.baseIll12 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[13], bean.baseIll13 == "on"))
        val illText = StringBuffer()
        for (ill in mBaseIllList) {
            if (ill.isChecked && ill.name != "其他，请描述") {
                illText.append("${ill.name},")
            }
        }
        if(bean.baseIllOther!=null){
            mOtherIll = bean.baseIllOther
        }
        if (!mBaseIllList[13].isChecked && illText.isNotEmpty()) {
            illText.deleteCharAt(illText.length - 1)
        } else {
            illText.append(mOtherIll)
        }
        tv_diseases_history.text = illText
    }

    private fun initUI() {
        cl_operation_history.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("外伤及手术史", arrayOf("无", "不详", "有，请详述")) { pos, text ->
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
            asCheckboxList(context, title, mBaseIllList, { data, pos ->
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
        switch_smoke_history.setOnCheckedChangeListener { compoundButton, isChecked ->
            switch_smoke_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_smoke.visibility = if(isChecked) View.VISIBLE else View.GONE
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
        switch_is_quit_smoke.setOnCheckedChangeListener { button, isChecked ->
            switch_is_quit_smoke.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_quit_smoke.visibility = if(isChecked) View.VISIBLE else View.GONE
        }
        cl_quit_smoke_date.setOnClickListener {
            showDatePickerDialog(tv_quit_smoke_date,parentFragmentManager)
        }
        cl_is_relapse_smoke.setOnClickListener {
            switch_is_relapse_smoke.isChecked = !switch_is_relapse_smoke.isChecked
        }
        switch_is_relapse_smoke.setOnCheckedChangeListener { button, isChecked ->
            switch_is_relapse_smoke.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_drink_history.setOnClickListener {
            switch_drink_history.isChecked = !switch_drink_history.isChecked
        }
        switch_drink_history.setOnCheckedChangeListener { button, isChecked ->
            switch_drink_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_drink.visibility = if(isChecked) View.VISIBLE else View.GONE
        }
        cl_drink_frequency.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "饮酒频率",
                    arrayOf("请设置","0=几乎不", "1=每周1-2次", "2=每周3-4次", "3=每周5-7次")
                ) { pos, text ->
                    tv_drink_frequency.text = text
                }.show()
        }
        cl_drink_capacity.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "每次饮酒量",
                    arrayOf("请设置","0=每次少量", "1=每周微醉", "2=偶尔大醉", "3=每次大醉")
                ) { pos, text ->
                    tv_drink_capacity.text = text
                }.show()
        }
        cl_is_quit_drink.setOnClickListener {
            switch_is_quit_drink.isChecked = !switch_is_quit_drink.isChecked
        }
        switch_is_quit_drink.setOnCheckedChangeListener { button, isChecked ->
            switch_is_quit_drink.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
            ll_quit_drink.visibility = if(isChecked) View.VISIBLE else View.GONE
        }
        cl_quit_drink_date.setOnClickListener {
            showDatePickerDialog(tv_quit_drink_date,parentFragmentManager)
        }
        cl_is_relapse_drink.setOnClickListener {
            switch_is_relapse_drink.isChecked = !switch_is_relapse_smoke.isChecked
        }
        switch_is_relapse_drink.setOnCheckedChangeListener { button, isChecked ->
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
