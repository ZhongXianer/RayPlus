package com.ksballetba.rayplus.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.sampleData.SampleListBean
import com.ksballetba.rayplus.util.*
import org.angmarch.views.NiceSpinner
import org.jetbrains.anko.collections.forEachReversedByIndex

class SamplesAdapter(layoutResId: Int, data: List<SampleListBean.Data>) :
    BaseQuickAdapter<SampleListBean.Data, BaseViewHolder>(layoutResId, data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: SampleListBean.Data?) {
        helper.setText(R.id.tv_sample_research_center_ids, item?.researchCenterIds)
            .setText(R.id.tv_sample_patient_ids, item?.patientIds)
            .setText(R.id.tv_sample_patient_name, "姓名：${item?.patientName}")
            .setText(R.id.tv_sample_sex, "性别：${item?.sex}")
            .setText(R.id.tv_sample_group_name, "组别：${item?.groupName}")
            .setText(R.id.tv_sample_id_num, "身份证号：${item?.idNum}")
            .setText(R.id.tv_sample_age, "年龄：${item?.age}")
            .setText(R.id.tv_sample_interview_status, "随访进度：${item?.interviewStatus}")
            .setText(R.id.tv_sample_last_interview_time, "上一次随访时间：${item?.lastInterviewTime}")
            .setText(R.id.tv_sample_next_interview_time, "预计下一次随访时间：${item?.nextInterviewTime}")
            .setText(
                R.id.tv_sample_submit_status,
                "状态：${getSampleSubmitStatus()[item!!.submitStatus]}"
            )
            .addOnClickListener(R.id.btn_sample_edit)
            .addOnClickListener(R.id.btn_sample_submit)
            .addOnClickListener(R.id.iv_delete_item_sample)
            .addOnClickListener(R.id.btn_sample_unlock)
        var treatment = "未提交"
        if (item.status.cycleStatus.size == 1)
            treatment = "未提交"
        else {
            run breaking@{
                item.status.cycleStatus.forEachReversedByIndex {
                    if (it.isSubmit == 1) {
                        treatment = "访视${it.cycleNumber}已提交"
                        return@breaking
                    }
                }
            }
        }
        val survivalStatus =
            if (item.status.interviewStatus.isEmpty()) "未提交" else "${item.status.interviewStatus.size}条"
        val submitStatusDetails =
            mutableListOf(
                "",
                "基线资料：${getSurvivalSubmitStatus()[item.status.cycleStatus[0].isSubmit]}",
                "治疗期访视：${treatment}",
                "生存期访视：${survivalStatus}"
            )
        helper.getView<NiceSpinner>(R.id.submit_status_details)
            .attachDataSource(submitStatusDetails)
        if (judgeUnlockSample() && (item.submitStatus == 1 || item.submitStatus == 2)) {
            helper.setBackgroundColor(R.id.btn_sample_unlock, Color.parseColor("#03A9F4"))
        } else {
            helper.setBackgroundColor(R.id.btn_sample_unlock, Color.parseColor("#94D3E6"))
        }

        if (item.submitStatus == 2) {
            helper.setBackgroundColor(R.id.btn_sample_submit, Color.parseColor("#94D3E6"))
            helper.setBackgroundColor(R.id.btn_sample_edit, Color.parseColor("#94D3E6"))
        } else {
            helper.setBackgroundColor(R.id.btn_sample_submit, Color.parseColor("#03A9F4"))
            helper.setBackgroundColor(R.id.btn_sample_edit, Color.parseColor("#03A9F4"))
        }

        if (item.researchCenterId != getResearchCenterId()) {
            helper.setBackgroundColor(R.id.btn_sample_submit, Color.parseColor("#94D3E6"))
            helper.setBackgroundColor(R.id.btn_sample_edit, Color.parseColor("#94D3E6"))
        } else {
            helper.setBackgroundColor(R.id.btn_sample_submit, Color.parseColor("#03A9F4"))
            helper.setBackgroundColor(R.id.btn_sample_edit, Color.parseColor("#03A9F4"))
        }
    }

}