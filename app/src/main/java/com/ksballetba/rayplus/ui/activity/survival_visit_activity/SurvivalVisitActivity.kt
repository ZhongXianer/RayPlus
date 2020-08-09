package com.ksballetba.rayplus.ui.activity.survival_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitBodyBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.SurvivalVisitFragment.Companion.SURVIVAL_VISIT_BODY
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.SurvivalVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_survival_visit.*
import org.jetbrains.anko.toast

class SurvivalVisitActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_SURVIVAL_VISIT_PAGE = "REFRESH_SURVIVAL_VISIT_PAGE"
    }

    lateinit var mViewModel: SurvivalVisitViewModel
    lateinit var mSurvivalVisitBody: SurvivalVisitBodyBean

    private var dieReasonIsSet = false
    private var osMethodIsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survival_visit)
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
        setSupportActionBar(tb_survival_visit)
        mViewModel = getSurvivalVisitViewModel(this)
        val survivalVisitBody =
            intent.getParcelableExtra<SurvivalVisitBodyBean>(SURVIVAL_VISIT_BODY)
        if (survivalVisitBody != null) {
            loadData(survivalVisitBody)
            mSurvivalVisitBody = survivalVisitBody
        } else mSurvivalVisitBody =
            SurvivalVisitBodyBean(
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
                0
            )
        cl_visit_date.setOnClickListener {
            showDatePickerDialog(tv_visit_date, supportFragmentManager)
        }
        cl_visit_way.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(getString(R.string.visit_way), arrayOf("电话", "门诊", "住院")) { _, text ->
                    tv_visit_way.text = text
                }.show()
        }
        cl_anti_tumor_treat.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(getString(R.string.anti_tumor_treat), arrayOf("是", "否")) { _, text ->
                    tv_anti_tumor_treat.text = text
                }.show()
        }
        cl_live_status.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.live_status),
                getSurvivalStatus()
            ) { pos, text ->
                tv_live_status.text = text
                if (pos == 0) {
                    cl_death_cause.visibility = View.VISIBLE
                    cl_death_date.visibility = View.VISIBLE
                    cl_gather_way.visibility = View.VISIBLE
                } else {
                    cl_death_cause.visibility = View.GONE
                    cl_death_date.visibility = View.GONE
                    cl_gather_way.visibility = View.GONE
                }
            }.show()
        }
        cl_death_date.setOnClickListener {
            showDatePickerDialog(tv_death_date, supportFragmentManager)
        }
        cl_death_cause.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(getString(R.string.death_cause), arrayOf("疾病进展", "其他")) { pos, text ->
                    dieReasonIsSet = true;
                    if (pos < 1) {
                        tv_death_cause.text = text
                    } else {
                        XPopup.Builder(this)
                            .asInputConfirm(getString(R.string.death_cause), "请输入其他原因") {
                                tv_death_cause.text = it
                            }.show()
                    }
                }.show()
        }
        cl_gather_way.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(
                    getString(R.string.gather_way),
                    arrayOf(
                        "1.街道办开具死亡证明",
                        "2.民政局系统出具死亡证明",
                        "3.公安局及派出所出具死亡证明",
                        "4.火化证明或公墓数据",
                        "5.本院死亡的医疗文件",
                        "6.其他医院死亡的医疗文件",
                        "7.家属手写证明文件",
                        "8.电话随访获知"
                    )
                ) { pos, text ->
                    osMethodIsSet = true;
                    if (pos < 8) {
                        tv_gather_way.text = text
                    } else {
                        XPopup.Builder(this)
                            .asInputConfirm(getString(R.string.gather_way), "请输入其他收集形式") {
                                tv_gather_way.text = it
                            }.show()
                    }
                }.show()
        }
        cl_confirm_live_date.setOnClickListener {
            showDatePickerDialog(tv_confirm_live_date, supportFragmentManager)
        }
        cl_last_contact_date.setOnClickListener {
            showDatePickerDialog(tv_last_contact_date, supportFragmentManager)
        }
        fab_save_survival_visit.setOnClickListener {
            if (mSurvivalVisitBody.isSubmit == 1)
                ToastUtils.showShort("已提交！不能再编辑！")
            else {
                val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
                addOrEditData(sampleId)
            }
        }
    }

    /**
     * 展示数据
     */
    private fun loadData(survivalVisitBody: SurvivalVisitBodyBean) {
        if (survivalVisitBody.interviewTime != null)
            tv_visit_date.text = survivalVisitBody.interviewTime
        if (survivalVisitBody.interviewWay != null) {
            tv_visit_way.text = getInterviewWay()[survivalVisitBody.interviewWay]
        }
        if (survivalVisitBody.hasOtherTreatment != null) {
            tv_anti_tumor_treat.text =
                if (survivalVisitBody.hasOtherTreatment == 1) "是" else "否"
        }
        if (survivalVisitBody.survivalStatus != null) {
            tv_live_status.text = getSurvivalStatus()[survivalVisitBody.survivalStatus]
            if (survivalVisitBody.survivalStatus == 0) {
                cl_death_cause.visibility = View.VISIBLE
                cl_death_date.visibility = View.VISIBLE
                cl_gather_way.visibility = View.VISIBLE
            } else {
                cl_death_cause.visibility = View.GONE
                cl_death_date.visibility = View.GONE
                cl_gather_way.visibility = View.GONE
            }
        }
        if (survivalVisitBody.dieTime != null)
            tv_death_date.text = survivalVisitBody.dieTime
        if (survivalVisitBody.dieReason != null) {
            tv_death_cause.text =
                if (survivalVisitBody.dieReason < 1) "疾病进展" else survivalVisitBody.otherReason
        }
        if (survivalVisitBody.oSMethod != null) {
            tv_gather_way.text =
                if (survivalVisitBody.oSMethod < 8) getOSMethod()[survivalVisitBody.oSMethod] else survivalVisitBody.otherMethod
        }
        if (survivalVisitBody.statusConfirmTime != null)
            tv_confirm_live_date.text = survivalVisitBody.statusConfirmTime
        if (survivalVisitBody.lastTimeSurvival != null)
            tv_last_contact_date.text = survivalVisitBody.lastTimeSurvival
    }

    private fun addOrEditData(sampleId: Int) {
        val interviewTime = parseDefaultContent(tv_visit_date.text.toString())
        val interviewWayStr = parseDefaultContent(tv_visit_way.text.toString())
        val interviewWay =
            if (interviewWayStr.isEmpty()) null else getInterviewWay().indexOf(interviewWayStr)
        val hasOtherTreatmentStr = parseDefaultContent(tv_anti_tumor_treat.text.toString())
        val hasOtherTreatment =
            if (hasOtherTreatmentStr.isEmpty()) null else if (hasOtherTreatmentStr == "是") 1 else 0
        val survivalStatusStr = parseDefaultContent(tv_live_status.text.toString())
        val survivalStatus =
            if (survivalStatusStr.isEmpty()) null else getSurvivalStatus().indexOf(
                survivalStatusStr
            )
        val dieTime = parseDefaultContent(
            tv_death_date.text.toString()
        )
        val dieReasonStr = parseDefaultContent(tv_death_cause.text.toString())
        var otherReason = mSurvivalVisitBody.otherReason
        var dieReason: Int? = mSurvivalVisitBody.dieReason
        if (dieReasonIsSet)
            if (dieReasonStr == "疾病进展") {
                dieReason = 0
                otherReason = ""
            } else {
                dieReason = 1
                otherReason = parseDefaultContent(tv_death_cause.text.toString())
            }
        val OSMethodStr = parseDefaultContent(tv_gather_way.text.toString())
        var otherMethod = mSurvivalVisitBody.otherMethod
        var OSMethod: Int? = mSurvivalVisitBody.oSMethod
        if (osMethodIsSet)
            if (getOSMethod().contains(OSMethodStr)) {
                OSMethod = getOSMethod().indexOf(OSMethodStr)
                otherMethod = ""
            } else {
                OSMethod = 8
                otherMethod = parseDefaultContent(tv_gather_way.text.toString())
            }
        val statusConfirmTime = parseDefaultContent(tv_confirm_live_date.text.toString())
        val lastTimeSurvival = parseDefaultContent(tv_last_contact_date.text.toString())
        mSurvivalVisitBody =
            SurvivalVisitBodyBean(
                dieReason,
                dieTime,
                hasOtherTreatment,
                mSurvivalVisitBody.interviewId,
                interviewTime,
                interviewWay,
                lastTimeSurvival,
                OSMethod,
                otherMethod,
                otherReason,
                statusConfirmTime,
                survivalStatus,
                mSurvivalVisitBody.isSubmit
            )
        mViewModel.editSurvivalVisit(sampleId, mSurvivalVisitBody).observe(this, Observer {
            if (it.code == 200) {
                toast("生存期随访操作成功")
                val intent = Intent(this, CRFActivity::class.java)
                intent.action = REFRESH_SURVIVAL_VISIT_PAGE
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                toast(it.msg)
                val intent = Intent(this, CRFActivity::class.java)
                intent.action = REFRESH_SURVIVAL_VISIT_PAGE
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })
    }

}
