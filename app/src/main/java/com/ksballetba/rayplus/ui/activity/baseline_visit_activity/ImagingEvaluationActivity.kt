package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.ImagingEvaluationBodyBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.IMAGING_EVALUATION_BODY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.getImagingEvaluationWayListInHistory
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_imaging_evaluation.*
import org.jetbrains.anko.toast
import java.util.Calendar

class ImagingEvaluationActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_IMAGING_EVALUATION_PAGE = "REFRESH_IMAGING_EVALUATION_PAGE"
    }

    lateinit var mViewModel: BaseVisitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imaging_evaluation)
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
        setSupportActionBar(tb_imaging_evaluation)
        mViewModel = getBaseVisitViewModel(this)
        val imagingEvaluationBody =
            intent.getParcelableExtra<ImagingEvaluationBodyBean>(IMAGING_EVALUATION_BODY)
        if (imagingEvaluationBody != null) {
            loadData(imagingEvaluationBody)
        }
        cl_part.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("部位", "请输入部位") {
                tv_part.text = it
            }.show()
        }
        cl_way.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "方法",
                arrayOf("CT", "MRI", "超声", "X线平片", "PET-CT", "其他")
            ) { pos, text ->
                if (pos < 5) {
                    tv_way.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm("其他", "请输入其他方法") {
                        tv_way.text = it
                    }.show()
                }
            }.show()
        }
        cl_tumor_long_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤长径", "请输入肿瘤长径（cm）") {
                tv_tumor_long_dia.text = it
            }.show()
        }
        cl_tumor_short_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤短径", "请输入肿瘤短径（cm）") {
                tv_tumor_short_dia.text = it
            }.show()
        }
        cl_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear + 1}-$dayOfMonth"
                    tv_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择时间")
        }
        fab_save_imaging_evaluation.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID,-1)
            val cycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY,-1)
            if (imagingEvaluationBody==null){
                addOrEditData(sampleId,cycleNumber,null)
            }else{
                addOrEditData(sampleId,cycleNumber,imagingEvaluationBody.evaluateId)
            }

        }
    }

    private fun loadData(imagingEvaluationBody: ImagingEvaluationBodyBean) {
        tv_part.text = imagingEvaluationBody.part
        tv_way.text =
            if (imagingEvaluationBody.method == "其他") imagingEvaluationBody.methodOther else imagingEvaluationBody.method
        tv_tumor_long_dia.text = imagingEvaluationBody.tumorLong.toString()
        tv_tumor_short_dia.text = imagingEvaluationBody.tumorShort.toString()
        tv_date.text = imagingEvaluationBody.time
    }

    private fun addOrEditData(sampleId: Int, cycleNumber: Int, evaluateId: Int?) {
        val part = parseDefaultContent(tv_part.text.toString())
        var method = parseDefaultContent(tv_way.text.toString())
        var methodOther = parseDefaultContent(tv_way.text.toString())
        if (getImagingEvaluationWayListInHistory().contains(method)) {
            methodOther = ""
        } else {
            method = "其他"
        }
        val tumorLong = tv_tumor_long_dia.text.toString().toIntOrNull()
        val tumorShort = tv_tumor_short_dia.text.toString().toIntOrNull()
        val time = parseDefaultContent(tv_date.text.toString())
        val imagingEvaluationBody = ImagingEvaluationBodyBean(
            evaluateId, method, methodOther, part, time, tumorLong, tumorShort
        )
        mViewModel.editImagingEvaluation(sampleId, cycleNumber, imagingEvaluationBody).observe(this,
            Observer {
                if(it.code==200){
                    toast("体格报告单操作成功")
                    val intent = Intent(this, CRFActivity::class.java)
                    intent.action = REFRESH_IMAGING_EVALUATION_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    toast("体格报告单操作失败")
                }
            })
    }
}
