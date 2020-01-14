package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.source.remote.SampleDataSource

class SamplesViewModel constructor(private var sampleDataSource: SampleDataSource): ViewModel(){

    fun fetchData(): LiveData<MutableList<SampleListBean.Data>> {
        val result = MutableLiveData<MutableList<SampleListBean.Data>>()
        sampleDataSource.loadInitial {
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

    fun editSample(sampleEditBodyBean: SampleEditBodyBean):LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.editSample(sampleEditBodyBean){
            result.postValue(it)
        }
        return result
    }

    fun submitSample(sampleSubmitBodyBean: SampleSubmitBodyBean):LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.submitSample(sampleSubmitBodyBean){
            result.postValue(it)
        }
        return result
    }

    fun deleteSample(sampleId:Int):LiveData<BaseResponseBean>{
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.deleteSample(sampleId){
            result.postValue(it)
        }
        return result
    }


    fun fetchLoadStatus() = sampleDataSource.mLoadStatus
}