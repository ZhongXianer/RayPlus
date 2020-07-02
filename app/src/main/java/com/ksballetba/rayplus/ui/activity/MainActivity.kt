package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.TextView
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
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.ProjectsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val PROJECT_ID = "PROJECT_ID"
        const val EXIT_APP_ACTION = "EXIT_APP_ACTION"
    }

    private lateinit var mViewModel: ProjectsViewModel
    private lateinit var mProjectsAdapter: ProjectsAdapter
    var mBackDownTime = 0.toLong()
    var mProjectList = mutableListOf<ProjectListBean.Data>()

    private val mProjectId = 1  //暂时使用


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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mBackDownTime < 1000 && mBackDownTime != 0.toLong()) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.action = EXIT_APP_ACTION
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                mBackDownTime = System.currentTimeMillis()
                toast("再按一次返回键退出")
            }
        }
        return false
    }

    /**
     * 初始化主界面，初始化相关控件及点击事件
     */
    private fun initUI() {
        setSupportActionBar(tb_main)
        btn_logout.setOnClickListener {
            logOut()
        }
        initRefresh()
//        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val userName = getUserName(this)
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.tv_doctor_name).text =
            "您好，$userName 医生"
    }

    private fun initList() {
        mViewModel = getProjectsViewModel(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_project.layoutManager = layoutManager
        mProjectsAdapter = ProjectsAdapter(R.layout.item_project, mProjectList)
        mProjectsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_project.adapter = mProjectsAdapter
        /*设置点击事件，跳转到对应的活动*/
        mProjectsAdapter.setOnItemClickListener { _, _, position ->
            navigateToSamplePage(mProjectList[position].projectId)
        }
        mViewModel.fetchLoadStatus().observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_project.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_project.finishRefresh()
                }
                Status.FAILED -> {
                    Log.d(TAG, it.msg.toString())
                    if (it.msg == "HTTP 401 UNAUTHORIZED") {
                        toast("登录已过期，请重新登录")
//                        reLogin(this)
                    } else {
                        toast(it.msg.toString())
                    }
                }
            }
        })
        srl_project.autoRefresh()
        srl_project.setOnRefreshListener {
            loadInitial(mProjectId)
        }
    }

    private fun loadInitial(projectId: Int) {
        mViewModel.fetchData(projectId).observe(this, Observer {
            mProjectList.clear()
            mProjectList.add(it)
            mProjectsAdapter.setNewData(mProjectList)
        })
    }

    private fun getProjectAuthorization(projectId: Int) {
        mViewModel.getAuthorization(projectId, getUserId(this)).observe(this, Observer {
            setAuthorization(it.toMutableList())
        })
    }

    /**
     * 退出登录点击事件
     */
    private fun logOut() {
        deleteToken(this)
        /*返回登录界面*/
        reStartLoginActivity(this)
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

    /**
     * 跳转到相应的活动
     */
    private fun navigateToSamplePage(projectId: Int) {
        val intent = Intent(this, SampleActivity::class.java)
        intent.putExtra(PROJECT_ID, projectId)
        getProjectAuthorization(projectId)
        setToken(this, projectId)
        startActivity(intent)
    }

    /**
     * 删除账号密码数据
     * 退出登录
     */
//    private fun deleteLoginToken() {
//        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.remove(LOGIN_TOKEN)
//        editor.remove(USER_NAME)
//        editor.apply()
//    }
}
