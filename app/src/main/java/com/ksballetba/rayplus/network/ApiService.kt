package com.ksballetba.rayplus.network

import com.ksballetba.rayplus.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*

//http://10.14.219.68:80/
//http://localhost:80/project?page=1&limit=10  实验项目
//http://localhost:80/research_center_all 所有的研究中心
//http://localhost:80/sample?page=1&limit=10 实验样本
//http://localhost:80/submit_sample 提交实验样本 POST
//http://localhost:80/login 登录 POST
//http://localhost:80/logout 退出登录 POST


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

}