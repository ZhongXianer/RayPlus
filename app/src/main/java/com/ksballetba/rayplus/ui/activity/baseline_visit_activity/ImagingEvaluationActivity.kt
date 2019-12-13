package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_imaging_evaluation.*
import java.util.*

class ImagingEvaluationActivity : AppCompatActivity() {

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

    private fun initUI(){
        setSupportActionBar(tb_imaging_evaluation)
        cl_part.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("部位","请输入部位"){
                tv_part.text = it
            }.show()
        }
        cl_way.setOnClickListener {
            XPopup.Builder(this).asCenterList("方法", arrayOf("CT", "MRI", "超声","X线平片","PET-CT","其他")) { pos, text ->
                if(pos<5){
                    tv_way.text = text
                }else{
                    XPopup.Builder(this).asInputConfirm("其他","请输入其他方法"){
                        tv_way.text = it
                    }.show()
                }
            }.show()
        }
        cl_tumor_long_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤长径","请输入肿瘤长径（cm）"){
                tv_tumor_long_dia.text = it
            }.show()
        }
        cl_tumor_short_dia.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("肿瘤短径","请输入肿瘤短径（cm）"){
                tv_tumor_short_dia.text = it
            }.show()
        }
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
            dpd.show(supportFragmentManager, "请选择时间")
        }
    }
}
