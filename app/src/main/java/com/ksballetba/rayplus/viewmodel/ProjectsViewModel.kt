package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.ProjectListBean
import com.ksballetba.rayplus.data.bean.UserNameBean
import com.ksballetba.rayplus.data.source.remote.ProjectDataSource

class ProjectsViewModel constructor(private var projectDataSource: ProjectDataSource):ViewModel(){

    fun getUserName(): LiveData<UserNameBean> {
        val result = MutableLiveData<UserNameBean>()
        projectDataSource.getUserName {
            result.postValue(it)
        }
        return result
    }

    fun fetchData(): LiveData<MutableList<ProjectListBean.Data>> {
        val result = MutableLiveData<MutableList<ProjectListBean.Data>>()
        projectDataSource.loadInitial {
            result.postValue(it)
        }
        return result
    }


    fun fetchLoadStatus() = projectDataSource.mLoadStatus
}