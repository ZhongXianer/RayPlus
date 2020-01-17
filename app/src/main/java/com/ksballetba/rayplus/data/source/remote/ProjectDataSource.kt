package com.ksballetba.rayplus.data.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.ksballetba.rayplus.data.bean.ProjectListBean
import com.ksballetba.rayplus.data.bean.UserNameBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class  ProjectDataSource(context: Context){

    companion object{
        const val TAG = "ProjectDataSource"
    }

    var mLoadStatus = MutableLiveData<NetworkState>()

    private val mToken = "Bearer ${context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE).getString(LOGIN_TOKEN,"")}"

    fun getUserName(callBack: (UserNameBean) -> Unit){
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getUserName(mToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    callBack(it)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error(it.message))
                }
            )
    }

    fun loadInitial(callBack: (MutableList<ProjectListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getProjectList(mToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
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
}