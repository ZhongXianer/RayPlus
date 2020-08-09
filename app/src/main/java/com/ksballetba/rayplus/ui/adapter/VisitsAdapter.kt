package com.ksballetba.rayplus.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitShowDataBean
import com.ksballetba.rayplus.util.getTreatmentVisitSubmitStatus

class VisitsAdapter(layoutResId: Int, data: List<TreatmentVisitShowDataBean>) :
    BaseQuickAdapter<TreatmentVisitShowDataBean, BaseViewHolder>(layoutResId, data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: TreatmentVisitShowDataBean) {
        helper.setText(R.id.tv_visit_name, item.data.title)
            .setText(R.id.tv_visit_list_date, "访视时间：${item.visitTime ?: "未设置"}")
            .setText(
                R.id.tv_visit_submit_status,
                "提交状态：${getTreatmentVisitSubmitStatus()[item.submitStatus]}"
            )
            .addOnClickListener(R.id.tv_visit_submit_button)
        if (item.submitStatus == 0)
            helper.setBackgroundColor(R.id.tv_visit_submit_button, Color.parseColor("#03A9F4"))
        else helper.setBackgroundColor(R.id.tv_visit_submit_button, Color.parseColor("#BDBDBD"))
    }
}