package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.baseLineData.*
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitSubmitResponseBean
import com.ksballetba.rayplus.data.source.remote.BaselineVisitDataSource

class BaselineVisitViewModel constructor(private var baselineVisitDataSource: BaselineVisitDataSource) :
    ViewModel() {

    fun getDemography(sampleId: Int): LiveData<DemographyBean> {
        val result = MutableLiveData<DemographyBean>()
        baselineVisitDataSource.getDemography(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editDemography(
        sampleId: Int,
        editDemographyBean: EditDemographyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editDemography(sampleId, editDemographyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getPhysicalExaminationList(sampleId: Int): LiveData<MutableList<PhysicalExaminationListBean.Data>> {
        val result = MutableLiveData<MutableList<PhysicalExaminationListBean.Data>>()
        baselineVisitDataSource.getPhysicalExaminationList(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editPhysicalExamination(
        sampleId: Int,
        physicalExaminationBodyBean: PhysicalExaminationBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editPhysicalExamination(sampleId, physicalExaminationBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deletePhysicalExamination(sampleId: Int, reportId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.deletePhysicalExamination(sampleId, reportId) {
            result.postValue(it)
        }
        return result
    }

    fun getPreviousHistory(sampleId: Int): LiveData<PreviousHistoryResponseBean> {
        val result = MutableLiveData<PreviousHistoryResponseBean>()
        baselineVisitDataSource.getPreviousHistory(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editPreviousHistory(
        sampleId: Int,
        previousHistoryBodyBean: PreviousHistoryBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editPreviousHistory(sampleId, previousHistoryBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getFirstVisitProcess(sampleId: Int): LiveData<FirstVisitProcessResponseBean> {
        val result = MutableLiveData<FirstVisitProcessResponseBean>()
        baselineVisitDataSource.getFirstVisitProcess(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editFirstVisitProcess(
        sampleId: Int,
        firstVisitProcessBodyBean: FirstVisitProcessBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editFirstVisitProcess(sampleId, firstVisitProcessBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getTreatmentHistoryList(sampleId: Int): LiveData<MutableList<TreatmentHistoryListBean.Data>> {
        val result = MutableLiveData<MutableList<TreatmentHistoryListBean.Data>>()
        baselineVisitDataSource.getTreatmentHistoryList(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editTreatmentHistory(
        sampleId: Int,
        treatmentHistoryBodyBean: TreatmentHistoryBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editTreatmentHistory(sampleId, treatmentHistoryBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deleteTreatmentHistory(sampleId: Int, diagnoseNumber: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.deleteTreatmentHistory(sampleId, diagnoseNumber) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = baselineVisitDataSource.mLoadStatus

}