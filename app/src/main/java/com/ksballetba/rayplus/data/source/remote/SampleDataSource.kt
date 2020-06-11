package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.NetworkType
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.*

class  SampleDataSource(context: Context){
    companion object {
        const val TAG = "SampleDataSource"
        const val DEFAULT_PAGE_LIMIT = 20
        var CURRENT_PAGE = 1;
    }


    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun loadInitial(projectId:Int,callBack: (MutableList<SampleListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getSampleList(mToken,projectId, 1, DEFAULT_PAGE_LIMIT)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if(it.msg=="获取样本成功"){
                        callBack(it.data.toMutableList())
                        mLoadStatus.postValue(NetworkState.LOADED)
                        CURRENT_PAGE = 2
                    }else{
                        mLoadStatus.postValue(NetworkState.error(it.msg))
                    }
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    LogUtils.tag(TAG).d(it.message)
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }

    fun loadMore(projectId:Int,callBack: (MutableList<SampleListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getSampleList(mToken,projectId, CURRENT_PAGE, DEFAULT_PAGE_LIMIT)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if(it.msg=="获取样本成功"){
                        callBack(it.data.toMutableList())
                        mLoadStatus.postValue(NetworkState.LOADED)
                        CURRENT_PAGE ++
                    }else{
                        mLoadStatus.postValue(NetworkState.error(it.msg))
                    }
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    LogUtils.tag(TAG).d(it.message)
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }


    fun loadAllResearchCenter(callBack: (MutableList<ResearchCenterBean>) -> Unit){
        RetrofitClient.getInstance(NetworkType.PROJECT)
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

    fun editSample(sampleEditBodyBean: SampleEditBodyBean, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editSample(mToken,sampleEditBodyBean)
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

    fun submitSample(sampleSubmitBodyBean: SampleSubmitBodyBean,callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .submitSample(mToken,sampleSubmitBodyBean)
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

    fun deleteSample(sampleId: Int,callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteSample(mToken,sampleId)
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