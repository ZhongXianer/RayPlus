package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.loginData.LoginBodyBean
import com.ksballetba.rayplus.data.bean.loginData.LoginResponseBean
import com.ksballetba.rayplus.data.source.remote.LoginDataSource

/**
 * Login界面的ViewModel类
 */
class LoginViewModel constructor(private var loginDataSource: LoginDataSource): ViewModel(){

    fun login(loginBodyBean: LoginBodyBean): LiveData<LoginResponseBean> {
        val result = MutableLiveData<LoginResponseBean>()
        loginDataSource.login(loginBodyBean) {
            result.postValue(it)  //Posts a task to a main thread to set the given value
        }
        return result
    }

    fun fetchLoadStatus() = loginDataSource.mLoadStatus
}