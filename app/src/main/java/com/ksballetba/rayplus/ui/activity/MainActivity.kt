package com.ksballetba.rayplus.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ImmersionBar
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.ProjectListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import com.ksballetba.rayplus.ui.adapter.ProjectsAdapter
import com.ksballetba.rayplus.util.getProjectsViewModel
import com.ksballetba.rayplus.viewmodel.ProjectsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
        const val EXIT_APP_ACTION = "EXIT_APP_ACTION"
    }

    private lateinit var mViewModel:ProjectsViewModel
    private lateinit var mProjectsAdapter: ProjectsAdapter
    var mBackDownTime = 0.toLong()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - mBackDownTime<1000&&mBackDownTime!=0.toLong()){
                val intent = Intent(this,LoginActivity::class.java)
                intent.action = EXIT_APP_ACTION
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }else{
                mBackDownTime = System.currentTimeMillis()
                toast("再按一次返回键退出")
            }
        }
        return false
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
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(LOGIN_TOKEN,"")
        mViewModel.fetchData().observe(this, Observer {
            mProjectList = it
            mProjectsAdapter.setNewData(mProjectList)
        })
        mViewModel.getUserName(token).observe(this, Observer {
            if(it.userName==null){
                toast("登录已过期，请重新登录")
                logOut()
            }
            nav_view.getHeaderView(0).findViewById<TextView>(R.id.tv_doctor_name).text = "您好，${it.userName}医生"
        })
    }

    private fun loadAfter(){
        mViewModel.fetchDataAfter().observe(this, Observer {
            mProjectList.addAll(it)
            mProjectsAdapter.addData(it)
        })
    }

    private fun logOut(){
        deleteLoginToken()
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
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

    private fun deleteLoginToken(){
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(LOGIN_TOKEN)
        editor.apply()
    }
}
