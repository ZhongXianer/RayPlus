package com.ksballetba.rayplus.util

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ksballetba.rayplus.data.source.remote.ProjectDataSource
import com.ksballetba.rayplus.data.source.remote.SampleDataSource
import com.ksballetba.rayplus.viewmodel.ProjectsViewModel
import com.ksballetba.rayplus.viewmodel.SamplesViewModel

fun getProjectsViewModel(activity: FragmentActivity): ProjectsViewModel {
    return ViewModelProvider(activity, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = ProjectDataSource()
            @Suppress("UNCHECKED_CAST")
            return ProjectsViewModel(source) as T
        }
    }).get(ProjectsViewModel::class.java)
}

fun getSamplesViewModel(activity: FragmentActivity): SamplesViewModel {
    return ViewModelProvider(activity, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = SampleDataSource()
            @Suppress("UNCHECKED_CAST")
            return SamplesViewModel(source) as T
        }
    }).get(SamplesViewModel::class.java)
}