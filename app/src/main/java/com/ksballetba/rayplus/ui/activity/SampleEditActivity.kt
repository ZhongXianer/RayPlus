package com.ksballetba.rayplus.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.google.gson.Gson
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SampleEditBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_BODY
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.SamplesViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_sample_edit.*
import org.jetbrains.anko.toast

class SampleEditActivity : AppCompatActivity() {

    companion object{
        const val TAG = "SampleEditActivity"
        const val REFRESH_LAST_PAGE = "REFRESH_LAST_PAGE"
    }

    lateinit var mViewModel:SamplesViewModel
    var mToken:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_edit)
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
        setSupportActionBar(tb_sample_edit)
        mViewModel = getSamplesViewModel(this)
        val sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        mToken = sharedPreferences.getString(LoginActivity.LOGIN_TOKEN,"")
        val sampleBody = intent.getParcelableExtra<SampleEditBodyBean>(SAMPLE_BODY)
        if(sampleBody!=null){
            loadData(sampleBody)
        }
        fab_save_sample.setOnClickListener {
            val sampleId = intent.getIntExtra(SAMPLE_ID,-1)
            if(sampleId==-1){
                addOrEditSample(null)
            }else{
                addOrEditSample(sampleId)
            }
        }
        cl_research_center.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.research_center),
                getResearchCenterList()
            ) { _, text ->
                tv_research_center.text = text
            }.show()
        }
        cl_patient_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(
                getString(R.string.patient_name),
                "请输入${getString(R.string.patient_name)}"
            ) {
                tv_patient_name.text = it
            }.show()
        }
        cl_patient_num.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(
                getString(R.string.patient_num),
                "请输入${getString(R.string.patient_num)}"
            ) {
                tv_patient_num.text = it
            }.show()
        }
        cl_patient_id.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(
                getString(R.string.patient_id),
                "请输入${getString(R.string.patient_id)}"
            ) {
                tv_patient_id.text = it
            }.show()
        }
        cl_patient_group.setOnClickListener {
            XPopup.Builder(this).asCenterList(
                getString(R.string.patient_group),
                getPatientGroupList()
            ) { pos, text ->
                tv_patient_group.text = text
            }.show()
        }
        cl_patient_sex.setOnClickListener {
            XPopup.Builder(this)
                .asCenterList(getString(R.string.sex), getSexList()) { _, text ->
                    tv_patient_sex.text = text
                }.show()
        }
        cl_patient_birthday.setOnClickListener {
            showDatePickerDialog(tv_patient_birthday,supportFragmentManager)
        }
        cl_patient_sign_date.setOnClickListener {
            showDatePickerDialog(tv_patient_sign_date,supportFragmentManager)
        }
        cl_patient_enter_group_date.setOnClickListener {
            showDatePickerDialog(tv_patient_enter_group_date,supportFragmentManager)
        }

    }

    private fun loadData(sampleBody: SampleEditBodyBean){
        tv_research_center.text = getResearchCenterList()[sampleBody.researchCenterId-1]
        tv_patient_name.text = sampleBody.patientName
        tv_patient_num.text = sampleBody.patientIds
        tv_patient_id.text = sampleBody.idNum
        tv_patient_group.text = getPatientGroupList()[sampleBody.groupId-1]
        tv_patient_sex.text = getSexList()[sampleBody.sex]
        tv_patient_birthday.text = sampleBody.date
        tv_patient_sign_date.text = sampleBody.signTime
        tv_patient_enter_group_date.text = sampleBody.inGroupTime
    }

    private fun addOrEditSample(sampleId: Int?){
        val researchCenterId = getResearchCenterList().indexOf(parseDefaultContent(tv_research_center.text.toString()))+1
        val patientName = parseDefaultContent(tv_patient_name.text.toString())
        val patientIds = parseDefaultContent(tv_patient_num.text.toString())
        val idNum = parseDefaultContent(tv_patient_id.text.toString())
        val groupId =
            getPatientGroupList().indexOf(parseDefaultContent(tv_patient_group.text.toString()))+1
        val sex = getSexList().indexOf(parseDefaultContent(tv_patient_sex.text.toString()))
        val date = parseDefaultContent(tv_patient_birthday.text.toString())
        val signTime = parseDefaultContent(tv_patient_sign_date.text.toString())
        val inGroupTime = parseDefaultContent(tv_patient_enter_group_date.text.toString())
        val sampleEditBodyBean = SampleEditBodyBean(
            date,
            groupId,
            idNum,
            inGroupTime,
            patientIds,
            patientName,
            researchCenterId,
            sampleId,
            sex,
            signTime
        )
        if (checkSampleValid(sampleEditBodyBean)) {
            mViewModel.editSample(sampleEditBodyBean).observe(this, Observer {
                if(it.code==200){
                    toast("样本操作成功")
                    val intent = Intent(this,SampleActivity::class.java)
                    intent.action = REFRESH_LAST_PAGE
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    toast("样本操作失败")
                }
            })
        }else{
            toast("请检查，身份证号是否正确或必填项是否为空")
        }
    }


    private fun checkSampleValid(sampleEditBodyBean: SampleEditBodyBean): Boolean {
        var idNum = ""
        if(sampleEditBodyBean.idNum!=null){
            idNum = sampleEditBodyBean.idNum
        }
        if(sampleEditBodyBean.sampleId==null){
            return !(sampleEditBodyBean.researchCenterId < 0 ||
                    sampleEditBodyBean.patientName.isNullOrEmpty() ||
                    sampleEditBodyBean.patientIds.isNullOrEmpty() ||
                    idNum.length!=18 ||
                    sampleEditBodyBean.groupId < 0 ||
                    sampleEditBodyBean.sex < 0 ||
                    sampleEditBodyBean.date.isNullOrEmpty() ||
                    sampleEditBodyBean.signTime.isNullOrEmpty() ||
                    sampleEditBodyBean.inGroupTime.isNullOrEmpty())
        }else{
            return !(sampleEditBodyBean.sampleId<0||
                    sampleEditBodyBean.researchCenterId < 0 ||
                    sampleEditBodyBean.patientName.isNullOrEmpty() ||
                    sampleEditBodyBean.patientIds.isNullOrEmpty() ||
                    idNum.length!=18 ||
                    sampleEditBodyBean.groupId < 0 ||
                    sampleEditBodyBean.sex < 0 ||
                    sampleEditBodyBean.date.isNullOrEmpty() ||
                    sampleEditBodyBean.signTime.isNullOrEmpty() ||
                    sampleEditBodyBean.inGroupTime.isNullOrEmpty())
        }

    }
}
