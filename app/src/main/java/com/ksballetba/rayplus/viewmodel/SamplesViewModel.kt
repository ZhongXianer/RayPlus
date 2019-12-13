package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.ResearchCenterBean
import com.ksballetba.rayplus.data.bean.SampleListBean
import com.ksballetba.rayplus.data.source.remote.SampleDataSource

class SamplesViewModel constructor(private var sampleDataSource: SampleDataSource): ViewModel(){
    fun fetchData(): LiveData<MutableList<SampleListBean.Data>> {
        val result = MutableLiveData<MutableList<SampleListBean.Data>>()
        sampleDataSource.loadInitial {
            result.postValue(it)
        }
        return result
    }

    fun fetchDataAfter(): LiveData<MutableList<SampleListBean.Data>> {
        val result = MutableLiveData<MutableList<SampleListBean.Data>>()
        sampleDataSource.loadAfter {
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

    fun fetchLoadStatus() = sampleDataSource.mLoadStatus
}