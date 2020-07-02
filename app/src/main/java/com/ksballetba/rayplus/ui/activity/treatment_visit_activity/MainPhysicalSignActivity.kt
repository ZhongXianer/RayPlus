package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.treatmentVisitData.MainPhysicalSignBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.MainPhysicalSignFragment.Companion.MAIN_PHYSICAL_SIGN_BODY
import com.ksballetba.rayplus.util.getMainPhysicalSignList
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_main_physical_sign.*
import org.jetbrains.anko.toast

class MainPhysicalSignActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_MAIN_PHYSICAL_SIGN_PAGE = "REFRESH_MAIN_PHYSICAL_SIGN_PAGE"
    }

    lateinit var mViewModel: TreatmentVisitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_physical_sign)
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
        setSupportActionBar(tb_main_physical_sign)
        mViewModel = getTreatmentVisitViewModel(this)
        val mainPhysicalSignBody =
            intent.getParcelableExtra<MainPhysicalSignBodyBean>(MAIN_PHYSICAL_SIGN_BODY)
        if (mainPhysicalSignBody != null) {
            loadData(mainPhysicalSignBody)
        }
        cl_physical_sign.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "症状体征和描述",
                arrayOf("高血压", "腹泻", "皮疹", "蛋白尿", "出血", "其他")
            ) { pos, text ->
                if (pos < 5) {
                    tv_physical_sign.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm("其他", "请输入其他症状体征和描述") {
                        tv_physical_sign.text = it
                    }.show()
                }
            }.show()
        }
        cl_start_date.setOnClickListener {
            showDatePickerDialog(tv_start_date,supportFragmentManager)
        }
        cl_end_date.setOnClickListener {
            showDatePickerDialog(tv_end_date,supportFragmentManager)
        }
        cl_exist_status.setOnClickListener {
            XPopup.Builder(this).asCenterList("存在状态", arrayOf("存在", "消失")) { pos, text ->
                tv_exist_status.text = text
                if (text == "存在") {
                    cl_end_date.visibility = View.GONE
                } else {
                    cl_end_date.visibility = View.VISIBLE
                }
            }.show()
        }
        fab_save_main_physical_sign.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
            val cycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY, -1)
            if (mainPhysicalSignBody == null) {
                addOrEditData(sampleId, cycleNumber, null)
            } else {
                addOrEditData(sampleId, cycleNumber, mainPhysicalSignBody.mainSymptomId)
            }
        }
    }

    private fun addOrEditData(sampleId: Int, cycleNumber: Int, mainSymptomId: Int?) {
        var symptomDescription = parseDefaultContent(tv_physical_sign.text.toString())
        var symptomDescriptionOther = parseDefaultContent(tv_physical_sign.text.toString())
        if (getMainPhysicalSignList().contains(symptomDescription)) {
            symptomDescriptionOther = ""
        } else {
            symptomDescription = "其他"
        }
        val startTime = parseDefaultContent(tv_start_date.text.toString())
        val endTime =
            if (cl_end_date.visibility == View.VISIBLE) parseDefaultContent(tv_end_date.text.toString()) else ""
        val existence =
            if (parseDefaultContent(tv_exist_status.text.toString()) == "存在") "0" else "1"
        val mainPhysicalSignBody =
            MainPhysicalSignBodyBean(
                endTime,
                existence,
                mainSymptomId,
                startTime,
                symptomDescription,
                symptomDescriptionOther
            )
        mViewModel.editMainPhysicalSign(sampleId, cycleNumber, mainPhysicalSignBody).observe(this,
            Observer {
                if(it.code==200){
                    toast("主要症状体征操作成功")
                    val intent = Intent(this, TreatmentVisitDetailActivity::class.java)
                    intent.action = REFRESH_MAIN_PHYSICAL_SIGN_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    toast("主要症状体征操作失败")
                }
            })
    }

    private fun loadData(mainPhysicalSignBody: MainPhysicalSignBodyBean) {
        tv_physical_sign.text =
            if (mainPhysicalSignBody.symptomDescription == "其他") mainPhysicalSignBody.symptomDescription else mainPhysicalSignBody.symptomDescriptionOther
        tv_start_date.text = mainPhysicalSignBody.startTime
        if(mainPhysicalSignBody.existence == "1"){
            cl_end_date.visibility = View.VISIBLE
            tv_exist_status.text = "消失"
        }else{
            cl_end_date.visibility = View.GONE
            tv_exist_status.text = "存在"
        }
        if(cl_end_date.visibility == View.VISIBLE){
            tv_end_date.text = mainPhysicalSignBody.endTime
        }
    }
}
