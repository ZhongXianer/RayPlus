package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.NavigationBean

class VisitsAdapter(layoutResId:Int,data:List<NavigationBean.Children>): BaseQuickAdapter<NavigationBean.Children, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: NavigationBean.Children?) {
        helper.setText(R.id.tv_visit_name,item?.title)
    }
}