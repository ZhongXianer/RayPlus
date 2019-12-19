package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.DemographyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_demographics.*
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 */
class DemographicsFragment : Fragment() {


    companion object{
        const val TAG = "DemographicsFragment"
    }

    private lateinit var mViewModel:BaselineVisitViewModel

    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demographics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        initUI()
    }

    private fun initData(){
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun loadData(){
        mViewModel.getDemography(mSampleId).observe(viewLifecycleOwner, Observer {
            tv_sex.text = if(it.sex == 0) "男" else "女"
            tv_birthday.text = it.date
            tv_race.text = if(it.race == "其他") it.raceOther else it.race
            tv_marital_status.text = if(it.marriage == "其他") it.marriageOther else it.marriage
            if(it.degree!=null){
                tv_degree.text = getDegreeList()[it.degree]
            }
            tv_occupation.text = if(it.vocation == "其他") it.vocationOther else it.vocation
            if(it.zone!=null){
                tv_area.text = getZoneList()[it.zone]
            }
            tv_id_num.text = it.idNum
            tv_admission_number.text = it.hospitalIds
            tv_patient_phone.text = it.phone
            tv_relation_phone.text = it.familyPhone
        })
    }

    private fun saveData(){
        val sex = getSexList().indexOf(tv_sex.text)
        val date = tv_birthday.text.toString()
        var race = tv_race.text.toString()
        var raceOther = tv_race.text.toString()
        if(!getRaceList().contains(race)){
            race = "其他"
        }else{
            raceOther = ""
        }
        var marriage = tv_marital_status.text.toString()
        var marriageOther = tv_marital_status.text.toString()
        if(!getMarriageList().contains(marriage)){
            marriage = "其他"
        }else{
            marriageOther = ""
        }
        val degree = if(getDegreeList().indexOf(tv_degree.text)==-1) null else getDegreeList().indexOf(tv_degree.text)
        var vocation = tv_occupation.text.toString()
        var vocationOther = tv_occupation.text.toString()
        if(!getVocationList().contains(vocation)){
            vocation = "其他"
        }else{
            vocationOther = ""
        }
        val zone = if(getZoneList().indexOf(tv_area.text)==-1) null else getZoneList().indexOf(tv_area.text)
        val idNum = tv_id_num.text.toString()
        val hospitalIds = tv_admission_number.text.toString()
        val phone = tv_patient_phone.text.toString()
        val familyPhone = tv_relation_phone.text.toString()
        val demographyBean = DemographyBean(date,degree,familyPhone,hospitalIds,idNum,marriage,marriageOther,phone,race,raceOther,sex,vocation,vocationOther,zone)
        mViewModel.editDemography(mSampleId,demographyBean).observe(viewLifecycleOwner, Observer {
            if(it.code==200){
                ToastUtils.showShort("人口统计学表单修改成功")
            }else{
                ToastUtils.showShort("人口统计学表单修改失败")
            }
        })
    }

    private fun initUI() {
        fab_save_demographics.setOnClickListener {
            saveData()
        }
        cl_sex.setOnClickListener {
            XPopup.Builder(context).asCenterList("性别", arrayOf("男", "女")) { _, text ->
                tv_sex.text = text
            }.show()
        }
        cl_birthday.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_birthday.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "访视时间")
        }
        cl_race.setOnClickListener {
            XPopup.Builder(context).asCenterList("人种", arrayOf("白人", "黑人","东方人","其他")) { pos, text ->
                if(pos<3){
                    tv_race.text = text
                }else{
                    XPopup.Builder(context).asInputConfirm("人种","请输入人种名称"){
                        tv_race.text = it
                    }.show()
                }
            }.show()
        }
        cl_marital_status.setOnClickListener {
            XPopup.Builder(context).asCenterList("婚姻状况", arrayOf("已婚", "未婚","其他")) { pos, text ->
                if(pos<2){
                    tv_marital_status.text = text
                }else{
                    XPopup.Builder(context).asInputConfirm("婚姻状况","请输入婚姻状况"){
                        tv_marital_status.text = it
                    }.show()
                }
            }.show()
        }
        cl_degree.setOnClickListener {
            XPopup.Builder(context).asCenterList("文化程度", getDegreeList()) { _, text ->
                tv_degree.text = text
            }.show()
        }
        cl_occupation.setOnClickListener {
            XPopup.Builder(context).asCenterList("职业", arrayOf("脑力劳动者", "体力劳动者","学生","离退休","无业或事业","其他，请描述")) { pos, text ->
                if(pos<5){
                    tv_occupation.text = text
                }else{
                    XPopup.Builder(context).asInputConfirm("职业","请输入职业名称"){
                        tv_occupation.text = it
                    }.show()
                }
            }.show()
        }
        cl_area.setOnClickListener {
            XPopup.Builder(context).asCenterList("常住地区", getZoneList()) { _, text ->
                tv_area.text = text
            }.show()
        }
        cl_id_num.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("身份证号","请输入身份证号"){
                tv_id_num.text = it
            }.show()
        }
        cl_admission_number.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("住院号","请输入住院号"){
                tv_admission_number.text = it
            }.show()
        }
        cl_patient_phone.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("患者电话","请输入患者电话"){
                tv_patient_phone.text = it
            }.show()
        }
        cl_relation_phone.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("家属电话","请输入家属电话"){
                tv_relation_phone.text = it
            }.show()
        }
    }
}
