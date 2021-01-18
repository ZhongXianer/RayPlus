package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.treatmentVisitData.MainPhysicalSignBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.MainPhysicalSignFragment.Companion.MAIN_PHYSICAL_SIGN_BODY
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_main_physical_sign.*
import kotlinx.android.synthetic.main.activity_main_physical_sign.tv_end_date
import kotlinx.android.synthetic.main.activity_main_physical_sign.tv_physical_sign
import kotlinx.android.synthetic.main.activity_main_physical_sign.tv_start_date
import kotlinx.android.synthetic.main.item_main_physical_sign.*
import org.jetbrains.anko.toast

class MainPhysicalSignActivity : AppCompatActivity() {

    companion object {
        const val REFRESH_MAIN_PHYSICAL_SIGN_PAGE = "REFRESH_MAIN_PHYSICAL_SIGN_PAGE"
    }

    lateinit var mViewModel: TreatmentVisitViewModel

    private var symptomDescription: String? = null
    private var symptomDescriptionOther: String? = null
    var symptomDescriptionIsClicked = false

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
                arrayOf("无", "高血压", "腹泻", "皮疹", "蛋白尿", "出血", "其他")
            ) { pos, text ->
                symptomDescriptionIsClicked = true
                if (pos < 6) {
                    tv_physical_sign.text = text
                } else {
                    XPopup.Builder(this).asInputConfirm("其他", "请输入其他症状体征和描述") {
                        tv_physical_sign.text = it
                    }.show()
                }
            }.show()
        }
        cl_toxicity_classification.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "毒性分级",
                arrayOf("1级", "2级", "3级", "4级", "5级")
            ) { _, text ->
                tv_toxicity_classification.text = text
            }.show()
        }
        cl_start_date.setOnClickListener {
            showDatePickerDialog(tv_start_date, supportFragmentManager)
        }
        cl_medicine_relation.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "与药物关系",
                arrayOf("肯定有关", "很可能有关", "可能有关", "可能无关", "肯定无关")
            ) { _, text ->
                tv_medicine_relation.text = text
            }.show()
        }
        cl_measure.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "采取措施",
                arrayOf("剂量不变", "减少剂量", "暂停用药", "停止用药", "实验用药已结束")
            ) { _, text ->
                tv_measure.text = text
            }.show()
        }
        cl_is_using_medicine.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "是否进行药物治疗",
                arrayOf("否", "是")
            ) { _, text ->
                tv_is_using_medicine.text = text
            }.show()
        }
        cl_end_date.setOnClickListener {
            showDatePickerDialog(tv_end_date, supportFragmentManager)
        }
        cl_existence.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                "转归",
                arrayOf("症状消失", "缓解", "持续", "加重", "恢复伴后遗症", "死亡")
            ) { _, text ->
                tv_existence.text = text
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
        if (symptomDescriptionIsClicked) {
            symptomDescription = parseDefaultContent(tv_physical_sign.text.toString())
            symptomDescriptionOther = parseDefaultContent(tv_physical_sign.text.toString())
            if (getMainPhysicalSignList().contains(symptomDescription)) {
                symptomDescriptionOther = null
            } else {
                symptomDescription = "其他"
            }
        }
        val startTime = parseDefaultContent(tv_start_date.text.toString())
        val endTime = parseDefaultContent(tv_end_date.text.toString())
        val existenceText = parseDefaultContent(tv_existence.text.toString())
        val existence = if (existenceText.isEmpty()) null else getExistence().indexOf(existenceText)
        val isUsingMedicine =
            when {
                parseDefaultContent(tv_is_using_medicine.text.toString()) == "是" -> 1
                parseDefaultContent(tv_is_using_medicine.text.toString()).isEmpty() -> null
                else -> 0
            }
        val measureText = parseDefaultContent(tv_measure.text.toString())
        val measure = if (measureText.isEmpty()) null else getMeasure().indexOf(measureText)
        val medicineRelationText = parseDefaultContent(tv_medicine_relation.text.toString())
        val medicineRelation =
            if (medicineRelationText.isEmpty()) null else getMedicineRelation().indexOf(
                medicineRelationText
            )
        val toxicityClassificationText =
            parseDefaultContent(tv_toxicity_classification.text.toString())
        val toxicityClassification =
            if (toxicityClassificationText.isEmpty()) null else getToxicityClassification().indexOf(
                toxicityClassificationText
            )
        val mainPhysicalSignBody =
            MainPhysicalSignBodyBean(
                endTime,
                existence,
                isUsingMedicine,
                mainSymptomId,
                measure,
                medicineRelation,
                startTime,
                symptomDescription,
                symptomDescriptionOther,
                toxicityClassification
            )
        mViewModel.editMainPhysicalSign(sampleId, cycleNumber, mainPhysicalSignBody).observe(this,
            Observer {
                if (it.code == 200) {
                    toast("主要症状体征操作成功")
                    val intent = Intent(this, TreatmentVisitDetailActivity::class.java)
                    intent.action = REFRESH_MAIN_PHYSICAL_SIGN_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else {
                    toast(it.msg)
                }
            })
    }

    private fun loadData(mainPhysicalSignBody: MainPhysicalSignBodyBean) {
        symptomDescription = mainPhysicalSignBody.symptomDescription
        symptomDescriptionOther = mainPhysicalSignBody.symptomDescriptionOther
        if (symptomDescription != null)
            tv_physical_sign.text =
                if (mainPhysicalSignBody.symptomDescription == "其他") mainPhysicalSignBody.symptomDescription
                else mainPhysicalSignBody.symptomDescriptionOther
        if (mainPhysicalSignBody.toxicityClassification != null)
            tv_toxicity_classification.text =
                getToxicityClassification()[mainPhysicalSignBody.toxicityClassification]
        if (mainPhysicalSignBody.startTime != null)
            tv_start_date.text = mainPhysicalSignBody.startTime
        if (mainPhysicalSignBody.medicineRelation != null)
            tv_medicine_relation.text = getMedicineRelation()[mainPhysicalSignBody.medicineRelation]
        if (mainPhysicalSignBody.measure != null)
            tv_measure.text = getMeasure()[mainPhysicalSignBody.measure]
        if (mainPhysicalSignBody.isUsingMedicine != null)
            tv_is_using_medicine.text = if (mainPhysicalSignBody.isUsingMedicine == 0) "否" else "是"
        if (mainPhysicalSignBody.existence != null)
            tv_existence.text = getExistence()[mainPhysicalSignBody.existence]
        if (mainPhysicalSignBody.endTime != null)
            tv_end_date.text = mainPhysicalSignBody.endTime


    }
}
