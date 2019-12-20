package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_treatment_history.*
import java.util.*

class TreatmentHistoryActivity : AppCompatActivity() {

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

    private fun initUI(){
        setSupportActionBar(tb_treatment_history)
        cl_treatment_line.setOnClickListener {
            XPopup.Builder(this).asCenterList("几线治疗", arrayOf("一线治疗", "二线治疗", "三线治疗", "四线治疗", "五线治疗")) { pos, text ->
                tv_treatment_line.text = text
                if(pos>0){
                    ll_not_first_line_treatment.visibility = View.VISIBLE
                }else{
                    ll_not_first_line_treatment.visibility = View.GONE
                }
            }.show()
        }
        cl_first_line_treatment.setOnClickListener {
            XPopup.Builder(this).asCenterList("一线治疗", arrayOf("无", "不详", "有，请填下表")) { pos, text ->
                if(pos<2){
                    ll_treatment_history_detail.visibility = View.GONE
                }else{
                    ll_treatment_history_detail.visibility = View.VISIBLE
                }
                tv_first_line_treatment.text = text
            }.show()
        }
        cl_last_best_treatment.setOnClickListener {
            XPopup.Builder(this).asCenterList("一线治疗最佳疗效", arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(PD)", "疗效不详(UK)")) { pos, text ->
                tv_last_best_treatment.text = text
            }.show()
        }
        cl_last_treatment_growth_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_last_treatment_growth_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_last_treatment_growth_part.setOnClickListener {

        }
        cl_is_biopsy_again.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.is_biopsy_again), arrayOf("是","否")) { pos, text ->
                tv_is_biopsy_again.text = text
            }.show()
        }
        cl_biopsy_way.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.biopsy_way),
                arrayOf("无", "手术", "胸腔镜", "纵膈镜", "经皮肺穿刺", "纤支镜", "E-BUS", "EUS-FNA","淋巴结活检","其他")
            ) { pos, text ->
                if (pos < 9) {
                    tv_biopsy_way.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm(getString(R.string.biopsy_way), "请输入其他活检方式") {
                        tv_biopsy_way.text = it
                    }.show()
                }
            }.show()
        }
        cl_biopsy_pathological_type.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.biopsy_pathological_type),
                arrayOf("无", "与第1次活检病理类型一致", "与第1次活检病理类型不一致")
            ) { pos, text ->
                if (pos < 2) {
                    tv_biopsy_pathological_type.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm(getString(R.string.biopsy_pathological_type), "请输入其他活检病理类型") {
                        tv_biopsy_pathological_type.text = it
                    }.show()
                }
            }.show()
        }
        cl_genetic_test_sample.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.genetic_test_sample),
                arrayOf("无", "外周血", "原发灶组织","转移灶组织")
            ) { pos, text ->
                if (pos < 3) {
                    tv_genetic_test_sample.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm(getString(R.string.genetic_test_sample), "请输入其他基因检测标本") {
                        tv_genetic_test_sample.text = it
                    }.show()
                }
            }.show()
        }
        cl_genetic_test_way.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.genetic_test_way),
                arrayOf("无", "ARMS", "FISH","二代测序")
            ) { pos, text ->
               tv_genetic_test_way.text = text
            }.show()
        }
        cl_genetic_mutation_type.setOnClickListener {

        }
        cl_PD_L1_expression.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList("PD-L1表达", arrayOf("未测", "不详", ">50%", "1%-50%","<1%","阴性")) { pos, text ->
                    tv_PD_L1_expression.text = text
                }.show()
        }
        cl_tumor_mutation_load.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList("肿瘤突变负荷(TMB)", arrayOf("未测", "不详", "数量（个突变/Mb）")) { pos, text ->
                    if(pos<3){
                        tv_tumor_mutation_load.text = text
                    }else{
                        XPopup.Builder(this).asInputConfirm("基因检测标本", "请输入转移灶组织描述") {
                            tv_tumor_mutation_load.text = it
                        }.show()
                    }
                }.show()
        }
        cl_microsatellite_instability.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList("微卫星不稳定性(MSI)", arrayOf("未测", "不详", "微卫星稳定性", "微卫星不稳定性")) { pos, text ->
                    tv_microsatellite_instability.text = text
                }.show()
        }
        cl_treatment_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_treatment_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
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
        cb_operation.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("手术","请输入手术（手术部位及方式）"){
                    tv_operation.text = it
                }.show()
            }
        }
        cb_radiotherapy.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("放疗","请输入放疗（放疗部位及剂量）"){
                    tv_radiotherapy.text = it
                }.show()
            }
        }
        cb_chemotherapy.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("化疗","请输入化疗（药名，使用剂量及频率，副作用）"){
                    tv_chemotherapy.text = it
                }.show()
            }
        }
        cb_targeted_therapy.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("靶向治疗","请输入靶向治疗（药名，使用剂量及频率，副作用）"){
                    tv_targeted_therapy.text = it
                }.show()
            }
        }
        cb_immunotherapy.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("免疫治疗","请输入免疫治疗（药名，使用剂量及频率，副作用）"){
                    tv_immunotherapy.text = it
                }.show()
            }
        }
        cb_other_therapy.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                XPopup.Builder(this).asInputConfirm("其他治疗","请输入其他治疗（药名，使用剂量及频率，副作用）"){
                    tv_other_therapy.text = it
                }.show()
            }
        }
    }
}
