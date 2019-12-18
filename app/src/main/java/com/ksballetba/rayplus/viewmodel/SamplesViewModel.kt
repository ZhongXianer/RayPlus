package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.source.remote.SampleDataSource

class SamplesViewModel constructor(private var sampleDataSource: SampleDataSource): ViewModel(){
    fun fetchData(token:String?): LiveData<MutableList<SampleListBean.Data>> {
        val result = MutableLiveData<MutableList<SampleListBean.Data>>()
        sampleDataSource.loadInitial(token) {
            result.postValue(it)
        }
        return result
    }

    fun fetchDataAfter(token:String?): LiveData<MutableList<SampleListBean.Data>> {
        val result = MutableLiveData<MutableList<SampleListBean.Data>>()
        sampleDataSource.loadAfter(token) {
            result.postValue(it)
        }
        return result
    }

    fun fetchAllResearchCenter(): LiveData<MutableList<ResearchCenterBean>>{
        val result = MutableLiveData<MutableList<ResearchCenterBean>>()
        sampleDataSource.loadAllResearchCenter {
            result.postValue(it)
        }
        return result
    }

    fun editSample(token:String?,sampleEditBodyBean: SampleEditBodyBean):LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.editSample(token,sampleEditBodyBean){
            result.postValue(it)
        }
        return result
    }

    fun submitSample(token:String?,sampleSubmitBodyBean: SampleSubmitBodyBean):LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.submitSample(token,sampleSubmitBodyBean){
            result.postValue(it)
        }
        return result
    }

    fun fetchLoadStatus() = sampleDataSource.mLoadStatus
}