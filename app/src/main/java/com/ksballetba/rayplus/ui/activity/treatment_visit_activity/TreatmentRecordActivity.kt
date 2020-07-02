package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentRecordBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.TreatmentRecordFragment.Companion.TREATMENT_RECORD_BODY
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_treatment_record.*
import org.jetbrains.anko.toast

class TreatmentRecordActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_TREATMENT_RECORD_PAGE = "REFRESH_TREATMENT_RECORD_PAGE"
    }

    lateinit var mViewModel: TreatmentVisitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_record)
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
        setSupportActionBar(tb_treatment_record)
        mViewModel = getTreatmentVisitViewModel(this)
        val treatmentRecordBody =
            intent.getParcelableExtra<TreatmentRecordBodyBean>(TREATMENT_RECORD_BODY)
        if (treatmentRecordBody != null) {
            loadData(treatmentRecordBody)
        }
        cl_treatment_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("治疗名称", "请输入治疗名称") {
                tv_treatment_name.text = it
            }.show()
        }
        cl_drug_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("药物名称", "请输入药物名称") {
                tv_drug_name.text = it
            }.show()
        }
        cl_treatment_start_date.setOnClickListener {
            showDatePickerDialog(tv_treatment_start_date,supportFragmentManager)
        }
        cl_treatment_end_date.setOnClickListener {
            showDatePickerDialog(tv_treatment_end_date,supportFragmentManager)
        }
        cl_dose_usage.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("剂量及用法", "请输入剂量及用法") {
                tv_dose_usage.text = it
            }.show()
        }
        fab_save_treatment_record.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
            val cycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY, -1)
            if (treatmentRecordBody == null) {
                addOrEditData(sampleId, cycleNumber, null)
            } else {
                addOrEditData(sampleId, cycleNumber, treatmentRecordBody.treatmentRecordId)
            }
        }
    }

    private fun loadData(treatmentRecordBody: TreatmentRecordBodyBean) {
        tv_treatment_name.text = treatmentRecordBody.treatmentName
        tv_drug_name.text = treatmentRecordBody.medicineName
        tv_treatment_start_date.text = treatmentRecordBody.startTime
        tv_treatment_end_date.text = treatmentRecordBody.endTime
        tv_dose_usage.text = treatmentRecordBody.description
    }

    private fun addOrEditData(sampleId: Int, cycleNumber: Int, treatmentRecordId: Int?) {
        val treatmentName = parseDefaultContent(tv_treatment_name.text.toString())
        val medicineName = parseDefaultContent(tv_drug_name.text.toString())
        val startTime = parseDefaultContent(tv_treatment_start_date.text.toString())
        val endTime = parseDefaultContent(tv_treatment_end_date.text.toString())
        val description = parseDefaultContent(tv_dose_usage.text.toString())
        val treatmentRecordBody =
            TreatmentRecordBodyBean(
                description, endTime, medicineName, startTime, treatmentName, treatmentRecordId
            )
        mViewModel.editTreatmentRecord(sampleId, cycleNumber, treatmentRecordBody).observe(this,
            Observer {
                if(it.code==200){
                    toast("治疗记录单操作成功")
                    val intent = Intent(this, TreatmentVisitDetailActivity::class.java)
                    intent.action = REFRESH_TREATMENT_RECORD_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    toast("治疗记录单操作失败")
                }
            })
    }
}
