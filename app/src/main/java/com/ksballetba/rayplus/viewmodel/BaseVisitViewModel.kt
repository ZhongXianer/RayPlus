package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.VisitTimeBean
import com.ksballetba.rayplus.data.source.remote.BaseVisitDataSource

class BaseVisitViewModel constructor(private var baseVisitDataSource: BaseVisitDataSource): ViewModel() {

    fun getVisitTime(sampleId:Int,cycleNumber:Int): LiveData<VisitTimeBean> {
        val result = MutableLiveData<VisitTimeBean>()
        baseVisitDataSource.getVisitTime(sampleId,cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editVisitTime(sampleId:Int,cycleNumber:Int,visitTimeBean:VisitTimeBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editVisitTime(sampleId,cycleNumber,visitTimeBean) {
            result.postValue(it)
        }
        return result
    }

    fun getLoadStatus() = baseVisitDataSource.mLoadStatus

}