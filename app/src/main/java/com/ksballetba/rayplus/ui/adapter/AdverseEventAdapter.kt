package com.ksballetba.rayplus.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.AdverseEventListBean

class AdverseEventAdapter(layoutResId:Int,data:List<AdverseEventListBean.Data?>): BaseQuickAdapter<AdverseEventListBean.Data?, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "TreatmentRecordAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: AdverseEventListBean.Data?) {
        helper.setText(R.id.tv_adverse_event_name,"不良事件名称:${item?.adverseEventName?:""}")
            .setText(R.id.tv_adverse_event_level,"不良事件等级:${item?.isServerEvent?:""}")
            .setText(R.id.tv_start_date,"开始日期:${item?.startTime?:""}")
            .setText(R.id.tv_drug_relationship,"与药物关系:${item?.medicineRelation?:""}")
            .setText(R.id.tv_take_measure,"采取措施:${item?.measure?:""}")
            .setText(R.id.tv_return,"转归:${item?.recover?:""}")
        helper.getView<ImageView>(R.id.iv_delete_item_adverse_event).visibility = if(item?.needDeleted!!) View.VISIBLE else View.GONE
        if(item.needDeleted!!){
            helper.addOnClickListener(R.id.iv_delete_item_adverse_event)
        }

    }
}