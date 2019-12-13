package com.ksballetba.rayplus.ui.fragment.project_summary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_survival_visit.*
import kotlinx.android.synthetic.main.fragment_project_summary_detail.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectSummaryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_summary_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        cl_is_stop_treat.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                getString(R.string.is_stop_treat),
                arrayOf("是", "否")
            ) { position, text ->
                tv_is_stop_treat.text = text
            }.show()
        }
        cl_clinic_terminal.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.clinic_terminal), "请输入内容") {
                tv_clinic_terminal.text = it
            }.show()
        }
        cl_last_take_medicine_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_last_take_medicine_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "请输入日期")
        }
        cl_take_medicine_num.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.clinic_terminal), "请输入内容") {
                tv_take_medicine_num.text = it
            }.show()
        }
        cl_stop_treat_cause.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList(
                    getString(R.string.stop_treat_cause),
                    arrayOf(
                        "病情进展（出现客观疗效评价的疾病进展或临床症状进展）",
                        "不良事件（与试验药物可能存在相关性）",
                        "不良事件（与试验药物不存在相关性）",
                        "自愿退出（与不良事件无相关性）",
                        "违背实验方案",
                        "死亡",
                        "失联",
                        "其他"
                    )
                ) { pos, text ->
                    if (pos < 7) {
                        tv_stop_treat_cause.text = text
                    } else {
                        XPopup.Builder(context)
                            .asInputConfirm(getString(R.string.stop_treat_cause), "请输入其他原因") {
                                tv_stop_treat_cause.text = it
                            }.show()
                    }
                }.show()
        }
        cl_curative_effect_summary_fps.setOnClickListener {
            XPopup.Builder(context)
                .asInputConfirm(
                    getString(R.string.curative_effect_summary_fps),
                    "请输入${getString(R.string.curative_effect_summary_fps)}"
                ) {
                    tv_curative_effect_summary_fps.text = it
                }.show()
        }
        cl_curative_effect_summary_os.setOnClickListener {
            XPopup.Builder(context)
                .asInputConfirm(
                    getString(R.string.curative_effect_summary_os),
                    "请输入${getString(R.string.curative_effect_summary_os)}"
                ) {
                    tv_curative_effect_summary_os.text = it
                }.show()
        }
        cl_best_treat.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                getString(R.string.best_treat),
                arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(SD)", "不能评价(NE)")
            ){position, text ->
                tv_best_treat.text = text
            }.show()
        }
    }
}
