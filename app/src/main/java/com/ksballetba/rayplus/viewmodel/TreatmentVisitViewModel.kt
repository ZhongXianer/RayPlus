package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.source.remote.TreatmentVisitDataSource

class
TreatmentVisitViewModel constructor(private var treatmentVisitDataSource: TreatmentVisitDataSource) : ViewModel() {

    fun getNavigation(sampleId: Int): LiveData<List<NavigationBean>> {
        val result = MutableLiveData<List<NavigationBean>>()
        treatmentVisitDataSource.getNavigation(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun addCycle(sampleId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        treatmentVisitDataSource.addCycle(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun getMainPhysicalSignList(sampleId: Int,cycleNumber:Int): LiveData<List<MainPhysicalSignListBean.Data>> {
        val result = MutableLiveData<List<MainPhysicalSignListBean.Data>>()
        treatmentVisitDataSource.getMainPhysicalSignList(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editMainPhysicalSign(sampleId: Int,cycleNumber:Int,mainPhysicalSignBodyBean: MainPhysicalSignBodyBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        treatmentVisitDataSource.editMainPhysicalSign(sampleId,cycleNumber,mainPhysicalSignBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deleteMainPhysicalSign(sampleId: Int,cycleNumber:Int,mainSymptomId:Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        treatmentVisitDataSource.deleteMainPhysicalSign(sampleId,cycleNumber,mainSymptomId) {
            result.postValue(it)
        }
        return result
    }

    fun getECOGScore(sampleId: Int,cycleNumber:Int): LiveData<ECOGBean> {
        val result = MutableLiveData<ECOGBean>()
        treatmentVisitDataSource.getECOGScore(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editECOGScore(sampleId: Int,cycleNumber:Int,ecogBean: ECOGBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        treatmentVisitDataSource.editECOGScore(sampleId,cycleNumber,ecogBean) {
            result.postValue(it)
        }
        return result
    }

    fun getTherapeuticEvaluation(sampleId: Int,cycleNumber:Int): LiveData<TherapeuticEvaluationBean> {
        val result = MutableLiveData<TherapeuticEvaluationBean>()
        treatmentVisitDataSource.getTherapeuticEvaluation(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editTherapeuticEvaluation(sampleId: Int,cycleNumber:Int,therapeuticEvaluationBean: TherapeuticEvaluationBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        treatmentVisitDataSource.editTherapeuticEvaluation(sampleId,cycleNumber,therapeuticEvaluationBean) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = treatmentVisitDataSource.mLoadStatus
}