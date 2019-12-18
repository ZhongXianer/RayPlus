package com.ksballetba.rayplus.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SampleEditBodyBean
import com.ksballetba.rayplus.data.bean.SampleListBean
import com.ksballetba.rayplus.data.bean.SampleSubmitBodyBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.LOGIN_TOKEN
import com.ksballetba.rayplus.ui.activity.LoginActivity.Companion.SHARED_PREFERENCE_NAME
import com.ksballetba.rayplus.ui.activity.SampleEditActivity.Companion.REFRESH_LAST_PAGE
import com.ksballetba.rayplus.ui.adapter.SamplesAdapter
import com.ksballetba.rayplus.util.getSamplesViewModel
import com.ksballetba.rayplus.viewmodel.SamplesViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_sample.*
import org.jetbrains.anko.toast

class SampleActivity : AppCompatActivity() {

    companion object{
        const val TAG = "SampleActivity"
        const val SAMPLE_ID = "SAMPLE_ID"
        const val SAMPLE_BODY = "SAMPLE_BODY"
    }

    private lateinit var mViewModel: SamplesViewModel
    private lateinit var mSamplesAdapter: SamplesAdapter
    var mSampleList = mutableListOf<SampleListBean.Data>()
    var mToken:String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this,R.color.colorPrimary)
        setContentView(R.layout.activity_sample)
        initUI()
        initList()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent?.action == REFRESH_LAST_PAGE){
            srl_sample.autoRefresh()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI(){
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        mToken = sharedPreferences.getString(LOGIN_TOKEN,"")
        setSupportActionBar(tb_sample)
        initRefresh()
        fab_add_sample.setOnClickListener {
            navigateToSampleEditActivity(-1,null)
        }
    }

    private fun initList(){
        mViewModel = getSamplesViewModel(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_sample.layoutManager = layoutManager
        mSamplesAdapter = SamplesAdapter(R.layout.item_sample,mSampleList)
        mSamplesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_sample.adapter = mSamplesAdapter
        mSamplesAdapter.setOnItemClickListener { _, _, position ->
            navigateToCRFActivity(mSampleList[position].sampleId)
        }
        mSamplesAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.btn_sample_edit->{
                    val sample = mSampleList[position]
                    val sampleBody = SampleEditBodyBean(
                        sample.date,
                        sample.groupId,
                        sample.idNum,
                        sample.inGroupTime,
                        sample.patientIds,
                        sample.patientName,
                        sample.researchCenterId,
                        sample.sampleId,
                        if(sample.sex == "男") 0 else 1,
                        sample.signTime
                    )
                    navigateToSampleEditActivity(mSampleList[position].sampleId,sampleBody)
                }
                R.id.btn_sample_submit->{
                    XPopup.Builder(this).asConfirm("信息","请问是否确认提交到总中心"){
                        submitSample(mSampleList[position].sampleId)
                    }.show()
                }
            }
        }
        mViewModel.fetchLoadStatus().observe(this, Observer {
            when(it.status){
                Status.RUNNING -> {
                    srl_sample.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_sample.finishRefresh()
                }
                Status.FAILED -> {
                    toast("网络加载失败")
                }
            }
        })
        srl_sample.autoRefresh()
        srl_sample.setOnRefreshListener {
            loadInitial()
        }
    }

    private fun loadInitial(){
        mViewModel.fetchData(mToken).observe(this, Observer {
            mSampleList = it
            mSamplesAdapter.setNewData(mSampleList)
        })
    }

    private fun loadAfter(){
        mViewModel.fetchDataAfter(mToken).observe(this, Observer {
            mSampleList.addAll(it)
            mSamplesAdapter.addData(it)
        })
    }

    private fun navigateToSampleEditActivity(sampleId: Int,sampleBody: SampleEditBodyBean?){
        val intent = Intent(this,SampleEditActivity::class.java)
        intent.putExtra(SAMPLE_ID,sampleId)
        if(sampleId>0){
            intent.putExtra(SAMPLE_BODY,sampleBody)
        }
        startActivity(intent)
    }

    private fun navigateToCRFActivity(sampleId: Int){
        val intent = Intent(this,CRFActivity::class.java)
        intent.putExtra(SAMPLE_ID,sampleId)
        startActivity(intent)
    }

    private fun submitSample(sampleId:Int){
        mViewModel.submitSample(mToken, SampleSubmitBodyBean(sampleId)).observe(this, Observer {
            if(it.code==200){
                toast("样本提交成功")
            }else{
                toast("样本提交失败")
            }
        })
    }

    private fun initRefresh() {
        srl_sample.setEnableRefresh(true)
        srl_sample.setEnableLoadMore(false)
        srl_sample.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_sample.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_sample.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_sample.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_sample.setEnableLoadMoreWhenContentNotFull(true)
    }
}


