package com.ksballetba.rayplus.util

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ksballetba.rayplus.data.source.remote.*
import com.ksballetba.rayplus.viewmodel.*

fun getLoginViewModel(activity: FragmentActivity):LoginViewModel{
    return ViewModelProvider(activity, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = LoginDataSource()
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(source) as T
        }
    }).get(LoginViewModel::class.java)
}

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

fun getBaseVisitViewModel(fragment: Fragment): BaseVisitViewModel {
    return ViewModelProvider(fragment, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = BaseVisitDataSource()
            @Suppress("UNCHECKED_CAST")
            return BaseVisitViewModel(source) as T
        }
    }).get(BaseVisitViewModel::class.java)
}

fun getBaseVisitViewModel(activity: FragmentActivity): BaseVisitViewModel {
    return ViewModelProvider(activity, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = BaseVisitDataSource()
            @Suppress("UNCHECKED_CAST")
            return BaseVisitViewModel(source) as T
        }
    }).get(BaseVisitViewModel::class.java)
}

fun getBaselineVisitViewModel(fragment: Fragment): BaselineVisitViewModel {
    return ViewModelProvider(fragment, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = BaselineVisitDataSource()
            @Suppress("UNCHECKED_CAST")
            return BaselineVisitViewModel(source) as T
        }
    }).get(BaselineVisitViewModel::class.java)
}

fun getBaselineVisitViewModel(activity: FragmentActivity): BaselineVisitViewModel {
    return ViewModelProvider(activity, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val source = BaselineVisitDataSource()
            @Suppress("UNCHECKED_CAST")
            return BaselineVisitViewModel(source) as T
        }
    }).get(BaselineVisitViewModel::class.java)
}