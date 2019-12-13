package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import org.angmarch.views.NiceSpinner

class LabInspectionAdapter(layoutResId:Int,data:List<String>): BaseQuickAdapter<String, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "LabInspectionAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_lab_inspection_title,item)
        val spinnerData = mutableListOf("无","1","2","3","4")
        helper.getView<NiceSpinner>(R.id.ns_lab_inspection).attachDataSource(spinnerData)
    }
}