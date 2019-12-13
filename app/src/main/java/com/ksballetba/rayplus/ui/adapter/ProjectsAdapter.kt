package com.ksballetba.rayplus.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.ProjectListBean

class ProjectsAdapter(layoutResId:Int,data:List<ProjectListBean.Data>): BaseQuickAdapter<ProjectListBean.Data, BaseViewHolder>(layoutResId,data) {

    companion object {
        const val TAG = "ProjectsAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: ProjectListBean.Data?) {
        helper.setText(R.id.tv_project_ids,item?.projectIds)
            .setText(R.id.tv_project_description,item?.description)
            .setText(R.id.tv_project_department,"负责单位：${item?.researchCenterIds}")
            .setText(R.id.tv_project_principal,"负责人：${item?.principal}")
            .setText(R.id.tv_project_phone,"电话：${item?.phone}")
            .setText(R.id.tv_project_current_progress,"当前进度：${item?.now}")
            .setText(R.id.tv_project_capacity,"项目容量：${item?.total}")
    }
}