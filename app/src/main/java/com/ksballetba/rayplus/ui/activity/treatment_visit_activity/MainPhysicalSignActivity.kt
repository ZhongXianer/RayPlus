package com.ksballetba.rayplus.ui.activity.treatment_visit_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_physical_sign.*
import java.util.*

class MainPhysicalSignActivity : AppCompatActivity() {

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

    private fun initUI(){
        setSupportActionBar(tb_main_physical_sign)
        cl_physical_sign.setOnClickListener {
            XPopup.Builder(this).asCenterList("症状体征和描述", arrayOf("高血压", "腹泻", "皮疹","蛋白尿","出血","其他")) { pos, text ->
                if(pos<5){
                    tv_physical_sign.text = text
                }else{
                    XPopup.Builder(this).asInputConfirm("其他","请输入其他症状体征和描述"){
                        tv_physical_sign.text = it
                    }.show()
                }
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
        cl_exist_status.setOnClickListener {
            XPopup.Builder(this).asCenterList("存在状态", arrayOf("存在","消失")) { pos, text ->
               tv_exist_status.text = text
            }.show()
        }
    }
}
