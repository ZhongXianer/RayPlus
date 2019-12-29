package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.MainPhysicalSignListBean

class MainPhysicalSignAdapter(layoutResId:Int,data:List<MainPhysicalSignListBean.Data>): BaseQuickAdapter<MainPhysicalSignListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "MainPhysicalSignAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: MainPhysicalSignListBean.Data?) {
        helper.setText(R.id.tv_physical_sign,"症状体征和描述:${item?.symptomDescription}")
            .setText(R.id.tv_exist_status,"存在状态:${item?.existence}")
            .setText(R.id.tv_start_date,"开始时间:${item?.startTime}")
            .setText(R.id.tv_end_date,"结束时间:${item?.endTime}")
            .addOnClickListener(R.id.iv_delete_item_main_physical_sign)
    }
}