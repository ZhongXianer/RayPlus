package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.sampleData.SampleEditBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleListBean
import com.ksballetba.rayplus.data.bean.sampleData.SampleSubmitBodyBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.MainActivity.Companion.PROJECT_ID
import com.ksballetba.rayplus.ui.activity.SampleEditActivity.Companion.REFRESH_LAST_PAGE
import com.ksballetba.rayplus.ui.adapter.SamplesAdapter
import com.ksballetba.rayplus.util.getSamplesViewModel
import com.ksballetba.rayplus.util.getUserId
import com.ksballetba.rayplus.util.judgeUnlockSample
import com.ksballetba.rayplus.util.reLogin
import com.ksballetba.rayplus.viewmodel.SamplesViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_sample.*
import org.jetbrains.anko.toast

class SampleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SampleActivity"
        const val SAMPLE_ID = "SAMPLE_ID"
        const val SAMPLE_BODY = "SAMPLE_BODY"
    }

    var mProjectId = 0

    private lateinit var mViewModel: SamplesViewModel
    private lateinit var mSamplesAdapter: SamplesAdapter
    var mSampleList = mutableListOf<SampleListBean.Data>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setContentView(R.layout.activity_sample)
        initUI()
        initList()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action == REFRESH_LAST_PAGE) {
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

    private fun initUI() {
        setSupportActionBar(tb_sample)
        initRefresh()
        mProjectId = intent.getIntExtra(PROJECT_ID, 0)
        fab_add_sample.setOnClickListener {
            navigateToSampleEditActivity(-1, null)
        }
    }

    private fun initList() {
        mViewModel = getSamplesViewModel(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_sample.layoutManager = layoutManager
        mSamplesAdapter = SamplesAdapter(R.layout.item_sample, mSampleList)
        mSamplesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_sample.adapter = mSamplesAdapter
        mSamplesAdapter.setOnItemClickListener { _, _, position ->
            navigateToCRFActivity(mSampleList[position].sampleId)
        }
        mSamplesAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.btn_sample_edit -> {
                    if (mSampleList[position].submitStatus != 2) {
                        val sample = mSampleList[position]
                        val sampleBody =
                            SampleEditBodyBean(
                                sample.date,
                                sample.groupId,
                                sample.idNum,
                                sample.inGroupTime,
                                sample.patientIds,
                                sample.patientName,
//                            sample.projectId,
                                sample.researchCenterId,
                                sample.sampleId,
                                if (sample.sex == "男") 0 else 1,
                                sample.signTime
                            )
                        navigateToSampleEditActivity(mSampleList[position].sampleId, sampleBody)
                    }
                }
                R.id.btn_sample_submit -> {
                    if (mSampleList[position].submitStatus != 2) {
                        XPopup.Builder(this).asConfirm("信息", "请问是否确认提交到总中心") {
                            submitSample(mSampleList[position].sampleId)
                        }.show()
                    }
                }
                R.id.iv_delete_item_sample -> {
                    if (mSampleList[position].submitStatus == 2) {
                        toast("已全部提交，不能删除！")
                    } else {
                        XPopup.Builder(this).asConfirm("信息", "请问是否确认删除") {
                            deleteSample(mSampleList[position].sampleId, position)
                        }.show()
                    }
                }
                R.id.btn_sample_unlock -> {
                    if (judgeUnlockSample() &&
                        (mSampleList[position].submitStatus == 1 || mSampleList[position].submitStatus == 2)
                    ) {
                        XPopup.Builder(this)
                            .asConfirm("信息", "请问是否确认解锁${mSampleList[position].patientIds}号样本") {
                                unlockSample(mSampleList[position].sampleId)
                            }.show()
                    }
                }
            }
        }

        mViewModel.fetchLoadStatus().observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_sample.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_sample.finishRefresh()
                }
                Status.FAILED -> {
//                    Log.d(TAG, "获取失败")
//                    reLogin(this)
                    toast(it.msg.toString())
                    Log.d(TAG, it.msg.toString())
                }
            }
        })
        srl_sample.autoRefresh()
        srl_sample.setOnRefreshListener {
            loadInitial()
        }
        srl_sample.setOnLoadMoreListener {
            loadMore()
        }
    }

    private fun loadInitial() {
        mViewModel.fetchData().observe(this, Observer {
            mSampleList = it
            if (mSampleList.size == 0)
                Log.d(TAG, "样本为空")
            mSamplesAdapter.setNewData(mSampleList)
        })
    }

    private fun loadMore() {
        mViewModel.fetchMore().observe(this, Observer {
            mSamplesAdapter.addData(it)
            srl_sample.finishLoadMore()
        })
    }

    private fun navigateToSampleEditActivity(sampleId: Int, sampleBody: SampleEditBodyBean?) {
        val intent = Intent(this, SampleEditActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        if (sampleId > 0) {
            intent.putExtra(SAMPLE_BODY, sampleBody)
        }
        startActivity(intent)
    }

    private fun navigateToCRFActivity(sampleId: Int) {
        val intent = Intent(this, CRFActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        startActivity(intent)
    }

    private fun submitSample(sampleId: Int) {
        mViewModel.submitSample(
            SampleSubmitBodyBean(
                sampleId
            )
        ).observe(this, Observer {
            if (it.code == 200) {
                toast("样本提交成功")
                srl_sample.autoRefresh()
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast("样本提交失败")
            }
        })
    }

    private fun deleteSample(sampleId: Int, pos: Int) {
        mViewModel.deleteSample(sampleId).observe(this, Observer {
            if (it.code == 200) {
                toast("样本删除成功")
                mSamplesAdapter.remove(pos)
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast("样本删除失败")
            }
        })
    }

    private fun unlockSample(sampleId: Int) {
        mViewModel.unlockSample(sampleId).observe(this, Observer {
            if (it.code == 200) {
                toast("样本解锁成功")
                srl_sample.autoRefresh()
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast("样本解锁失败")
            }
        })
    }

    private fun initRefresh() {
        srl_sample.setEnableRefresh(true)
        srl_sample.setEnableAutoLoadMore(true)
        srl_sample.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_sample.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_sample.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_sample.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_sample.setEnableLoadMoreWhenContentNotFull(true)
    }
}


