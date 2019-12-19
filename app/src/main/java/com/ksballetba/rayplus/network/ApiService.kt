package com.ksballetba.rayplus.network

import com.ksballetba.rayplus.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*

//token = eyJhbGciOiJIUzI1NiIsImlhdCI6MTU3Njc0MzQ4MiwiZXhwIjoxNTc2NzQ3MDgyfQ.eyJ1c2VyX2lkIjoxfQ.y-163XUIqLIDRBn2iNMQLkwsCJhkHwidEyI1JHY8eLk

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

    @GET("/patient/{sample_id}")
    fun getDemography(@Path("sample_id") sampleId:Int):Observable<DemographyBean>

    @POST("/patient/{sample_id}")
    fun editDemography(@Path("sample_id") sampleId:Int,@Body demographyBean: DemographyBean):Observable<BaseResponseBean>
}