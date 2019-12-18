package com.ksballetba.rayplus.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.*

class  SampleDataSource{
    companion object {
        const val DEF_PAGE_SIZE = 10
        const val TAG = "SampleDataSource"
    }
    var mLoadStatus = MutableLiveData<NetworkState>()
    var mNextPageKey = 10

    fun loadInitial(token:String?,callBack: (MutableList<SampleListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getSampleList(token,1, DEF_PAGE_SIZE)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                    mNextPageKey = 10
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    LogUtils.tag(TAG).d(it.message)
                    mLoadStatus.postValue(NetworkState.error("网络加载失败"))
                }
            )
    }


    fun loadAfter(token:String?,callBack: (MutableList<SampleListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getSampleList(token,mNextPageKey, DEF_PAGE_SIZE)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                    mNextPageKey+=10
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onComplete = {
                    println("Completed")

                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error("网络加载失败"))
                }
            )
    }

    fun loadAllResearchCenter(callBack: (MutableList<ResearchCenterBean>) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getAllResearchCenterList()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.toMutableList())
                },
                onComplete = {
                    LogUtils.d("Completed")

                },
                onError = {
                    LogUtils.d(it.message)
                }
            )
    }

    fun editSample(token:String?, sampleEditBodyBean: SampleEditBodyBean, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editSample(token,sampleEditBodyBean)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("Completed")
                },
                onError = {
                    LogUtils.tag(TAG).d(it.message)
                }
            )
    }

    fun submitSample(token:String?,sampleSubmitBodyBean: SampleSubmitBodyBean,callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.instance
            .create(ApiService::class.java)
            .submitSample(token,sampleSubmitBodyBean)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("Completed")
                },
                onError = {
                    LogUtils.tag(TAG).d(it.message)
                }
            )
    }
}