package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationBodyBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.CRF_PAGE
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.IMAGING_EVALUATION_BODY
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.REFRESH_PAGE
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.TREATMENT_VISIT_DETAIL_PAGE
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_imaging_evaluation.*
import org.jetbrains.anko.toast

class ImagingEvaluationActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_IMAGING_EVALUATION_PAGE = "REFRESH_IMAGING_EVALUATION_PAGE"
    }

    lateinit var mViewModel: BaseVisitViewModel
    private lateinit var mRefreshPage: String

    private lateinit var mImagingEvaluationBody: ImagingEvaluationBodyBean

    private var timeIsSet: Boolean = false
    private var describeIsSet: Boolean = false

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
        mRefreshPage = intent.getStringExtra(REFRESH_PAGE)
        val imagingEvaluationBody =
            intent.getParcelableExtra<ImagingEvaluationBodyBean>(IMAGING_EVALUATION_BODY)
        if (imagingEvaluationBody != null) {
            println(imagingEvaluationBody)
            mImagingEvaluationBody = imagingEvaluationBody
            loadData(imagingEvaluationBody)
        } else
            mImagingEvaluationBody =
                ImagingEvaluationBodyBean(
                    null,
                    null,
                    null,
                    null,
                    "",
                    null,
                    null,
                    null
                )
        cl_part.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("部位", "请输入部位") {
                tv_part.text = it
                mImagingEvaluationBody.part = it?.toString()
            }.show()
        }
        cl_way.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "方法",
                arrayOf("CT", "MRI", "超声", "X线平片", "PET-CT", "其他")
            ) { pos, text ->
                if (pos < 5) {
                    tv_way.text = text
                    mImagingEvaluationBody.methodOther = null
                    mImagingEvaluationBody.method = text
                } else {
                    XPopup.Builder(this).asInputConfirm("其他", "请输入其他方法") {
                        tv_way.text = it
                        mImagingEvaluationBody.method = "其他"
                        mImagingEvaluationBody.methodOther = it?.toString()
                    }.show()
                }
            }.show()
        }
        cl_tumor_long_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤长径", "请输入肿瘤长径（cm）") {
                tv_tumor_long_dia.text = it
                mImagingEvaluationBody.tumorLong = it?.toString()
            }.show()
        }
        cl_tumor_short_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤短径", "请输入肿瘤短径（cm）") {
                tv_tumor_short_dia.text = it
                mImagingEvaluationBody.tumorShort = it?.toString()
            }.show()
        }
        cl_date.setOnClickListener {
            showDatePickerDialog(tv_date, supportFragmentManager)
            timeIsSet = true
        }
        tv_des_content.setOnClickListener {
            describeIsSet = true
        }
        fab_save_imaging_evaluation.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
            val cycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY, -1)
            addOrEditData(sampleId, cycleNumber)
        }
    }

    private fun loadData(imagingEvaluationBody: ImagingEvaluationBodyBean) {
        tv_part.text = imagingEvaluationBody.part
        tv_way.text =
            if (imagingEvaluationBody.method == "其他") imagingEvaluationBody.methodOther else imagingEvaluationBody.method
        if (imagingEvaluationBody.tumorLong == null) tv_tumor_long_dia.text = ""
        else tv_tumor_long_dia.text = imagingEvaluationBody.tumorLong
        if (imagingEvaluationBody.tumorShort == null) tv_tumor_short_dia.text = ""
        else tv_tumor_short_dia.text = imagingEvaluationBody.tumorShort
        tv_date.text = imagingEvaluationBody.time
        tv_des_content.setText(imagingEvaluationBody.tumorDesc)
    }

    private fun addOrEditData(sampleId: Int, cycleNumber: Int) {
        if (mImagingEvaluationBody.evaluateId == null &&
            mImagingEvaluationBody.method == null &&
            mImagingEvaluationBody.methodOther == null &&
            mImagingEvaluationBody.part == null &&
            !describeIsSet &&
            mImagingEvaluationBody.tumorLong == null &&
            mImagingEvaluationBody.tumorShort == null && !timeIsSet
        ) {
            toast("参数无效")
            return
        }
        if (timeIsSet) mImagingEvaluationBody.time =
            parseDefaultContent(tv_date.text.toString())
        mImagingEvaluationBody.tumorDesc = tv_des_content.text?.toString()
        mViewModel.editImagingEvaluation(sampleId, cycleNumber, mImagingEvaluationBody)
            .observe(this,
                Observer {
                    if (it.code == 200) {
                        toast("体格报告单操作成功")
                        when (mRefreshPage) {
                            CRF_PAGE -> {
                                val intent = Intent(this, CRFActivity::class.java)
                                intent.action = REFRESH_IMAGING_EVALUATION_PAGE
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                            }
                            TREATMENT_VISIT_DETAIL_PAGE -> {
                                val intent = Intent(this, TreatmentVisitDetailActivity::class.java)
                                intent.action = REFRESH_IMAGING_EVALUATION_PAGE
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                            }
                        }
                    } else {
                        toast(it.msg)
                    }
                })
    }
}
