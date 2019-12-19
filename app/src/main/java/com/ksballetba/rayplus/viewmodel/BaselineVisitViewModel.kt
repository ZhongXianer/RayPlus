package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.DemographyBean
import com.ksballetba.rayplus.data.bean.PhysicalExaminationBodyBean
import com.ksballetba.rayplus.data.bean.PhysicalExaminationListBean
import com.ksballetba.rayplus.data.source.remote.BaselineVisitDataSource

class BaselineVisitViewModel constructor(private var baselineVisitDataSource: BaselineVisitDataSource): ViewModel() {

    fun getDemography(sampleId:Int): LiveData<DemographyBean> {
        val result = MutableLiveData<DemographyBean>()
        baselineVisitDataSource.getDemography(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editDemography(sampleId:Int,demographyBean: DemographyBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editDemography(sampleId,demographyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getPhysicalExaminationList(sampleId:Int):LiveData<MutableList<PhysicalExaminationListBean.Data>>{
        val result = MutableLiveData<MutableList<PhysicalExaminationListBean.Data>>()
        baselineVisitDataSource.getPhysicalExaminationList(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editPhysicalExamination(sampleId:Int,physicalExaminationBodyBean: PhysicalExaminationBodyBean): LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.editPhysicalExamination(sampleId,physicalExaminationBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deletePhysicalExamination(sampleId:Int,reportId:Int): LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        baselineVisitDataSource.deletePhysicalExamination(sampleId,reportId) {
            result.postValue(it)
        }
        return result
    }

}