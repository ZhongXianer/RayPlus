package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.treatmentVisitData.*
import com.ksballetba.rayplus.network.*
import com.ksballetba.rayplus.util.getToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TreatmentVisitDataSource(context: Context) {

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = getToken()
//    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun getNavigation(
        sampleId: Int,
        callBack: (MutableList<NavigationBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getNavigation(mToken, sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
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

    fun addCycle(sampleId: Int, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .addCycle(mToken, sampleId)
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

    fun deleteCycle(sampleId: Int, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteCycle(mToken, sampleId)
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

    fun submitCycle(sampleId: Int, cycleNumber: Int, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .submitCycle(mToken, sampleId, cycleNumber)
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

    fun getSubmitStatus(
        sampleId: Int,
        callBack: (MutableList<TreatmentVisitSubmitResponseBean.Data>) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getTreatmentCycleSubmitStatus(mToken, sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                },
                onComplete = {
                    LogUtils.d("completed")
                },
                onError = {
                    LogUtils.d(it.message)
                }
            )
    }

    fun getMainPhysicalSignList(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (MutableList<MainPhysicalSignListBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getMainPhysicalSignList(mToken, sampleId, cycleNumber)
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

    fun editMainPhysicalSign(
        sampleId: Int,
        cycleNumber: Int,
        mainPhysicalSignBodyBean: MainPhysicalSignBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editMainPhysicalSign(mToken, sampleId, cycleNumber, mainPhysicalSignBodyBean)
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

    fun deleteMainPhysicalSign(
        sampleId: Int,
        cycleNumber: Int,
        mainSymptomId: Int,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteMainPhysicalSign(mToken, sampleId, cycleNumber, mainSymptomId)
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

    fun getECOGScore(sampleId: Int, cycleNumber: Int, callBack: (ECOGBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getECOGScore(mToken, sampleId, cycleNumber)
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

    fun editECOGScore(
        sampleId: Int,
        cycleNumber: Int,
        ecog: ECOGBean.Data,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editECOGScore(mToken, sampleId, cycleNumber, ecog)
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

    fun getTherapeuticEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (TherapeuticEvaluationBean) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getTherapeuticEvaluation(mToken, sampleId, cycleNumber)
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

    fun editTherapeuticEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        therapeuticEvaluationBean: TherapeuticEvaluationBean.Data,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editTherapeuticEvaluation(mToken, sampleId, cycleNumber, therapeuticEvaluationBean)
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

    fun getTreatmentRecordList(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (MutableList<TreatmentRecordListBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getTreatmentRecordList(mToken, sampleId, cycleNumber)
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

    fun editTreatmentRecord(
        sampleId: Int,
        cycleNumber: Int,
        treatmentRecordBodyBean: TreatmentRecordBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editTreatmentRecord(mToken, sampleId, cycleNumber, treatmentRecordBodyBean)
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

    fun deleteTreatmentRecord(
        sampleId: Int,
        cycleNumber: Int,
        treatmentRecordId: Int,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteTreatmentRecord(mToken, sampleId, cycleNumber, treatmentRecordId)
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

    fun getAdjustment(sampleId: Int, cycleNumber: Int, callBack: (AdjustBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getAdjustment(mToken, sampleId, cycleNumber)
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

    fun editAdjustment(
        sampleId: Int,
        cycleNumber: Int,
        adjustBodyBean: AdjustBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editAdjustment(mToken, sampleId, cycleNumber, adjustBodyBean)
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

    fun getAdverseEventList(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (MutableList<AdverseEventListBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getAdverseEventList(mToken, sampleId, cycleNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data!!.toMutableList())
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

    fun editAdverseEvent(
        sampleId: Int,
        cycleNumber: Int,
        adverseEventBodyBean: AdverseEventBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editAdverseEvent(mToken, sampleId, cycleNumber, adverseEventBodyBean)
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

    fun deleteAdverseEvent(
        sampleId: Int,
        cycleNumber: Int,
        adverseEventId: Int,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteAdverseEvent(mToken, sampleId, cycleNumber, adverseEventId)
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