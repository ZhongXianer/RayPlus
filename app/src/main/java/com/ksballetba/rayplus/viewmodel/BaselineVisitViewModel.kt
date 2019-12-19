package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.DemographyBean
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

}