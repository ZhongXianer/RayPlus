package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.data.bean.PreviousHistoryBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_previous_history.*
import java.util.Calendar
import com.ksballetba.rayplus.util.asCheckboxList
import com.ksballetba.rayplus.util.getBaseIllListInHistory
import com.ksballetba.rayplus.util.getBaselineVisitViewModel
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
        switch_smoke_history.setSwitchTextAppearance(context, R.style.s_off)
        switch_is_quit_smoke.setSwitchTextAppearance(context, R.style.s_off)
        switch_is_relapse_smoke.setSwitchTextAppearance(context, R.style.s_off)
        switch_drink_history.setSwitchTextAppearance(context, R.style.s_off)
        switch_is_quit_drink.setSwitchTextAppearance(context, R.style.s_off)
        switch_is_relapse_drink.setSwitchTextAppearance(context, R.style.s_off)
        mViewModel.getPreviousHistory(mSampleId).observe(viewLifecycleOwner, Observer {
            LogUtils.tag(TAG).d(it)
//            tv_operation_history.text = if(it.surgery!="其他") it.surgery else it.surgeryOther
//            initBaseIllList(it.baseIll)
//            val illText = StringBuffer()
//            for (ill in mBaseIllList){
//                if(ill.isChecked&&ill.name!="其他，请描述"){
//                    illText.append("${ill.name},")
//                }
//            }
//            if(!mBaseIllList[13].isChecked&&illText.isNotEmpty()){
//                illText.deleteCharAt(illText.length-1)
//            }else{
//                illText.append(it.baseIll.baseIllOther)
//            }
//            tv_diseases_history.text = illText
        })
    }

    private fun initBaseIllList(baseIll: PreviousHistoryBean.BaseIll){
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[0],baseIll.baseIll1 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[1],baseIll.baseIll2 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[2],baseIll.baseIll3 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[3],baseIll.baseIll4 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[4],baseIll.baseIll5 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[5],baseIll.baseIll6 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[6],baseIll.baseIll7 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[7],baseIll.baseIll8 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[8],baseIll.baseIll9 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[9],baseIll.baseIll10 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[10],baseIll.baseIll11 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[11],baseIll.baseIll12 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[12],baseIll.baseIll13 == "on"))
        mBaseIllList.add(BaseCheckBean(getBaseIllListInHistory()[13],baseIll.baseIllOther.isNotEmpty()))
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
            var data = mutableListOf(
                BaseCheckBean("无", false),
                BaseCheckBean("不详", false),
                BaseCheckBean("高血压", true),
                BaseCheckBean("冠心病", false),
                BaseCheckBean("糖尿病", true),
                BaseCheckBean("慢性阻塞性肺疾病", false),
                BaseCheckBean("支气管哮喘", false),
                BaseCheckBean("肺结核", true),
                BaseCheckBean("间质性肺疾病", false),
                BaseCheckBean("高脂血症", false),
                BaseCheckBean("病毒性肝炎", false),
                BaseCheckBean("风湿免疫性疾病", false),
                BaseCheckBean("肾脏病", false),
                BaseCheckBean("其他，请描述", false)
            )
            var otherDiseases = ""
            val diseases = StringBuffer()
            asCheckboxList(context, title, data, { data, pos ->
                if (data.name == "其他，请描述") {
                    XPopup.Builder(context).asInputConfirm("基础疾病史", "请输入基础疾病史") {
                        otherDiseases = it
                    }.show()
                }
            }, { checkedData ->
                for (d in checkedData) {
                    if (d.isChecked&&d.name!="其他，请描述") {
                        diseases.append("${d.name},")
                    }
                }
                if (diseases.isNotEmpty()&&otherDiseases.isBlank()) {
                    diseases.deleteCharAt(diseases.length - 1)
                }
                diseases.append(otherDiseases)
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
            if (isChecked) {
                ll_smoke.visibility = View.VISIBLE
            } else {
                ll_smoke.visibility = View.GONE
            }
            switch_smoke_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
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
            if (isChecked) {
                ll_quit_smoke.visibility = View.VISIBLE
            } else {
                ll_quit_smoke.visibility = View.GONE
            }
            switch_is_quit_smoke.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_quit_smoke_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_quit_smoke_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "戒烟日期")
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
            if (isChecked) {
                ll_drink.visibility = View.VISIBLE
            } else {
                ll_drink.visibility = View.GONE
            }
            switch_drink_history.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_drink_frequency.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "饮酒频率",
                    arrayOf("0=几乎不", "1=每周1-2次", "2=每周3-4次", "3=每周5-7次")
                ) { pos, text ->
                    tv_drink_frequency.text = text
                }.show()
        }
        cl_drink_capacity.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    "每次饮酒量",
                    arrayOf("0=每次少量", "1=每周微醉", "2=偶尔大醉", "3=每次大醉")
                ) { pos, text ->
                    tv_drink_capacity.text = text
                }.show()
        }
        cl_is_quit_drink.setOnClickListener {
            switch_is_quit_drink.isChecked = !switch_is_quit_drink.isChecked
        }
        switch_is_quit_drink.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                ll_quit_drink.visibility = View.VISIBLE
            } else {
                ll_quit_drink.visibility = View.GONE
            }
            switch_is_quit_drink.setSwitchTextAppearance(
                context,
                if (isChecked) R.style.s_on else R.style.s_off
            )
        }
        cl_quit_drink_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_quit_drink_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "戒烟日期")
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
    }

}
