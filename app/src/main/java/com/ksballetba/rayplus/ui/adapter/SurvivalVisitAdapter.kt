package com.ksballetba.rayplus.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitListBean
import com.ksballetba.rayplus.util.getSurvivalStatus
import com.ksballetba.rayplus.util.getSurvivalSubmitStatus

class SurvivalVisitAdapter(layoutResId:Int,data:List<SurvivalVisitListBean.Data>): BaseQuickAdapter<SurvivalVisitListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "TreatmentRecordAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: SurvivalVisitListBean.Data?) {
        helper.setText(R.id.tv_visit_date,"随访日期:${item?.interviewTime?:""}")
            .setText(R.id.tv_live_status,"生存状态:${getSurvivalStatus()[item!!.survivalStatus]}")
            .setText(R.id.tv_last_contact_date,"最后一次联系日期:${item.lastTimeSurvival?:""}")
            .setText(R.id.submit_status,"提交状态：${getSurvivalSubmitStatus()[item.isSubmit]}")
            .addOnClickListener(R.id.iv_delete_item_survival_visit)
            .addOnClickListener(R.id.submit_button)
        if(item.survivalStatus==0){
            helper.getView<TextView>(R.id.tv_death_date).visibility = View.VISIBLE
            helper.setText(R.id.tv_death_date,"死亡日期:${item.dieTime?:""}")
        }else{
            helper.getView<TextView>(R.id.tv_death_date).visibility = View.GONE
        }
    }
}