package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.SurvivalVisitListBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SurvivalVisitDataSource(context: Context){

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun getSurvivalVisitList(sampleId:Int,callBack: (MutableList<SurvivalVisitListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getSurvivalVisitList(mToken,sampleId)
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

    fun editSurvivalVisit(sampleId:Int,survivalVisitBodyBean: SurvivalVisitBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editSurvivalVisit(mToken,sampleId,survivalVisitBodyBean)
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

    fun deleteSurvivalVisit(sampleId:Int,interviewId:Int,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .deleteSurvivalVisit(mToken,sampleId,interviewId)
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
}