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
    fun getPreviousHistory(@Path("sample_id") sampleId:Int):Observable<PreviousHistoryBean>

    @Headers("Content-Type:application/json")
    @POST("/patient_history/{sample_id}")
    fun editPreviousHistory(@Path("sample_id") sampleId:Int,@Body previousHistoryBean: PreviousHistoryBean):Observable<BaseResponseBean>

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
}