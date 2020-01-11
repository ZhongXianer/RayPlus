package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.TreatmentRecordListBean

class TreatmentRecordAdapter(layoutResId:Int,data:List<TreatmentRecordListBean.Data>): BaseQuickAdapter<TreatmentRecordListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "TreatmentRecordAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: TreatmentRecordListBean.Data?) {
        helper.setText(R.id.tv_treatment_name,"治疗名称:${item?.treatmentName?:""}")
            .setText(R.id.tv_drug_name,"药物名称:${item?.medicineName?:""}")
            .setText(R.id.tv_treatment_start_date,"给药/治疗开始日期:${item?.startTime?:""}")
            .setText(R.id.tv_treatment_end_date,"给药/治疗结束日期:${item?.endTime?:""}")
            .setText(R.id.tv_dose_usage,"剂量及用法:${item?.description?:""}")
            .addOnClickListener(R.id.iv_delete_item_treatment_record)
    }
}