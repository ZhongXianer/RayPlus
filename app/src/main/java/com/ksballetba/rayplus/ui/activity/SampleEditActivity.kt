package com.ksballetba.rayplus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_sample_edit.*
import java.util.*

class SampleEditActivity : AppCompatActivity() {

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

    private fun initUI(){
        setSupportActionBar(tb_sample_edit)
        cl_research_center.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.research_center), arrayOf("同济医院", "襄阳市第一人民医院", "襄阳市中医医院")) { _, text ->
                tv_research_center.text = text
            }.show()
        }
        cl_patient_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(getString(R.string.patient_name),"请输入${getString(R.string.patient_name)}"){
                tv_patient_name.text = it
            }.show()
        }
        cl_patient_num.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(getString(R.string.patient_num),"请输入${getString(R.string.patient_num)}"){
                tv_patient_num.text = it
            }.show()
        }
        cl_patient_id.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(getString(R.string.patient_id),"请输入${getString(R.string.patient_id)}"){
                tv_patient_id.text = it
            }.show()
        }
        cl_patient_group.setOnClickListener {
            XPopup.Builder(this).asInputConfirm(getString(R.string.patient_group),"请输入${getString(R.string.patient_group)}"){
                tv_patient_group.text = it
            }.show()
        }
        cl_patient_sex.setOnClickListener {
            XPopup.Builder(this).asCenterList(getString(R.string.sex), arrayOf("男", "女")) { _, text ->
                tv_patient_sex.text = text
            }.show()
        }
        cl_patient_birthday.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_patient_birthday.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_patient_sign_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_patient_sign_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_patient_enter_group_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_patient_enter_group_date_title.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
    }
}
