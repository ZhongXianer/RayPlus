package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.baseData.BaseResponseBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleEditBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleListBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSelectBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSubmitBodyBean
import com.ksballetba.rayplus.data.source.remote.SampleDataSource

class SamplesViewModel constructor(private var sampleDataSource: SampleDataSource) : ViewModel() {

    fun fetchData(sampleQueryBodyBean: SampleQueryBodyBean): LiveData<SampleListBean> {
        val result = MutableLiveData<SampleListBean>()
        sampleDataSource.loadInitial(sampleQueryBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun fetchMore(sampleQueryBodyBean: SampleQueryBodyBean): LiveData<SampleListBean> {
        val result = MutableLiveData<SampleListBean>()
        sampleDataSource.loadMore(sampleQueryBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getAllResearchCenter(projectId: Int): LiveData<MutableList<SampleSelectBodyBean.Data>> {
        val result = MutableLiveData<MutableList<SampleSelectBodyBean.Data>>()
        sampleDataSource.getResearchCenters(projectId) {
            result.postValue(it)
        }
        return result
    }

    fun getGroupIds(): LiveData<MutableList<SampleSelectBodyBean.Data>> {
        val result = MutableLiveData<MutableList<SampleSelectBodyBean.Data>>()
        sampleDataSource.getGroupIds() {
            result.postValue(it)
        }
        return result
    }

    fun editSample(sampleEditBodyBean: SampleEditBodyBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.editSample(sampleEditBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun submitSample(sampleSubmitBodyBean: SampleSubmitBodyBean): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.submitSample(sampleSubmitBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun deleteSample(sampleId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.deleteSample(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun unlockSample(sampleId: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        sampleDataSource.unlockSample(sampleId) {
            result.postValue(it)
        }
        return result
    }


    fun fetchLoadStatus() = sampleDataSource.mLoadStatus
}