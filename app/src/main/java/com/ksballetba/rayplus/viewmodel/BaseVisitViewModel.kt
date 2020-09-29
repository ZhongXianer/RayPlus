package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.baseData.*
import com.ksballetba.rayplus.data.bean.projectSummaryData.SummarySignatureBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSelectBodyBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitSubmitResponseBean
import com.ksballetba.rayplus.data.source.remote.BaseVisitDataSource
import okhttp3.MultipartBody

class BaseVisitViewModel constructor(private var baseVisitDataSource: BaseVisitDataSource) :
    ViewModel() {

    fun getVisitTime(sampleId: Int, cycleNumber: Int): LiveData<VisitTimeBean> {
        val result = MutableLiveData<VisitTimeBean>()
        baseVisitDataSource.getVisitTime(sampleId, cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun submitBaseline(sampleId: Int, cycleNumber: Int): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.submitCycle(sampleId, cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun getSubmitStatus(sampleId: Int): LiveData<MutableList<TreatmentVisitSubmitResponseBean.Data>> {
        val result = MutableLiveData<MutableList<TreatmentVisitSubmitResponseBean.Data>>()
        baseVisitDataSource.getSubmitStatus(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun editVisitTime(
        sampleId: Int,
        cycleNumber: Int,
        visitEditBean: VisitEditBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editVisitTime(sampleId, cycleNumber, visitEditBean) {
            result.postValue(it)
        }
        return result
    }

    fun getResearchCenters(projectId: Int): LiveData<MutableList<SampleSelectBodyBean.Data>> {
        val result = MutableLiveData<MutableList<SampleSelectBodyBean.Data>>()
        baseVisitDataSource.getResearchCenters(projectId) {
            result.postValue(it.data.toMutableList())
        }
        return result
    }

    fun getBaselineInvestigatorSignature(sampleId: Int): LiveData<InvestigatorSignatureBodyBean.Data> {
        val result = MutableLiveData<InvestigatorSignatureBodyBean.Data>()
        baseVisitDataSource.getBaselineInvestigatorSignature(sampleId) {
            result.postValue(it.data)
        }
        return result
    }

    fun addBaselineInvestigatorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean
    ): LiveData<PartResponseBodyBean> {
        val result = MutableLiveData<PartResponseBodyBean>()
        baseVisitDataSource.addBaselineInvestigatorSignature(sampleId, signatureRequestBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getCycleInvestigatorSignature(
        sampleId: Int,
        cycleNumber: Int
    ): LiveData<InvestigatorSignatureBodyBean.Data> {
        val result = MutableLiveData<InvestigatorSignatureBodyBean.Data>()
        baseVisitDataSource.getCycleInvestigatorSignature(sampleId, cycleNumber) {
            result.postValue(it.data)
        }
        return result
    }

    fun addCycleInvestigatorSignature(
        sampleId: Int,
        cycleNumber: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean
    ): LiveData<PartResponseBodyBean> {
        val result = MutableLiveData<PartResponseBodyBean>()
        baseVisitDataSource.addCycleInvestigatorSignature(
            sampleId,
            cycleNumber,
            signatureRequestBodyBean
        ) {
            result.postValue(it)
        }
        return result
    }

    fun getLabInspection(sampleId: Int, cycleNumber: Int): LiveData<LabInspectionResponseBean> {
        val result = MutableLiveData<LabInspectionResponseBean>()
        baseVisitDataSource.getLabInspection(sampleId, cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editLabInspection(
        sampleId: Int,
        cycleNumber: Int,
        labInspectionBodyBean: LabInspectionBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editLabInspection(sampleId, cycleNumber, labInspectionBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun getIsImagingEvaluate(
        sampleId: Int,
        cycleNumber: Int
    ): LiveData<IsImagingEvaluateResponseBean> {
        val result = MutableLiveData<IsImagingEvaluateResponseBean>()
        baseVisitDataSource.getIsImgingEvaluate(sampleId, cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editIsImagingEvaluate(
        sampleId: Int,
        cycleNumber: Int,
        isPhotoEvaluate: Int
    ): LiveData<EditIsImagingEvaluateResponseBody> {
        val result = MutableLiveData<EditIsImagingEvaluateResponseBody>()
        baseVisitDataSource.editIsImagingEvaluate(
            sampleId,
            cycleNumber,
            IsImagingEvaluateBodyBean(isPhotoEvaluate)
        ) {
            result.postValue(it)
        }
        return result
    }

    fun getImagingEvaluationList(
        sampleId: Int,
        cycleNumber: Int
    ): LiveData<MutableList<ImagingEvaluationListBean.Data>> {
        val result = MutableLiveData<MutableList<ImagingEvaluationListBean.Data>>()
        baseVisitDataSource.getImagingEvaluationList(sampleId, cycleNumber) {
            result.postValue(it)
        }
        return result
    }

    fun editImagingEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        imagingEvaluationBodyBean: ImagingEvaluationBodyBean
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.editImagingEvaluation(
            sampleId,
            cycleNumber,
            imagingEvaluationBodyBean
        ) {
            result.postValue(it)
        }
        return result
    }

    fun deleteImagingEvaluation(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int
    ): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.deleteImagingEvaluation(sampleId, cycleNumber, evaluateId) {
            result.postValue(it)
        }
        return result
    }

    fun getImagingEvaluationFileList(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int
    ): LiveData<MutableList<ImagingEvaluationFileListBean.Data?>> {
        val result = MutableLiveData<MutableList<ImagingEvaluationFileListBean.Data?>>()
        baseVisitDataSource.getImagingEvaluationFileList(sampleId, cycleNumber, evaluateId) {
            result.postValue(it)
        }
        return result
    }

    fun uploadImagingEvaluationFile(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int,
//        fileBodyBean: FileBodyBean
        file: MultipartBody.Part
    ): LiveData<PartResponseBodyBean> {
        val result = MutableLiveData<PartResponseBodyBean>()
        baseVisitDataSource.uploadImagingEvaluationFile(
            sampleId,
            cycleNumber,
            evaluateId,
            file
        ) {
            result.postValue(it)
        }
        return result
    }

    fun deleteImagingEvaluationFile(filePath: String): LiveData<BaseResponseBean> {
        val result = MutableLiveData<BaseResponseBean>()
        baseVisitDataSource.deleteImagingEvaluationFile(filePath) {
            result.postValue(it)
        }
        return result
    }

    fun getSummarySignature(sampleId: Int): LiveData<SummarySignatureBodyBean.Data?> {
        val result = MutableLiveData<SummarySignatureBodyBean.Data?>()
        baseVisitDataSource.getSummarySignature(sampleId) {
            result.postValue(it)
        }
        return result
    }

    fun addSummaryInvestigatorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean
    ): LiveData<PartResponseBodyBean> {
        val result = MutableLiveData<PartResponseBodyBean>()
        baseVisitDataSource.addSummaryInvestigatorSignature(sampleId, signatureRequestBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun addSummaryInspectorSignature(
        sampleId: Int,
        signatureRequestBodyBean: SignatureRequestBodyBean
    ): LiveData<PartResponseBodyBean> {
        val result = MutableLiveData<PartResponseBodyBean>()
        baseVisitDataSource.addSummaryInspectorSignature(sampleId, signatureRequestBodyBean) {
            result.postValue(it)
        }
        return result
    }


    fun getLoadStatus() = baseVisitDataSource.mLoadStatus

}