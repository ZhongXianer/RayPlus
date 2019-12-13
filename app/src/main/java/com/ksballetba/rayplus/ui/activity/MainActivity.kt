package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.*
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ImmersionBar
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.ProjectListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.adapter.ProjectsAdapter
import com.ksballetba.rayplus.util.getProjectsViewModel
import com.ksballetba.rayplus.viewmodel.ProjectsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    private lateinit var mViewModel:ProjectsViewModel
    private lateinit var mProjectsAdapter: ProjectsAdapter
    var mProjectList = mutableListOf<ProjectListBean.Data>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).transparentStatusBar().init()
        setContentView(R.layout.activity_main)
        initUI()
        initList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                dl_main.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI(){
        setSupportActionBar(tb_main)
        btn_logout.setOnClickListener {
            logOut()
        }
        initRefresh()
    }

    private fun initList(){
        mViewModel = getProjectsViewModel(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_project.layoutManager = layoutManager
        mProjectsAdapter = ProjectsAdapter(R.layout.item_project,mProjectList)
        mProjectsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_project.adapter = mProjectsAdapter
        mProjectsAdapter.setOnItemClickListener { _, _, position ->
            navigateToSamplePage()
        }
        mViewModel.fetchLoadStatus().observe(this, Observer {
            when(it.status){
                Status.RUNNING -> {
                    srl_project.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_project.finishRefresh()
                }
                Status.FAILED -> {
                    toast("网络加载失败")
                }
            }
        })
        srl_project.autoRefresh()
        srl_project.setOnRefreshListener {
            loadInitial()
        }
    }

    private fun loadInitial(){
        mViewModel.fetchData().observe(this, Observer {
            mProjectList = it
            mProjectsAdapter.setNewData(mProjectList)
        })
    }

    private fun loadAfter(){
        mViewModel.fetchDataAfter().observe(this, Observer {
            mProjectList.addAll(it)
            mProjectsAdapter.addData(it)
        })
    }

    private fun logOut(){
        startActivity(Intent(this,CRFActivity::class.java))
    }

    private fun initRefresh() {
        srl_project.setEnableRefresh(true)
        srl_project.setEnableLoadMore(false)
        srl_project.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_project.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_project.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_project.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_project.setEnableLoadMoreWhenContentNotFull(true)
    }

    private fun navigateToSamplePage(){
        startActivity(Intent(this,SampleActivity::class.java))
    }
}
