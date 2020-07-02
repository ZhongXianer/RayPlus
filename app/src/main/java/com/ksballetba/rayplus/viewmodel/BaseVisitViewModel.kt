package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.source.remote.BaseVisitDataSource

class BaseVisitViewModel constructor(private var baseVisitDataSource: BaseVisitDataSource): ViewModel() {

    fun getVisitTime(sampleId:Int,cycleNumber:Int): LiveData<VisitTimeBean> {
        val result = MutableLiveData<VisitTimeBean>()
        baseVisitDataSource.getVisitTime(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editVisitTime(sampleId:Int,cycleNumber:Int,visitEditBean: VisitEditBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editVisitTime(sampleId,cycleNumber,visitEditBean) {
            result.postValue(it)
        }
        return result
    }

    fun getLabInspection(sampleId:Int,cycleNumber:Int): LiveData<LabInspectionResponseBean> {
        val result = MutableLiveData<LabInspectionResponseBean>()
        baseVisitDataSource.getLabInspection(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editLabInspection(sampleId:Int,cycleNumber:Int,labInspectionBodyBean: LabInspectionBodyBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editLabInspection(sampleId,cycleNumber,labInspectionBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getImagingEvaluationList(sampleId:Int,cycleNumber:Int):LiveData<MutableList<ImagingEvaluationListBean.Data>>{
        val result = MutableLiveData<MutableList<ImagingEvaluationListBean.Data>>()
        baseVisitDataSource.getImagingEvaluationList(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editImagingEvaluation(sampleId:Int,cycleNumber:Int,imagingEvaluationBodyBean: ImagingEvaluationBodyBean): LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editImagingEvaluation(sampleId,cycleNumber,imagingEvaluationBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deleteImagingEvaluation(sampleId:Int,cycleNumber:Int,evaluateId:Int): LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.deleteImagingEvaluation(sampleId,cycleNumber,evaluateId) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = baseVisitDataSource.mLoadStatus

}