package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.NavigationBean
import com.ksballetba.rayplus.util.getTreatmentVisitSubmitStatus

class VisitsAdapter(layoutResId:Int,data:List<NavigationBean.Data>): BaseQuickAdapter<NavigationBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: NavigationBean.Data?) {
        helper.setText(R.id.tv_visit_name,item?.title)
            .setText(R.id.tv_visit_submit_status,"提交状态：${getTreatmentVisitSubmitStatus()[item!!.isSubmit]}")
            .addOnClickListener(R.id.tv_visit_submit)
    }
}