package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.projectData.ProjectListBean
import com.ksballetba.rayplus.data.bean.projectData.ProjectProcessBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.NetworkType
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.util.getToken
import com.ksballetba.rayplus.util.getTokenByProjectId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProjectDataSource(context: Context) {

    companion object {
        const val TAG = "ProjectDataSource"
    }

    var mLoadStatus = MutableLiveData<NetworkState>()
    val mContext = context


//    fun getUserName(callBack: (UserNameBean) -> Unit){
//        RetrofitClient.getInstance(NetworkType.PROJECT)
//            .create(ApiService::class.java)
//            .getUserName(mToken)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy (
//                onNext = {
//                    callBack(it)
//                },
//                onComplete = {
//                    println("Completed")
//                },
//                onError = {
//                    mLoadStatus.postValue(NetworkState.error(it.message))
//                }
//            )
//    }

    fun loadInitial(projectId: Int, callBack: (ProjectListBean.Data) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.getInstance(NetworkType.AUTH)
            .create(ApiService::class.java)
            .getProjectList(getToken(mContext, projectId), projectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data)
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }

    fun getProjectProcess(projectId: Int, callBack: (ProjectProcessBean) -> Unit) {
        RetrofitClient.getInstance(NetworkType.PROJECT)
            .create(ApiService::class.java)
            .getProjectProcess(getTokenByProjectId(mContext, projectId))
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

    /**
     * 获取用户在项目下的权限
     */
    fun getProjectAuthorization(
        projectId: Int,
        userId: Int,
        callBack: (MutableList<String?>) -> Unit
    ) {
        RetrofitClient.getInstance(NetworkType.AUTH)
            .create(ApiService::class.java)
            .getProjectAuthorization(projectId, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                },
                onComplete = {
                    println("completed!")
                },
                onError = {
                    LogUtils.tag(SampleDataSource.TAG).d(it.message)
                }
            )
    }

}