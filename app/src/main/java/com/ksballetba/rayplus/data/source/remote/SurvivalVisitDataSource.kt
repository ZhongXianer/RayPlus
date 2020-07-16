package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.baseData.BaseResponseBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitListBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.NetworkType
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.util.getToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SurvivalVisitDataSource(context: Context) {

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = getToken()
//    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun getSurvivalVisitList(
        sampleId: Int,
        callBack: (MutableList<SurvivalVisitListBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getSurvivalVisitList(mToken, sampleId)
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

    fun editSurvivalVisit(
        sampleId: Int,
        survivalVisitBodyBean: SurvivalVisitBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editSurvivalVisit(mToken, sampleId, survivalVisitBodyBean)
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

    //新增提交治疗期随访接口
    fun submitSurvivalVisit(interviewId: Int, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .submitSurvivalVisit(mToken, interviewId)
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
                    LogUtils.tag(SampleDataSource.TAG).d(it.message)
                }
            )
    }

    fun deleteSurvivalVisit(sampleId: Int, interviewId: Int, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteSurvivalVisit(mToken, sampleId, interviewId)
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