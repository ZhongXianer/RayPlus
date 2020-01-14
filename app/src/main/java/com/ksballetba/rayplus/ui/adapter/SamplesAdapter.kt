package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SampleListBean

class SamplesAdapter(layoutResId:Int,data:List<SampleListBean.Data>): BaseQuickAdapter<SampleListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: SampleListBean.Data?) {
        helper.setText(R.id.tv_sample_research_center_ids,item?.researchCenterIds)
            .setText(R.id.tv_sample_patient_ids,item?.patientIds)
            .setText(R.id.tv_sample_patient_name,"姓名：${item?.patientName}")
            .setText(R.id.tv_sample_sex,"性别：${item?.sex}")
            .setText(R.id.tv_sample_group_name,"组别：${item?.groupName}")
            .setText(R.id.tv_sample_id_num,"身份证号：${item?.idNum}")
            .setText(R.id.tv_sample_age,"年龄：${item?.age}")
            .setText(R.id.tv_sample_interview_status,"随访进度：${item?.interviewStatus}")
            .setText(R.id.tv_sample_last_interview_time,"上一次随访时间：${item?.lastInterviewTime}")
            .setText(R.id.tv_sample_next_interview_time,"预计下一次随访时间：${item?.nextInterviewTime}")
            .addOnClickListener(R.id.btn_sample_edit)
            .addOnClickListener(R.id.btn_sample_submit)
            .addOnClickListener(R.id.iv_delete_item_sample)
    }
}