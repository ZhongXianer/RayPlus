package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.treatmentVisitData.MainPhysicalSignListBean
import com.ksballetba.rayplus.util.getExistence
import com.ksballetba.rayplus.util.getExistenceStatus

class MainPhysicalSignAdapter(layoutResId: Int, data: List<MainPhysicalSignListBean.Data>) :
    BaseQuickAdapter<MainPhysicalSignListBean.Data, BaseViewHolder>(layoutResId, data) {

    companion object {
        const val TAG = "MainPhysicalSignAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: MainPhysicalSignListBean.Data?) {
        helper.setText(
            R.id.tv_physical_sign,
            "症状体征和描述:${item?.symptomDescription ?: ""}"
        )
            .setText(R.id.tv_start_date, "开始时间:${item?.startTime ?: ""}")
            .setText(R.id.tv_end_date, "结束时间:${item?.endTime ?: ""}")
            .addOnClickListener(R.id.iv_delete_item_main_physical_sign)
        if (item?.symptomDescription == "其他")
            helper.setText(
                R.id.tv_physical_sign,
                "症状体征和描述:${item.symptomDescription}-${item.symptomDescriptionOther}"
            )
        if (item?.existence == null)
            helper.setText(R.id.tv_existence_list, "转归:")
        else helper.setText(R.id.tv_existence_list, "转归:${getExistence()[item.existence]}")
    }
}