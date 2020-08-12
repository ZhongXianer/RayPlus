package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.DemographyBean
import com.ksballetba.rayplus.data.bean.EditDemographyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_demographics.*

/**
 * A simple [Fragment] subclass.
 */
class DemographicsFragment : Fragment() {


    companion object {
        const val TAG = "DemographicsFragment"
    }

    private lateinit var mViewModel: BaselineVisitViewModel

    var mSampleId = 0

    private var raceIsSet = false
    private var marriageIsSet = false
    private var vocationIsSet = false

    private lateinit var mDemographyBean: DemographyBean

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

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun loadData() {
        mViewModel.getDemography(mSampleId).observe(viewLifecycleOwner, Observer {
            mDemographyBean = it
            tv_sex.text = if (it.data.sex == 0) "男" else "女"
            tv_birthday.text = it.data.date
            if (it.data.race != null)
                tv_race.text = if (it.data.race == "其他") it.data.raceOther else it.data.race
            if (it.data.marriage != null)
                tv_marital_status.text =
                    if (it.data.marriage == "其他") it.data.marriageOther else it.data.marriage
            if (it.data.degree != null) {
                tv_degree.text = getDegreeList()[it.data.degree]
            }
            if (it.data.vocation != null)
                tv_occupation.text =
                    if (it.data.vocation == "其他") it.data.vocationOther else it.data.vocation
            if (it.data.zone != null) {
                tv_area.text = getZoneList()[it.data.zone]
            }
            tv_id_num.text = it.data.idNum
            if (it.data.hospitalIds != null)
                tv_admission_number.text = it.data.hospitalIds
            if (it.data.phone != null)
                tv_patient_phone.text = it.data.phone
            if (it.data.familyPhone != null)
                tv_relation_phone.text = it.data.familyPhone
        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun saveData() {
        val sex = getSexList().indexOf(tv_sex.text)
        val date = tv_birthday.text.toString()
        var race = mDemographyBean.data.race
        var raceOther = mDemographyBean.data.raceOther
        if (raceIsSet) {
            race = tv_race.text.toString()
            raceOther = tv_race.text.toString()
            if (!getRaceList().contains(race)) {
                race = "其他"
            } else {
                raceOther = ""
            }
        }
        var marriage = mDemographyBean.data.marriage
        var marriageOther = mDemographyBean.data.marriageOther
        if (marriageIsSet) {
            marriage = tv_marital_status.text.toString()
            marriageOther = tv_marital_status.text.toString()
            if (!getMarriageList().contains(marriage)) {
                marriage = "其他"
            } else {
                marriageOther = ""
            }
        }
        val degree =
            if (getDegreeList().indexOf(tv_degree.text) == -1) null else getDegreeList().indexOf(
                tv_degree.text
            )
        var vocation = mDemographyBean.data.vocation
        var vocationOther = mDemographyBean.data.vocationOther
        if (vocationIsSet) {
            vocation = tv_occupation.text.toString()
            vocationOther = tv_occupation.text.toString()
            if (!getVocationList().contains(vocation)) {
                vocation = "其他"
            } else {
                vocationOther = ""
            }
        }
        val zone =
            if (getZoneList().indexOf(tv_area.text) == -1) null else getZoneList().indexOf(tv_area.text)
        val idNum = tv_id_num.text.toString()
        val hospitalIds = tv_admission_number.text.toString()
        val phone = tv_patient_phone.text.toString()
        val familyPhone = tv_relation_phone.text.toString()
        val editDemographyBean = EditDemographyBean(
            date,
            degree,
            familyPhone,
            hospitalIds,
            idNum,
            marriage,
            marriageOther,
            phone,
            race,
            raceOther,
            sex,
            vocation,
            vocationOther,
            zone
        )
        mViewModel.editDemography(mSampleId, editDemographyBean)
            .observe(viewLifecycleOwner, Observer {
                if (it.code == 200) {
                    ToastUtils.showShort("人口统计学表单修改成功")
                } else {
                    ToastUtils.showShort(it.msg)
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
        cl_race.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("人种", arrayOf("白人", "黑人", "东方人", "其他")) { pos, text ->
                    raceIsSet = true
                    if (pos < 3) {
                        tv_race.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("人种", "请输入人种名称") {
                            tv_race.text = it
                        }.show()
                    }
                }.show()
        }
        cl_marital_status.setOnClickListener {
            XPopup.Builder(context).asCenterList("婚姻状况", arrayOf("已婚", "未婚", "其他")) { pos, text ->
                marriageIsSet = true
                if (pos < 2) {
                    tv_marital_status.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("婚姻状况", "请输入婚姻状况") {
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
            XPopup.Builder(context).asCenterList(
                "职业",
                arrayOf("脑力劳动者", "体力劳动者", "学生", "离退休", "无业或失业", "其他，请描述")
            ) { pos, text ->
                vocationIsSet = true
                if (pos < 5) {
                    tv_occupation.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("职业", "请输入职业名称") {
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
            XPopup.Builder(context).asInputConfirm("身份证号", "请输入身份证号") {
                tv_id_num.text = it
            }.show()
        }
        cl_admission_number.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("住院号", "请输入住院号") {
                tv_admission_number.text = it
            }.show()
        }
        cl_patient_phone.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("患者电话", "请输入患者电话") {
                tv_patient_phone.text = it
            }.show()
        }
        cl_relation_phone.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("家属电话", "请输入家属电话") {
                tv_relation_phone.text = it
            }.show()
        }
    }
}
