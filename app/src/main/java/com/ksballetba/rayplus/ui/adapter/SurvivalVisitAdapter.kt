package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SurvivalVisitListBean
import com.ksballetba.rayplus.util.getSurvivalStatus

class SurvivalVisitAdapter(layoutResId:Int,data:List<SurvivalVisitListBean.Data>): BaseQuickAdapter<SurvivalVisitListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "TreatmentRecordAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: SurvivalVisitListBean.Data?) {
        helper.setText(R.id.tv_visit_date,"随访日期:${item?.interviewTime?:""}")
            .setText(R.id.tv_live_status,"生存状态:${getSurvivalStatus()[item!!.survivalStatus]}")
            .setText(R.id.tv_death_date,"死亡日期:${item.dieTime?:""}")
            .setText(R.id.tv_last_contact_date,"最后一次联系日期:${item.lastTimeSurvival?:""}")
            .addOnClickListener(R.id.iv_delete_item_survival_visit)
    }
}