package com.ksballetba.rayplus.ui.fragment.project_summary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.projectSummaryData.ProjectSummaryBodyBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.ProjectSummaryViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_project_summary_detail.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectSummaryDetailFragment : Fragment() {

    companion object {
        const val TAG = "ProjectSummaryDetailFragment"
    }

    private lateinit var mViewModel: ProjectSummaryViewModel

    var mSampleId = 0

    private var reasonStopDrugIsSet = false
    private var mReasonStopDrug: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_summary_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        initUI()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getProjectSummaryViewModel(this)
    }

    private fun loadData() {
        mViewModel.getProjectSummary(mSampleId).observe(viewLifecycleOwner, Observer {
            mReasonStopDrug = it.data?.reasonStopDrug
            if (it.data?.isStop == null) tv_is_stop_treat.text = "请设置"
            else {
                if (it.data.isStop == 1) tv_is_stop_treat.text = "是"
                else tv_is_stop_treat.text = "否"
            }
            tv_clinic_terminal.text = it.data?.relay ?: "请设置"
            tv_last_take_medicine_date.text = it.data?.lastTimeDrug ?: "请设置"
            if (it.data?.treatmentTimes == null) tv_take_medicine_num.text = "请设置"
            else tv_take_medicine_num.text = it.data.treatmentTimes.toString()
            if (it.data?.reasonStopDrug == null) tv_stop_treat_cause.text = "请设置"
            else tv_stop_treat_cause.text =
                if (it.data.reasonStopDrug < 7) getReasonStopDrug()[it.data.reasonStopDrug] else it.data.otherReasons
            tv_curative_effect_summary_pfs.text = it.data?.pFS ?: "请设置"
            tv_curative_effect_summary_os.text = it.data?.oS ?: "请设置"
            if (it.data?.bestEffect == null) tv_best_treat.text = "请设置"
            else tv_best_treat.text = getBestEffect()[it.data.bestEffect]
        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun saveData() {
        val isStopStr = parseDefaultContent(tv_is_stop_treat.text.toString())
        var isStop: Int?
        if (isStopStr == "") isStop = null
        else isStop = if (isStopStr == "是") 1 else 0
        val relay = parseDefaultContent(tv_clinic_terminal.text.toString())
        val lastTimeDrug = parseDefaultContent(tv_last_take_medicine_date.text.toString())
        val treatmentTimes = parseDefaultContent(tv_take_medicine_num.text.toString()).toIntOrNull()
        val reasonStopDrugStr = parseDefaultContent(tv_stop_treat_cause.text.toString())
        var otherReasons = parseDefaultContent(tv_stop_treat_cause.text.toString())
        val reasonStopDrug: Int?
        if (reasonStopDrugIsSet) {
            if (getReasonStopDrug().contains(reasonStopDrugStr)) {
                reasonStopDrug = getReasonStopDrug().indexOf(reasonStopDrugStr)
                otherReasons = ""
            } else {
                reasonStopDrug = 7
            }
        } else reasonStopDrug = mReasonStopDrug
        val pFS = parseDefaultContent(tv_curative_effect_summary_pfs.text.toString())
        val oS = parseDefaultContent(tv_curative_effect_summary_os.text.toString())
        val bestEffectStr = parseDefaultContent(tv_best_treat.text.toString())
        val bestEffect =
            if (bestEffectStr.isEmpty()) null else getBestEffect().indexOf(bestEffectStr)
        val projectSummaryBody =
            ProjectSummaryBodyBean(
                bestEffect,
                isStop,
                lastTimeDrug,
                oS,
                otherReasons,
                pFS,
                reasonStopDrug,
                relay,
                treatmentTimes
            )
        mViewModel.editProjectSummary(mSampleId, projectSummaryBody).observe(viewLifecycleOwner,
            Observer {
                if (it.code == 200) {
                    ToastUtils.showShort("项目总结修改成功")
                } else {
                    ToastUtils.showShort(it.msg)
                }
            })
    }

    private fun initUI() {
        fab_save_project_summary.setOnClickListener {
            saveData()
        }
        cl_is_stop_treat.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                getString(R.string.is_stop_treat),
                arrayOf("是", "否")
            ) { _, text ->
                tv_is_stop_treat.text = text
            }.show()
        }
        cl_clinic_terminal.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.clinic_terminal), "请输入内容") {
                tv_clinic_terminal.text = it
            }.show()
        }
        cl_last_take_medicine_date.setOnClickListener {
            showDatePickerDialog(tv_last_take_medicine_date, parentFragmentManager)
        }
        cl_take_medicine_num.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.take_medicine_num), "请输入内容") {
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
                    reasonStopDrugIsSet = true
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
        cl_curative_effect_summary_pfs.setOnClickListener {
            XPopup.Builder(context)
                .asInputConfirm(
                    getString(R.string.curative_effect_summary_pfs),
                    "请输入${getString(R.string.curative_effect_summary_pfs)}"
                ) {
                    tv_curative_effect_summary_pfs.text = it
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
            ) { _, text ->
                tv_best_treat.text = text
            }.show()
        }
    }
}
