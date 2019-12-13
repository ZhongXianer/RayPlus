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
            XPopup.Builder(this).asCenterList("几线治疗", arrayOf("一线治疗", "二线治疗", "三线治疗", "四线治疗", "五线治疗")) { _, text ->
                tv_treatment_line.text = text
            }.show()
        }
        cl_first_line_treatment.setOnClickListener {
            XPopup.Builder(this).asCenterList("一线治疗", arrayOf("无", "不详", "有，请填下表")) { pos, text ->
                if(pos<2){
                    ll_first_line_treatment.visibility = View.GONE
                }else{
                    ll_first_line_treatment.visibility = View.VISIBLE
                }
                tv_first_line_treatment.text = text
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
