package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitListBean
import com.ksballetba.rayplus.data.source.remote.SurvivalVisitDataSource

class SurvivalVisitViewModel constructor(private var survivalVisitDataSource: SurvivalVisitDataSource) :
    ViewModel() {

    fun getSurvivalVisitList(sampleId: Int): LiveData<MutableList<SurvivalVisitListBean.Data>> {
        val result = MutableLiveData<MutableList<SurvivalVisitListBean.Data>>()
        survivalVisitDataSource.getSurvivalVisitList(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editSurvivalVisit(
        sampleId: Int,
        survivalVisitBodyBean: SurvivalVisitBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        survivalVisitDataSource.editSurvivalVisit(sampleId, survivalVisitBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun submitSurvivalVisit(interviewId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        survivalVisitDataSource.submitSurvivalVisit(interviewId) {
            result.postValue(it)
        }
        return result
    }

    fun deleteSurvivalVisit(sampleId: Int, interviewId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        survivalVisitDataSource.deleteSurvivalVisit(sampleId, interviewId) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = survivalVisitDataSource.mLoadStatus

}