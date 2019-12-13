package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_demographics.*
import kotlinx.android.synthetic.main.fragment_visit_time.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DemographicsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demographics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        cl_sex.setOnClickListener {
            XPopup.Builder(context).asCenterList("性别", arrayOf("男", "女")) { _, text ->
                tv_sex.text = text
            }.show()
        }
        cl_birthday.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
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
            XPopup.Builder(context).asCenterList("文化程度", arrayOf("文盲", "小学", "初中", "中专或高中", "大专或本科", "本科以上")) { _, text ->
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
            XPopup.Builder(context).asCenterList("常住地区", arrayOf("城市", "农村")) { _, text ->
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
