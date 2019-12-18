package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.LoginBodyBean
import com.ksballetba.rayplus.data.bean.LoginResponseBean
import com.ksballetba.rayplus.data.source.remote.LoginDataSource

class LoginViewModel constructor(private var loginDataSource: LoginDataSource): ViewModel(){
    fun login(loginBodyBean: LoginBodyBean): LiveData<LoginResponseBean> {
        val result = MutableLiveData<LoginResponseBean>()
        loginDataSource.login(loginBodyBean) {
            result.postValue(it)
        }
        return result
    }

    fun fetchLoadStatus() = loginDataSource.mLoadStatus
}