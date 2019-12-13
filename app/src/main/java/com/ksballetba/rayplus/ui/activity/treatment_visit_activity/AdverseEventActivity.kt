package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_adverse_event.*
import kotlinx.android.synthetic.main.activity_treatment_record.*
import java.util.*

class AdverseEventActivity : AppCompatActivity() {

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
        cl_adverse_event_name.setOnClickListener {
            XPopup.Builder(this).asInputConfirm("不良事件名称","请输入不良事件名称"){
                tv_adverse_event_name.text = it
            }.show()
        }
        cl_is_serious.setOnClickListener {
            XPopup.Builder(this).asCenterList("是否为严重不良事件", arrayOf("是","否")){_, text ->
                tv_is_serious.text = text
            }.show()
        }
        cl_toxicity_grading.setOnClickListener {
            XPopup.Builder(this).asCenterList("毒性分级", arrayOf("1级","2级","3级","4级","5级")){_, text ->
                tv_toxicity_grading.text = text
            }.show()
        }
        cl_start_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
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
            XPopup.Builder(this).asCenterList("采取措施", arrayOf("剂量不变","减少剂量","暂停用药","停止用药","实验用药已结束")){_, text ->
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
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_return_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(supportFragmentManager, "请选择日期")
        }
    }
}
