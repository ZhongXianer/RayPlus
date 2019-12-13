package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_physical_examination.*
import java.util.*

class PhysicalExaminationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physical_examination)
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
        setSupportActionBar(tb_physical_examination)
        cl_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
        cl_body_temperature.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("体温（℃）","请输入体温"){
                tv_body_temperature.text = it
            }.show()
        }
        cl_breathe.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("呼吸（次/分）","请输入呼吸"){
                tv_breathe.text = it
            }.show()
        }
        cl_blood_systolic_pressure.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("血压（mmHg ）","请输入收缩压"){
                tv_blood_systolic_pressure.text = it
            }.show()
        }
        cl_blood_diastolic_pressure.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("血压（mmHg ）","请输入舒张压"){
                tv_blood_diastolic_pressure.text = it
            }.show()
        }
        cl_heart_rate.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("心率（次/分）","请输入心率"){
                tv_heart_rate.text = it
            }.show()
        }
        fab_save_physical_examination.setOnClickListener {
            save()
        }
    }

    private fun save(){

    }
}
