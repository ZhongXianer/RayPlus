package com.ksballetba.rayplus.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.network.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TreatmentVisitDataSource{

    var mLoadStatus = MutableLiveData<NetworkState>()

    fun getNavigation(sampleId:Int,callBack: (List<NavigationBean>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getNavigation(sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    mLoadStatus.postValue(NetworkState.LOADED)
                    LogUtils.d("Completed")
                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error(it.message))
                    LogUtils.d(it.message)
                }
            )
    }

    fun addCycle(sampleId:Int,callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.instance
            .create(ApiService::class.java)
            .addCycle(sampleId)
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

    fun getMainPhysicalSignList(sampleId:Int,cycleNumber:Int,callBack: (MutableList<MainPhysicalSignListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getMainPhysicalSignList(sampleId,cycleNumber)
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

    fun editMainPhysicalSign(sampleId:Int,cycleNumber:Int,mainPhysicalSignBodyBean: MainPhysicalSignBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editMainPhysicalSign(sampleId,cycleNumber,mainPhysicalSignBodyBean)
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

    fun deleteMainPhysicalSign(sampleId:Int,cycleNumber:Int,mainSymptomId:Int,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .deleteMainPhysicalSign(sampleId,cycleNumber,mainSymptomId)
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

    fun getECOGScore(sampleId:Int,cycleNumber:Int,callBack: (ECOGBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getECOGScore(sampleId,cycleNumber)
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

    fun editECOGScore(sampleId:Int,cycleNumber:Int,ecogBean: ECOGBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editECOGScore(sampleId,cycleNumber,ecogBean)
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

    fun getTherapeuticEvaluation(sampleId:Int,cycleNumber:Int,callBack: (TherapeuticEvaluationBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getTherapeuticEvaluation(sampleId,cycleNumber)
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

    fun editTherapeuticEvaluation(sampleId:Int,cycleNumber:Int,therapeuticEvaluationBean: TherapeuticEvaluationBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editTherapeuticEvaluation(sampleId,cycleNumber,therapeuticEvaluationBean)
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

    fun getTreatmentRecordList(sampleId:Int,cycleNumber:Int,callBack: (MutableList<TreatmentRecordListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getTreatmentRecordList(sampleId,cycleNumber)
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

    fun editTreatmentRecord(sampleId:Int,cycleNumber:Int,treatmentRecordBodyBean: TreatmentRecordBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editTreatmentRecord(sampleId,cycleNumber,treatmentRecordBodyBean)
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

    fun deleteTreatmentRecord(sampleId:Int,cycleNumber:Int,treatmentRecordId:Int,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .deleteTreatmentRecord(sampleId,cycleNumber,treatmentRecordId)
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

    fun getAdjustment(sampleId:Int,cycleNumber:Int,callBack: (AdjustBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getAdjustment(sampleId,cycleNumber)
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

    fun editAdjustment(sampleId:Int,cycleNumber:Int,adjustBodyBean: AdjustBodyBean,callBack: (BaseResponseBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .editAdjustment(sampleId,cycleNumber,adjustBodyBean)
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