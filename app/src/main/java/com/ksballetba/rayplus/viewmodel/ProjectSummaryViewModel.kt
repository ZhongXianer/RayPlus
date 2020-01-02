package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.AdverseEventListBean
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.ProjectSummaryBodyBean
import com.ksballetba.rayplus.data.bean.ProjectSummaryResponseBean
import com.ksballetba.rayplus.data.source.remote.ProjectSummaryDataSource

class ProjectSummaryViewModel constructor(private var projectSummaryDataSource: ProjectSummaryDataSource): ViewModel() {

    fun getProjectSummary(sampleId:Int): LiveData<ProjectSummaryResponseBean> {
        val result = MutableLiveData<ProjectSummaryResponseBean>()
        projectSummaryDataSource.getProjectSummary(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editProjectSummary(sampleId:Int,projectSummaryBodyBean: ProjectSummaryBodyBean): LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        projectSummaryDataSource.editProjectSummary(sampleId,projectSummaryBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getAllAdverseEventList(sampleId:Int): LiveData<MutableList<AdverseEventListBean.Data>>{
        val result = MutableLiveData<MutableList<AdverseEventListBean.Data>>()
        projectSummaryDataSource.getAllAdverseEventList(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = projectSummaryDataSource.mLoadStatus

}