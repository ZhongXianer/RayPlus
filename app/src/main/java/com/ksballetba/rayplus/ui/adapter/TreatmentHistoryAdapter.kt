package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseLineData.TreatmentHistoryListBean

class TreatmentHistoryAdapter(layoutResId:Int, data:List<TreatmentHistoryListBean.Data>): BaseQuickAdapter<TreatmentHistoryListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "TreatmentRecordAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: TreatmentHistoryListBean.Data?) {
        helper.setText(R.id.tv_treatment_line,"几线治疗:${item?.diagnoseNumber?:""}")
            .setText(R.id.tv_start_date,"开始时间:${item?.startTime?:""}")
            .addOnClickListener(R.id.iv_delete_item_treatment_history)
    }
}