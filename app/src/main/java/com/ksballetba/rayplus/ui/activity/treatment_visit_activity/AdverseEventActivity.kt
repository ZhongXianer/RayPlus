package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.AdverseEventBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.AdverseEventFragment.Companion.ADVERSE_EVENT_BODY
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_adverse_event.*
import org.jetbrains.anko.toast
import java.util.Calendar

class AdverseEventActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AdverseEventActivity"
        const val REFRESH_ADVERSE_EVENT_PAGE = "REFRESH_ADVERSE_EVENT_PAGE"
    }

    lateinit var mViewModel: TreatmentVisitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse_event)
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
        setSupportActionBar(tb_adverse_event)
        mViewModel = getTreatmentVisitViewModel(this)
        val adverseEventBody = intent.getParcelableExtra<AdverseEventBodyBean>(ADVERSE_EVENT_BODY)
        if(adverseEventBody!=null){
            loadData(adverseEventBody)
        }
        cl_adverse_event_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("不良事件名称","请输入不良事件名称"){
                tv_adverse_event_name.text = it
            }.show()
        }
        cl_is_serious.setOnClickListener {
            XPopup.Builder(this).asCenterList("是否为严重不良事件", arrayOf("是","否")){_, text ->
                tv_is_serious.text = text
                if(text=="是"){
                    ll_server_event.visibility = View.VISIBLE
                }else{
                    ll_server_event.visibility = View.GONE
                }
            }.show()
        }
        cl_toxicity_grading.setOnClickListener {
            XPopup.Builder(this).asCenterList("毒性分级", getAdverseEventToxicityClassify()){_, text ->
                tv_toxicity_grading.text = text
            }.show()
        }
        cl_start_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_start_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_drug_relationship.setOnClickListener {
            XPopup.Builder(this).asCenterList("与药物关系", arrayOf("肯定有关","很可能有关","可能有关","可能无关","肯定无关")){_, text ->
                tv_drug_relationship.text = text
            }.show()
        }
        cl_take_measure.setOnClickListener {
            XPopup.Builder(this).asCenterList("采取措施", getAdverseEventMeasure()){_, text ->
                tv_take_measure.text = text
            }.show()
        }
        cl_is_drug_treat.setOnClickListener {
            XPopup.Builder(this).asCenterList("是否进行药物治疗", arrayOf("是","否")){_, text ->
                tv_is_drug_treat.text = text
            }.show()
        }
        cl_return.setOnClickListener {
            XPopup.Builder(this).asCenterList("转归", arrayOf("症状消失","缓解","持续","加重","恢复伴后遗症","死亡")){_, text ->
                tv_return.text = text
            }.show()
        }
        cl_return_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_return_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_report_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_report_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_report_type.setOnClickListener {
            XPopup.Builder(this).asCenterList("报告类型", getAdverseEventReportType()){ _, text ->
                tv_report_type.text = text
            }.show()
        }
        cl_SAE_diagnose.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(getString(R.string.SAE_diagnose),"请输入诊断"){
                tv_SAE_diagnose.text = it
            }.show()
        }
        cl_SAE_state.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.SAE_state), arrayOf("死亡","导致住院","延长住院时间","伤残","功能障碍","导致先天畸形","危及生命","怀孕","其他情况")){pos,text->
                if(pos<8){
                    tv_SAE_state.text = text
                }else{
                    XPopup.Builder(this).asInputConfirm(getString(R.string.SAE_diagnose),"请输入其他情况"){
                        tv_SAE_state.text = it
                    }.show()
                }
            }.show()
        }
        cl_die_time.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_die_time.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_SAE_start_time.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_SAE_start_time.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_medicine_measure.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.medicine_measure), getAdverseEventMedicineMeasure()){_, text ->
                tv_medicine_measure.text = text
            }.show()
        }
        cl_SAE_recover.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.SAE_recover), getAdverseEventSAERecover()){ _, text ->
                tv_SAE_recover.text = text
            }.show()
        }
        cl_SAE_relation.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.SAE_relation), getAdverseEventMedicineRelation()){_, text ->
                tv_SAE_relation.text = text
            }.show()
        }
        fab_save_adverse_event.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID, -1)
            val cycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY, -1)
            if (adverseEventBody == null) {
                addOrEditData(sampleId, cycleNumber, null)
            } else {
                addOrEditData(sampleId, cycleNumber, adverseEventBody.adverseEventId)
            }
        }
    }

    private fun addOrEditData(sampleId: Int, cycleNumber: Int, adverseEventId: Int?) {
        var adverseEvent:AdverseEventBodyBean
        val adverseEventName = parseDefaultContent(tv_adverse_event_name.text.toString())
        val isServerEventStr = parseDefaultContent(tv_is_serious.text.toString())
        val isServerEvent = if(isServerEventStr.isEmpty()) null else if(isServerEventStr == "是") 1 else 0
        val toxicityClassificationStr = parseDefaultContent(tv_toxicity_grading.text.toString())
        val toxicityClassification = if(toxicityClassificationStr.isEmpty()) null else getAdverseEventToxicityClassify().indexOf(toxicityClassificationStr)
        val startTime = parseDefaultContent(tv_start_date.text.toString())
        val medicineRelationStr = parseDefaultContent(tv_drug_relationship.text.toString())
        val medicineRelation = if(medicineRelationStr.isEmpty()) null else getAdverseEventMedicineRelation().indexOf(medicineRelationStr)
        val measureStr = parseDefaultContent(tv_take_measure.text.toString())
        val measure = if(measureStr.isEmpty()) null else getAdverseEventMeasure().indexOf(measureStr)
        val isUsingMedicineStr = parseDefaultContent(tv_is_drug_treat.text.toString())
        val isUsingMedicine = if(isUsingMedicineStr.isEmpty()) null else if(isUsingMedicineStr == "是") 1 else 0
        val recoverStr = parseDefaultContent(tv_return.text.toString())
        val recover = if(recoverStr.isEmpty()) null else recoverStr
        val recoverTime = parseDefaultContent(tv_return_date.text.toString())
        if(ll_server_event.visibility == View.GONE){
            adverseEvent = AdverseEventBodyBean(
                adverseEventId,adverseEventName,null,isServerEvent,isUsingMedicine,measure,null,medicineRelation,null,recover,recoverTime,null,null,null,null,null,null,null,startTime,toxicityClassification
            )
        }else{
            val reportTime = parseDefaultContent(tv_report_date.text.toString())
            val reportTypeStr = parseDefaultContent(tv_report_type.text.toString())
            val reportType = if(reportTypeStr.isEmpty()) null else getAdverseEventReportType().indexOf(reportTypeStr)
            val sAEDiagnose = parseDefaultContent(tv_SAE_diagnose.text.toString())
            val sAEStateStr = parseDefaultContent(tv_SAE_state.text.toString())
            var otherSAEState = parseDefaultContent(tv_SAE_state.text.toString())
            var sAEState = 0
            if(getAdverseEventSAEState().contains(sAEStateStr)){
                sAEState = getAdverseEventSAEState().indexOf(sAEStateStr)
                otherSAEState = ""
            }else{
                sAEState = 8
            }
            val dieTime = parseDefaultContent(tv_die_time.text.toString())
            val sAEStartTime = parseDefaultContent(tv_SAE_start_time.text.toString())
            val medicineMeasureStr = parseDefaultContent(tv_medicine_measure.text.toString())
            val medicineMeasure = if(medicineMeasureStr.isEmpty()) null else getAdverseEventMedicineMeasure().indexOf(medicineMeasureStr)
            val sAERecoverStr = parseDefaultContent(tv_SAE_recover.text.toString())
            val sAERecover = if(sAERecoverStr.isEmpty()) null else getAdverseEventSAERecover().indexOf(sAERecoverStr)
            val sAERelationStr = parseDefaultContent(tv_SAE_relation.text.toString())
            val sAERelation = if(sAERelationStr.isEmpty()) null else getAdverseEventMedicineRelation().indexOf(sAERelationStr)
            adverseEvent = AdverseEventBodyBean(
                adverseEventId,adverseEventName,dieTime,isServerEvent,isUsingMedicine,measure,medicineMeasure,medicineRelation,otherSAEState,recover,recoverTime,reportTime,reportType
                ,sAEDiagnose,sAERecover,sAERelation,sAEStartTime,sAEState,startTime,toxicityClassification
            )
        }
        mViewModel.editAdverseEvent(sampleId, cycleNumber, adverseEvent).observe(this,
            Observer {
                if(it.code==200){
                    toast("不良事件操作成功")
                    val intent = Intent(this, TreatmentVisitDetailActivity::class.java)
                    intent.action = REFRESH_ADVERSE_EVENT_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    toast("不良事件操作失败")
                }
            })
    }

    private fun loadData(adverseEventBody: AdverseEventBodyBean) {
        tv_adverse_event_name.text = adverseEventBody.adverseEventName
        if(adverseEventBody.isServerEvent!=null){
            tv_is_serious.text = if(adverseEventBody.isServerEvent==1) "是" else "否"
            ll_server_event.visibility = if(adverseEventBody.isServerEvent==1) View.VISIBLE else View.GONE
        }
        if(adverseEventBody.toxicityClassification!=null){
            tv_toxicity_grading.text = getAdverseEventToxicityClassify()[adverseEventBody.toxicityClassification]
        }
        tv_start_date.text = adverseEventBody.startTime
        if(adverseEventBody.medicineRelation!=null&&adverseEventBody.medicineRelation!=-1){
            tv_drug_relationship.text = getAdverseEventMedicineRelation()[adverseEventBody.medicineRelation]
        }
        if(adverseEventBody.measure!=null&&adverseEventBody.measure!=-1){
            tv_take_measure.text = getAdverseEventMeasure()[adverseEventBody.measure]
        }
        if(adverseEventBody.isUsingMedicine!=null){
            tv_is_drug_treat.text = if(adverseEventBody.isUsingMedicine==1) "是" else "否"
        }
        tv_return.text = adverseEventBody.recover
        tv_return_date.text = adverseEventBody.recoverTime
        if(ll_server_event.visibility == View.VISIBLE){
            tv_report_date.text = adverseEventBody.reportTime
            if(adverseEventBody.reportType!=null){
                tv_report_type.text = getAdverseEventReportType()[adverseEventBody.reportType]
            }
            tv_SAE_diagnose.text = adverseEventBody.sAEDiagnose
            if(adverseEventBody.sAEState!=null){
                tv_SAE_state.text = if(adverseEventBody.sAEState<8) getAdverseEventSAEState()[adverseEventBody.sAEState] else adverseEventBody.otherSAEState
            }
            tv_die_time.text = adverseEventBody.dieTime
            tv_SAE_start_time.text = adverseEventBody.sAEStartTime
            if(adverseEventBody.medicineMeasure!=null){
                tv_medicine_measure.text = getAdverseEventMedicineMeasure()[adverseEventBody.medicineMeasure]
            }
            if(adverseEventBody.sAERecover!=null){
                tv_SAE_recover.text = getAdverseEventSAERecover()[adverseEventBody.sAERecover]
            }
            if(adverseEventBody.sAERelation!=null){
                tv_SAE_relation.text = getAdverseEventMedicineRelation()[adverseEventBody.sAERelation]
            }
        }
    }
}
