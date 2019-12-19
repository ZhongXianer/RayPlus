package com.ksballetba.rayplus.data.source.remote

import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.BaseResponseBean
import com.ksballetba.rayplus.data.bean.DemographyBean
import com.ksballetba.rayplus.data.bean.PhysicalExaminationBodyBean
import com.ksballetba.rayplus.data.bean.PhysicalExaminationListBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class BaselineVisitDataSource{

    fun getDemography(sampleId:Int,callBack: (DemographyBean) -> Unit) {
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getDemography(sampleId)
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

    fun editDemography(sampleId:Int,demographyBean: DemographyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editDemography(sampleId,demographyBean)
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

    fun getPhysicalExaminationList(sampleId:Int,callBack: (MutableList<PhysicalExaminationListBean.Data>) -> Unit) {
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getPhysicalExaminationList(sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                },
                onComplete = {
                    LogUtils.d("Completed")
                },
                onError = {
                    LogUtils.d(it.message)
                }
            )
    }

    fun editPhysicalExamination(sampleId:Int,physicalExaminationBodyBean: PhysicalExaminationBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editPhysicalExamination(sampleId,physicalExaminationBodyBean)
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

    fun deletePhysicalExamination(sampleId:Int,reportId:Int,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .deletePhysicalExamination(sampleId,reportId)
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