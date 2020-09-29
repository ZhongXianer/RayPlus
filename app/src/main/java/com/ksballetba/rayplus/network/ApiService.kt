package com.ksballetba.rayplus.network

import com.ksballetba.rayplus.data.bean.AuthorizationResponseBean
import com.ksballetba.rayplus.data.bean.*
import com.ksballetba.rayplus.data.bean.baseData.*
import com.ksballetba.rayplus.data.bean.baseLineData.*
import com.ksballetba.rayplus.data.bean.loginData.LoginBodyBean
import com.ksballetba.rayplus.data.bean.loginData.LoginResponseBean
import com.ksballetba.rayplus.data.bean.projectData.ProjectListBean
import com.ksballetba.rayplus.data.bean.projectData.ProjectProcessBean
import com.ksballetba.rayplus.data.bean.projectSummaryData.ProjectSummaryBodyBean
import com.ksballetba.rayplus.data.bean.projectSummaryData.ProjectSummaryResponseBean
import com.ksballetba.rayplus.data.bean.projectSummaryData.SummarySignatureBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleEditBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleListBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSelectBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSubmitBodyBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitListBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("/v1/token")
    fun login(@Body loginBodyBean: LoginBodyBean): Observable<LoginResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/v1/project/{project_id}")
    fun getProjectList(
        @Header("Authorization") token: String?,
        @Path("project_id") projectId: Int
    ): Observable<ProjectListBean>

    @Headers("Content-Type:application/json")
    @GET("/project/process")
    fun getProjectProcess(@Header("Authorization") token: String?): Observable<ProjectProcessBean>

    @Headers("Content-Type:application/json")
    @GET("/v1/user/auths")
    fun getProjectAuthorization(
        @Query("project_id") projectId: Int, @Query("user_id") userId: Int
    ): Observable<AuthorizationResponseBean>

    @Headers("Content-Type: application/json")
    @GET("/v1/research_centers")
    fun getResearchCenters(
        @Header("Authorization") token: String?,
        @Query("project_id") projectId: Int
    ): Observable<SampleSelectBodyBean>

    @Headers("Content-Type: application/json")
    @GET("/group_ids")
    fun getGroupIds(@Header("Authorization") token: String): Observable<SampleSelectBodyBean>

    @Headers("Content-Type:application/json")
    @GET("/sample")
    fun getSampleList(
        @Header("Authorization") token: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("tumor_pathological_type") tumorPathologicalType: String?,
        @Query("research_center_id") researchCenterId: Int?,
        @Query("group_id") groupId: Int?,
        @Query("sex") sex: Int?,
        @Query("name") name: String?,
        @Query("IDcard") idCard: String?,
        @Query("patient_ids") patientIds: String?,
        @Query("submit_status") submitStatus: Int?
    ): Observable<SampleListBean>

    @Headers("Content-Type:application/json")
    @POST("/sample")
    fun editSample(
        @Header("Authorization") token: String?,
        @Body sampleEditBodyBean: SampleEditBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/submit_sample")
    fun submitSample(
        @Header("Authorization") token: String?,
        @Body sampleSubmitBodyBean: SampleSubmitBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/sample/{sample_id}")
    fun deleteSample(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/sample/unlock/{sample_id}")
    fun unlockSample(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<BaseResponseBean>


    @Headers("Content-Type:application/json")
    @GET("/cycle_time/{sample_id}/{cycle_number}")
    fun getVisitTime(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<VisitTimeBean>

    @Headers("Content-Type:application/json")
    @POST("/cycle_time/{sample_id}/{cycle_number}")
    fun editVisitTime(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body visitEditBean: VisitEditBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/sample/signature/{sample_id}")
    fun getBaselineInvestigatorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<InvestigatorSignatureBodyBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/sample/signature/{sample_id}")
    fun addBaselineInvestigatorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body signatureRequestBodyBean: SignatureRequestBodyBean
    ): Observable<PartResponseBodyBean>

    @Headers("Content-Type:application/json")
    @GET("/cycle/signature/{sample_id}/{cycle_number}")
    fun getCycleInvestigatorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int
    ): Observable<InvestigatorSignatureBodyBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/cycle/signature/{sample_id}/{cycle_number}")
    fun addCycleInvestigatorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int,
        @Body signatureRequestBodyBean: SignatureRequestBodyBean
    ): Observable<PartResponseBodyBean>

    @Headers("Content-Type:application/json")
    @GET("/patient/{sample_id}")
    fun getDemography(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<DemographyBean>

    @Headers("Content-Type:application/json")
    @POST("/patient/{sample_id}")
    fun editDemography(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body editDemographyBean: EditDemographyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/patient_report_table/{sample_id}")
    fun getPhysicalExaminationList(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<PhysicalExaminationListBean>

    @Headers("Content-Type:application/json")
    @POST("/patient_report/{sample_id}")
    fun editPhysicalExamination(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body physicalExaminationBodyBean: PhysicalExaminationBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @DELETE("/patient_report/{sample_id}/{report_id}")
    fun deletePhysicalExamination(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "report_id"
        ) reportId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/patient_history/{sample_id}")
    fun getPreviousHistory(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<PreviousHistoryResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/patient_history/{sample_id}")
    fun editPreviousHistory(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body previousHistoryBodyBean: PreviousHistoryBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/first_diagnose/{sample_id}")
    fun getFirstVisitProcess(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<FirstVisitProcessResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/first_diagnose/{sample_id}")
    fun editFirstVisitProcess(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body firstVisitProcessBodyBean: FirstVisitProcessBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/diagnose_history/{sample_id}")
    fun getTreatmentHistoryList(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<TreatmentHistoryListBean>

    @Headers("Content-Type:application/json")
    @POST("/diagnose_history/{sample_id}")
    fun editTreatmentHistory(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body treatmentHistoryBodyBean: TreatmentHistoryBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/diagnose_history/{sample_id}/{diagnose_id}")
    fun deleteTreatmentHistory(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "diagnose_id"
        ) diagnoseNumber: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/lab_inspection/{sample_id}/{cycle_number}")
    fun getLabInspection(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<LabInspectionResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/lab_inspection/{sample_id}/{cycle_number}")
    fun editLabInspection(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body labInspectionBodyBean: LabInspectionBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/is_photo_evaluate/{sample_id}/{cycle_number}")
    fun getIsImagingEvaluate(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int
    ): Observable<IsImagingEvaluateResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/is_photo_evaluate/{sample_id}/{cycle_number}")
    fun editIsImagingEvaluate(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int,
        @Body isImagingEvaluateBodyBean: IsImagingEvaluateBodyBean
    ): Observable<EditIsImagingEvaluateResponseBody>

    @Headers("Content-Type:application/json")
    @GET("/photo_evaluate_table/{sample_id}/{cycle_number}")
    fun getImagingEvaluationList(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<ImagingEvaluationListBean>

    @Headers("Content-Type:application/json")
    @POST("/photo_evaluate/{sample_id}/{cycle_number}")
    fun editImagingEvaluation(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body imagingEvaluationBodyBean: ImagingEvaluationBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/photo_evaluate/file/{sample_id}/{cycle_number}/{evaluate_id}")
    fun getImagingEvaluationFileList(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int,
        @Path("evaluate_id") evaluateId: Int
    ): Observable<ImagingEvaluationFileListBean>

    @Multipart
    @POST("/photo_evaluate/file/{sample_id}/{cycle_number}/{evaluate_id}")
    fun uploadImagingEvaluationFile(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Path("cycle_number") cycleNumber: Int,
        @Path("evaluate_id") evaluateId: Int,
        @Part file: MultipartBody.Part
    ): Observable<PartResponseBodyBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/static_file")
    fun deleteImagingEvaluationFile(
        @Header("Authorization") token: String?,
        @Query("file_path") filePath: String
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/photo_evaluate/{sample_id}/{cycle_number}/{evaluate_id}")
    fun deleteImagingEvaluation(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Path("evaluate_id") evaluateId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/nav/{sample_id}")
    fun getNavigation(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<NavigationBean>

    @Headers("Content-Type:application/json")
    @POST("/cycle/{sample_id}")
    fun addCycle(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/cycle/{sample_id}")
    fun deleteCycle(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/submit_cycle/{sample_id}/{cycle_number}")
    fun submitCycle(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/cycle/submit_info/{sample_id}")
    fun getTreatmentCycleSubmitStatus(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<TreatmentVisitSubmitResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/main_symptom_table/{sample_id}/{cycle_number}")
    fun getMainPhysicalSignList(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<MainPhysicalSignListBean>

    @Headers("Content-Type:application/json")
    @POST("/main_symptom/{sample_id}/{cycle_number}")
    fun editMainPhysicalSign(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body mainPhysicalSignBodyBean: MainPhysicalSignBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/main_symptom/{sample_id}/{cycle_number}/{main_symptom_id}")
    fun deleteMainPhysicalSign(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Path("main_symptom_id") mainSymptomId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/ECOG/{sample_id}/{cycle_number}")
    fun getECOGScore(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<ECOGBean>

    @Headers("Content-Type:application/json")
    @POST("/ECOG/{sample_id}/{cycle_number}")
    fun editECOGScore(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body ecog: ECOGBean.Data
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/evaluation/{sample_id}/{cycle_number}")
    fun getTherapeuticEvaluation(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<TherapeuticEvaluationBean>

    @Headers("Content-Type:application/json")
    @POST("/evaluation/{sample_id}/{cycle_number}")
    fun editTherapeuticEvaluation(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body therapeuticEvaluationBean: TherapeuticEvaluationBean.Data
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/treatment_record_table/{sample_id}/{cycle_number}")
    fun getTreatmentRecordList(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<TreatmentRecordListBean>

    @Headers("Content-Type:application/json")
    @POST("/treatment_record/{sample_id}/{cycle_number}")
    fun editTreatmentRecord(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body treatmentRecordBodyBean: TreatmentRecordBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/treatment_record/{sample_id}/{cycle_number}/{treatment_record_id}")
    fun deleteTreatmentRecord(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Path("treatment_record_id") treatmentRecordId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/treatment_record_adjustment_status/{sample_id}/{cycle_number}")
    fun getAdjustment(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<AdjustBean>

    @Headers("Content-Type:application/json")
    @POST("/treatment_record_adjustment_status/{sample_id}/{cycle_number}")
    fun editAdjustment(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body adjustBodyBean: AdjustBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/adverse_event_table/{sample_id}/{cycle_number}")
    fun getAdverseEventList(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int
    ): Observable<AdverseEventListBean>

    @Headers("Content-Type:application/json")
    @POST("/adverse_event/{sample_id}/{cycle_number}")
    fun editAdverseEvent(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Body adverseEventBodyBean: AdverseEventBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/adverse_event/{sample_id}/{cycle_number}/{adverse_event_id}")
    fun deleteAdverseEvent(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "cycle_number"
        ) cycleNumber: Int, @Path("adverse_event_id") adverseEventId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/interview_table/{sample_id}")
    fun getSurvivalVisitList(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<SurvivalVisitListBean>

    @Headers("Content-Type:application/json")
    @POST("/interview/{sample_id}")
    fun editSurvivalVisit(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body survivalVisitBodyBean: SurvivalVisitBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/interview/{sample_id}/{interview_id}")
    fun deleteSurvivalVisit(
        @Header("Authorization") token: String?, @Path("sample_id") sampleId: Int, @Path(
            "interview_id"
        ) interviewId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/interview/submit/{interview_id}")
    fun submitSurvivalVisit(
        @Header("Authorization") token: String?,
        @Path("interview_id") interviewId: Int
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/summary/{sample_id}")
    fun getProjectSummary(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<ProjectSummaryResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/summary/{sample_id}")
    fun editProjectSummary(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body projectSummaryBodyBean: ProjectSummaryBodyBean
    ): Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @GET("/adverse_event_table_all/{sample_id}")
    fun getAllAdverseEventList(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<AdverseEventListBean>

    @Headers("Content-Type: application/json")
    @GET("/summary/signature/{sample_id}")
    fun getSummarySignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int
    ): Observable<SummarySignatureBodyBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/summary/signature/{sample_id}")
    fun addSummaryInvestigatorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body signatureRequestBodyBean: SignatureRequestBodyBean
    ): Observable<PartResponseBodyBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/summary/cra_signature/{sample_id}")
    fun addSummaryInspectorSignature(
        @Header("Authorization") token: String?,
        @Path("sample_id") sampleId: Int,
        @Body signatureRequestBodyBean: SignatureRequestBodyBean
    ): Observable<PartResponseBodyBean>
}