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

    @POST("/cycle_time/{sample_id}/{cycle_number}")
    fun editVisitTime(@Path("sample_id") sampleId:Int,@Path("cycle_number") cycleNumber:Int,@Body visitTimeBean: VisitTimeBean):Observable<BaseResponseBean>

}