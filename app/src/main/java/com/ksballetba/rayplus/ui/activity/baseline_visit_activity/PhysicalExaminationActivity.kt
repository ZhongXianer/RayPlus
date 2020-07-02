package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseLineData.PhysicalExaminationBodyBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment.PhysicalExaminationFragment.Companion.PHYSICAL_EXAMINATION_BODY
import com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment.PhysicalExaminationFragment.Companion.REPORT_ID
import com.ksballetba.rayplus.util.getBaselineVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_physical_examination.*
import org.jetbrains.anko.toast

class PhysicalExaminationActivity : AppCompatActivity() {

    companion object{
        const val REFRESH_PHYSICAL_EXAMINATION_PAGE = "REFRESH_PHYSICAL_EXAMINATION_PAGE"
    }

    lateinit var mViewModel:BaselineVisitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physical_examination)
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
        setSupportActionBar(tb_physical_examination)
        mViewModel = getBaselineVisitViewModel(this)
        val physicalExaminationBody = intent.getParcelableExtra<PhysicalExaminationBodyBean>(PHYSICAL_EXAMINATION_BODY)
        if(physicalExaminationBody!=null){
            loadData(physicalExaminationBody)
        }
        cl_date.setOnClickListener {
            showDatePickerDialog(tv_date,supportFragmentManager)
        }
        cl_body_temperature.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("体温（℃）","请输入体温"){
                tv_body_temperature.text = it
            }.show()
        }
        cl_breathe.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("呼吸（次/分）","请输入呼吸"){
                tv_breathe.text = it
            }.show()
        }
        cl_blood_systolic_pressure.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("血压（mmHg ）","请输入收缩压"){
                tv_blood_systolic_pressure.text = it
            }.show()
        }
        cl_blood_diastolic_pressure.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("血压（mmHg ）","请输入舒张压"){
                tv_blood_diastolic_pressure.text = it
            }.show()
        }
        cl_heart_rate.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("心率（次/分）","请输入心率"){
                tv_heart_rate.text = it
            }.show()
        }
        fab_save_physical_examination.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID,-1)
            val reportId = intent.getIntExtra(REPORT_ID,-1)
            if(reportId==-1){
                addOrEditPhysicalExamination(sampleId,null)
            }else{
                addOrEditPhysicalExamination(sampleId,reportId)
            }

        }
    }

    private fun loadData(physicalExaminationBody: PhysicalExaminationBodyBean){
        tv_date.text = physicalExaminationBody.time
        tv_body_temperature.text = physicalExaminationBody.temperature.toString()
        tv_breathe.text = physicalExaminationBody.breathFrequency.toString()
        tv_blood_systolic_pressure.text = physicalExaminationBody.maxpressure.toString()
        tv_blood_diastolic_pressure.text = physicalExaminationBody.minpressure.toString()
        tv_heart_rate.text = physicalExaminationBody.heartRate.toString()
    }

    private fun addOrEditPhysicalExamination(sampleId:Int,reportId:Int?){
        val time = parseDefaultContent(tv_date.text.toString())
        val temperature = tv_body_temperature.text.toString().toFloatOrNull()
        val breathFrequency = tv_breathe.text.toString().toIntOrNull()
        val maxpressure = tv_blood_systolic_pressure.text.toString().toIntOrNull()
        val minpressure = tv_blood_diastolic_pressure.text.toString().toIntOrNull()
        val heartRate = tv_heart_rate.text.toString().toIntOrNull()
        val physicalExaminationBodyBean =
            PhysicalExaminationBodyBean(
                breathFrequency,
                heartRate,
                maxpressure,
                minpressure,
                reportId,
                temperature,
                time
            )
        mViewModel.editPhysicalExamination(sampleId,physicalExaminationBodyBean).observe(this,androidx.lifecycle.Observer {
            if(it.code==200){
                toast("体格报告单操作成功")
                val intent = Intent(this,CRFActivity::class.java)
                intent.action = REFRESH_PHYSICAL_EXAMINATION_PAGE
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }else{
                toast("体格报告单操作失败")
            }
        })
    }
}
