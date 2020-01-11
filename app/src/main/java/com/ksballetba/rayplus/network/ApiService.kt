package com.ksballetba.rayplus.network

import com.ksballetba.rayplus.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("/login")
    fun login(@Body loginBodyBean: LoginBodyBean):Observable<LoginResponseBean>

    @GET("/project")
    fun getProjectList(@Query("page") page:Int,@Query("limit") limit:Int):Observable<ProjectListBean>

    @GET("/user_div_info")
    fun getUserName(@Header("Authorization") token:String?):Observable<UserNameBean>

    @GET("/sample")
    fun getSampleList(@Header("Authorization") token:String?, @Query("page") page:Int, @Query("limit") limit:Int):Observable<SampleListBean>

    @Headers("Content-Type:application/json")
    @POST("/sample")
    fun editSample(@Header("Authorization") token:String?,@Body sampleEditBodyBean: SampleEditBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/submit_sample")
    fun submitSample(@Header("Authorization") token:String?,@Body sampleSubmitBodyBean: SampleSubmitBodyBean):Observable<BaseResponseBean>

    @GET("/research_center_all")
    fun getAllResearchCenterList():Observable<List<ResearchCenterBean>>

    @GET("/cycle_time/{sample_id}/{cycle_number}")
    fun getVisitTime(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<VisitTimeBean>

    @Headers("Content-Type:application/json")
    @POST("/cycle_time/{sample_id}/{cycle_number}")
    fun editVisitTime(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body visitTimeBean: VisitTimeBean):Observable<BaseResponseBean>

    @GET("/patient/{sample_id}")
    fun getDemography(@Path("sample_id") sampleId:Int):Observable<DemographyBean>

    @Headers("Content-Type:application/json")
    @POST("/patient/{sample_id}")
    fun editDemography(@Path("sample_id") sampleId:Int,@Body demographyBean: DemographyBean):Observable<BaseResponseBean>

    @GET("/patient_report_table/{sample_id}")
    fun getPhysicalExaminationList(@Path("sample_id") sampleId:Int):Observable<PhysicalExaminationListBean>

    @Headers("Content-Type:application/json")
    @POST("/patient_report/{sample_id}")
    fun editPhysicalExamination(@Path("sample_id") sampleId:Int,@Body physicalExaminationBodyBean: PhysicalExaminationBodyBean):Observable<BaseResponseBean>

    @DELETE("/patient_report/{sample_id}/{report_id}")
    fun deletePhysicalExamination(@Path("sample_id") sampleId:Int,@Path("report_id") reportId:Int):Observable<BaseResponseBean>

    @GET("/patient_history/{sample_id}")
    fun getPreviousHistory(@Path("sample_id") sampleId:Int):Observable<PreviousHistoryResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/patient_history/{sample_id}")
    fun editPreviousHistory(@Path("sample_id") sampleId:Int,@Body previousHistoryBodyBean: PreviousHistoryBodyBean):Observable<BaseResponseBean>

    @GET("/first_diagnose/{sample_id}")
    fun getFirstVisitProcess(@Path("sample_id") sampleId:Int):Observable<FirstVisitProcessResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/first_diagnose/{sample_id}")
    fun editFirstVisitProcess(@Path("sample_id") sampleId:Int,@Body firstVisitProcessBodyBean: FirstVisitProcessBodyBean):Observable<BaseResponseBean>

    @GET("/diagnose_history/{sample_id}")
    fun getTreatmentHistoryList(@Path("sample_id") sampleId:Int):Observable<TreatmentHistoryListBean>

    @Headers("Content-Type:application/json")
    @POST("/diagnose_history/{sample_id}")
    fun editTreatmentHistory(@Path("sample_id") sampleId:Int,@Body treatmentHistoryBodyBean: TreatmentHistoryBodyBean):Observable<BaseResponseBean>

//    @DELETE("/patient_report/{sample_id}/{report_id}")
//    fun deleteTreatmentHistory(@Path("sample_id") sampleId:Int,@Path("report_id") reportId:Int):Observable<BaseResponseBean>

    @GET("/lab_inspection/{sample_id}/{cycle_number}")
    fun getLabInspection(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<LabInspectionResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/lab_inspection/{sample_id}/{cycle_number}")
    fun editLabInspection(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body labInspectionBodyBean: LabInspectionBodyBean):Observable<BaseResponseBean>

    @GET("/photo_evaluate_table/{sample_id}/{cycle_number}")
    fun getImagingEvaluationList(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<ImagingEvaluationListBean>

    @Headers("Content-Type:application/json")
    @POST("/photo_evaluate/{sample_id}/{cycle_number}")
    fun editImagingEvaluation(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body imagingEvaluationBodyBean: ImagingEvaluationBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/photo_evaluate/{sample_id}/{cycle_number}/{evaluate_id}")
    fun deleteImagingEvaluation(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Path("evaluate_id") evaluateId:Int):Observable<BaseResponseBean>

    @GET("/nav/{sample_id}")
    fun getNavigation(@Path("sample_id") sampleId:Int):Observable<List<NavigationBean>>

    @Headers("Content-Type:application/json")
    @POST("/cycle/{sample_id}")
    fun addCycle(@Path("sample_id") sampleId:Int):Observable<BaseResponseBean>

    @GET("/main_symptom_table/{sample_id}/{cycle_number}")
    fun getMainPhysicalSignList(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<MainPhysicalSignListBean>

    @Headers("Content-Type:application/json")
    @POST("/main_symptom/{sample_id}/{cycle_number}")
    fun editMainPhysicalSign(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body mainPhysicalSignBodyBean: MainPhysicalSignBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/main_symptom/{sample_id}/{cycle_number}/{main_symptom_id}")
    fun deleteMainPhysicalSign(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Path("main_symptom_id") mainSymptomId:Int):Observable<BaseResponseBean>

    @GET("/ECOG/{sample_id}/{cycle_number}")
    fun getECOGScore(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<ECOGBean>

    @Headers("Content-Type:application/json")
    @POST("/ECOG/{sample_id}/{cycle_number}")
    fun editECOGScore(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body ecogBean: ECOGBean):Observable<BaseResponseBean>

    @GET("/evaluation/{sample_id}/{cycle_number}")
    fun getTherapeuticEvaluation(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<TherapeuticEvaluationBean>

    @Headers("Content-Type:application/json")
    @POST("/evaluation/{sample_id}/{cycle_number}")
    fun editTherapeuticEvaluation(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body therapeuticEvaluationBean: TherapeuticEvaluationBean):Observable<BaseResponseBean>

    @GET("/treatment_record_table/{sample_id}/{cycle_number}")
    fun getTreatmentRecordList(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<TreatmentRecordListBean>

    @Headers("Content-Type:application/json")
    @POST("/treatment_record/{sample_id}/{cycle_number}")
    fun editTreatmentRecord(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body treatmentRecordBodyBean: TreatmentRecordBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/treatment_record/{sample_id}/{cycle_number}/{treatment_record_id}")
    fun deleteTreatmentRecord(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Path("treatment_record_id") treatmentRecordId:Int):Observable<BaseResponseBean>

    @GET("/treatment_record_adjustment_status/{sample_id}/{cycle_number}")
    fun getAdjustment(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<AdjustBean>

    @Headers("Content-Type:application/json")
    @POST("/treatment_record_adjustment_status/{sample_id}/{cycle_number}")
    fun editAdjustment(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body adjustBodyBean: AdjustBodyBean):Observable<BaseResponseBean>

    @GET("/adverse_event_table/{sample_id}/{cycle_number}")
    fun getAdverseEventList(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int):Observable<AdverseEventListBean>

    @Headers("Content-Type:application/json")
    @POST("/adverse_event/{sample_id}/{cycle_number}")
    fun editAdverseEvent(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body adverseEventBodyBean: AdverseEventBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/adverse_event/{sample_id}/{cycle_number}/{adverse_event_id}")
    fun deleteAdverseEvent(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Path("adverse_event_id") adverseEventId:Int):Observable<BaseResponseBean>

    @GET("/interview_table/{sample_id}")
    fun getSurvivalVisitList(@Path("sample_id") sampleId:Int):Observable<SurvivalVisitListBean>

    @Headers("Content-Type:application/json")
    @POST("/interview/{sample_id}")
    fun editSurvivalVisit(@Path("sample_id") sampleId:Int,@Body survivalVisitBodyBean: SurvivalVisitBodyBean):Observable<BaseResponseBean>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @DELETE("/interview/{sample_id}/{interview_id}")
    fun deleteSurvivalVisit(@Path("sample_id") sampleId:Int,@Path("interview_id") interviewId:Int):Observable<BaseResponseBean>

    @GET("/summary/{sample_id}")
    fun getProjectSummary(@Path("sample_id") sampleId:Int):Observable<ProjectSummaryResponseBean>

    @Headers("Content-Type:application/json")
    @POST("/summary/{sample_id}")
    fun editProjectSummary(@Path("sample_id") sampleId:Int,@Body projectSummaryBodyBean: ProjectSummaryBodyBean):Observable<BaseResponseBean>

    @GET("/adverse_event_table_all/{sample_id}")
    fun getAllAdverseEventList(@Path("sample_id") sampleId:Int):Observable<AdverseEventListBean>
}