package com.ksballetba.rayplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksballetba.rayplus.data.bean.projectData.ProjectListBean
import com.ksballetba.rayplus.data.bean.projectData.ProjectProcessBean
import com.ksballetba.rayplus.data.source.remote.ProjectDataSource

class ProjectsViewModel constructor(private var projectDataSource: ProjectDataSource) :
    ViewModel() {

//    fun getUserName(): LiveData<UserNameBean> {
//        val result = MutableLiveData<UserNameBean>()
//        projectDataSource.getUserName {
//            result.postValue(it)
//        }
//        return result
//    }

    fun fetchData(projectId: Int): LiveData<ProjectListBean.Data> {
        val result = MutableLiveData<ProjectListBean.Data>()
        projectDataSource.loadInitial(projectId) {
            result.postValue(it)
        }
        return result
    }

    fun getProjectProcess(projectId: Int): LiveData<ProjectProcessBean> {
        val result = MutableLiveData<ProjectProcessBean>()
        projectDataSource.getProjectProcess(projectId) {
            result.postValue(it)
        }
        return result
    }

    fun getAuthorization(projectId: Int, userId: Int): LiveData<MutableList<String?>> {
        val result = MutableLiveData<MutableList<String?>>()
        projectDataSource.getProjectAuthorization(projectId, userId) {
            result.postValue(it)
        }
        return result
    }


    fun fetchLoadStatus() = projectDataSource.mLoadStatus
}