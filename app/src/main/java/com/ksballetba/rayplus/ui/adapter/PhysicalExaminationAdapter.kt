package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseLineData.PhysicalExaminationListBean

class PhysicalExaminationAdapter(layoutResId:Int,data:List<PhysicalExaminationListBean.Data>): BaseQuickAdapter<PhysicalExaminationListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "PhysicalExaminationAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: PhysicalExaminationListBean.Data?) {
        helper.setText(R.id.tv_date,"日期:${item?.time?:""}")
            .setText(R.id.tv_body_temperature,"体温(℃):${item?.temperature?:""}")
            .setText(R.id.tv_breathe,"呼吸(次/分):${item?.breathFrequency?:""}")
            .setText(R.id.tv_blood_pressure,"血压(mmHg):${item?.minpressure?:""}/${item?.maxpressure?:""}")
            .setText(R.id.tv_heart_rate,"心率:${item?.heartRate?:""}")
            .addOnClickListener(R.id.iv_delete_item_physical_examination)
    }
}