package com.ksballetba.rayplus.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.ksballetba.rayplus.data.bean.ProjectListBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class  ProjectDataSource{
    companion object {
        const val DEF_PAGE_SIZE = 10
    }
    var mLoadStatus = MutableLiveData<NetworkState>()
    var mNextPageKey = 10

    fun loadInitial(callBack: (MutableList<ProjectListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getProjectList(0,DEF_PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                    mNextPageKey = 10
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onComplete = {
                    println("Completed")
                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error("网络加载失败"))
                }
            )
    }


    fun loadAfter(callBack: (MutableList<ProjectListBean.Data>) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .getProjectList(mNextPageKey, DEF_PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callBack(it.data.toMutableList())
                    mNextPageKey+=10
                    mLoadStatus.postValue(NetworkState.LOADED)
                },
                onComplete = {
                    println("Completed")

                },
                onError = {
                    mLoadStatus.postValue(NetworkState.error("网络加载失败"))
                }
            )
    }
}