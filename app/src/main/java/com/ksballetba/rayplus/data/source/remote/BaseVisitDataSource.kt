package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.baseData.*
import com.ksballetba.rayplus.data.bean.projectSummaryData.SummarySignatureBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSelectBodyBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitSubmitResponseBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.NetworkType
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.util.getToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class BaseVisitDataSource(context: Context) {

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = getToken()
//    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

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

    fun getVisitTime(sampleId: Int, cycleNumber: Int, callBack: (VisitTimeBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getVisitTime(mToken, sampleId, cycleNumber)
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

    fun editVisitTime(
        sampleId: Int,
        cycleNumber: Int,
        visitEditBean: VisitEditBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editVisitTime(mToken, sampleId, cycleNumber, visitEditBean)
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

    fun getResearchCenters(projectId: Int, callBack: (SampleSelectBodyBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.AUTH)
            .create(ApiService::class.java)
            .getResearchCenters(mToken, projectId)
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

    fun getBaselineInvestigatorSignature(
        sampleId: Int,
        callBack: (InvestigatorSignatureBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getBaselineInvestigatorSignature(mToken, sampleId)
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

    fun addBaselineInvestigatorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean,
        callBack: (PartResponseBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .addBaselineInvestigatorSignature(mToken, sampleId, signatureRequestBodyBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("completed")
                },
                onError = {
                    LogUtils.d(it.message)
                }
            )
    }

    fun getCycleInvestigatorSignature(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (InvestigatorSignatureBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getCycleInvestigatorSignature(mToken, sampleId, cycleNumber)
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

    fun addCycleInvestigatorSignature(
        sampleId: Int,
        cycleNumber: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean,
        callBack: (PartResponseBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .addCycleInvestigatorSignature(mToken, sampleId, cycleNumber, signatureRequestBodyBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    LogUtils.d("completed")
                },
                onError = {
                    LogUtils.d(it.message)
                }
            )

    }

    fun getLabInspection(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (LabInspectionResponseBean) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getLabInspection(mToken, sampleId, cycleNumber)
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
                    LogUtils.tag("LabIns").d(it.message)
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }

    fun editLabInspection(
        sampleId: Int,
        cycleNumber: Int,
        labInspectionBodyBean: LabInspectionBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editLabInspection(mToken, sampleId, cycleNumber, labInspectionBodyBean)
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

    fun getIsImgingEvaluate(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (IsImagingEvaluateResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getIsImagingEvaluate(mToken, sampleId, cycleNumber)
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

    fun editIsImagingEvaluate(
        sampleId: Int,
        cycleNumber: Int,
        isPhotoEvaluate: IsImagingEvaluateBodyBean,
        callBack: (EditIsImagingEvaluateResponseBody) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editIsImagingEvaluate(mToken, sampleId, cycleNumber, isPhotoEvaluate)
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

    fun getImagingEvaluationList(
        sampleId: Int,
        cycleNumber: Int,
        callBack: (MutableList<ImagingEvaluationListBean.Data>) -> Unit
    ) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getImagingEvaluationList(mToken, sampleId, cycleNumber)
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

    fun editImagingEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        imagingEvaluationBodyBean: ImagingEvaluationBodyBean,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .editImagingEvaluation(mToken, sampleId, cycleNumber, imagingEvaluationBodyBean)
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

    fun deleteImagingEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int,
        callBack: (BaseResponseBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteImagingEvaluation(mToken, sampleId, cycleNumber, evaluateId)
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

    fun getImagingEvaluationFileList(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int,
        callBack: (MutableList<ImagingEvaluationFileListBean.Data?>) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getImagingEvaluationFileList(mToken, sampleId, cycleNumber, evaluateId)
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

    fun uploadImagingEvaluationFile(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int,
//        fileBodyBean: FileBodyBean,
        file: MultipartBody.Part,
        callBack: (PartResponseBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .uploadImagingEvaluationFile(mToken, sampleId, cycleNumber, evaluateId, file)
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

    fun deleteImagingEvaluationFile(filePath: String, callBack: (BaseResponseBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .deleteImagingEvaluationFile(mToken, filePath)
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

    fun getSummarySignature(sampleId: Int, callBack: (SummarySignatureBodyBean.Data?) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getSummarySignature(mToken, sampleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data)
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

    fun addSummaryInvestigatorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean,
        callBack: (PartResponseBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .addSummaryInvestigatorSignature(mToken, sampleId, signatureRequestBodyBean)
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

    fun addSummaryInspectorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean,
        callBack: (PartResponseBodyBean) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .addSummaryInspectorSignature(mToken, sampleId, signatureRequestBodyBean)
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