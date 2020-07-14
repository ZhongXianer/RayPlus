package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationListBean

class ImagingEvaluationAdapter(layoutResId: Int, data: List<ImagingEvaluationListBean.Data>) :
    BaseQuickAdapter<ImagingEvaluationListBean.Data, BaseViewHolder>(layoutResId, data) {

    companion object {
        const val TAG = "PhysicalExaminationAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: ImagingEvaluationListBean.Data?) {
        helper.setText(R.id.tv_part, "部位:${item?.part ?: ""}")
            .setText(R.id.tv_way, "方法:${item?.method ?: ""}")
            .setText(R.id.tv_tumor_long_dia, "肿瘤长径(cm):${item?.tumorLong ?: ""}")
            .setText(R.id.tv_tumor_short_dia, "肿瘤短径(cm):${item?.tumorShort ?: ""}")
            .setText(R.id.tv_date, "时间:${item?.time ?: ""}")
            .setText(R.id.tumor_describe, "肿瘤描述:${item?.tumorDesc ?: ""}")
            .addOnClickListener(R.id.iv_delete_item_imaging_evaluation)
            .addOnClickListener(R.id.file_btn)
    }
}