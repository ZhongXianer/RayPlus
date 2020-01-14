package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.AdverseEventListBean
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.ProjectSummaryBodyBean
import com.ksballetba.rayplus.data.bean.ProjectSummaryResponseBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProjectSummaryDataSource(context: Context){

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun getProjectSummary(sampleId:Int,callBack: (ProjectSummaryResponseBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getProjectSummary(mToken,sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("Completed")
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onError = {
                    LogUtils.d(it.message)
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }

    fun editProjectSummary(sampleId:Int,projectSummaryBodyBean: ProjectSummaryBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editProjectSummary(mToken,sampleId,projectSummaryBodyBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("Completed")
                },
                onError = {
                    LogUtils.d(it.message)
                }
            )
    }

    fun getAllAdverseEventList(sampleId:Int,callBack: (MutableList<AdverseEventListBean.Data>) -> Unit){
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getAllAdverseEventList(mToken,sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                },
                onComplete = {
                    LogUtils.d("Completed")
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onError = {
                    LogUtils.d(it.message)
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }
}