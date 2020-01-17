package com.ksballetba.rayplus.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.ksballetba.rayplus.data.bean.LoginBodyBean
import com.ksballetba.rayplus.data.bean.LoginResponseBean
import com.ksballetba.rayplus.network.ApiService
import com.ksballetba.rayplus.network.NetworkState
import com.ksballetba.rayplus.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LoginDataSource{

    var mLoadStatus = MutableLiveData<NetworkState>()

    fun login(loginBodyBean: LoginBodyBean,callBack: (LoginResponseBean) -> Unit) {
        mLoadStatus.postValue(NetworkState.LOADING)
        RetrofitClient.instance
            .create(ApiService::class.java)
            .login(loginBodyBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
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
}