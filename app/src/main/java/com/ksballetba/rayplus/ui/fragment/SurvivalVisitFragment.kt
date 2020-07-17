package com.ksballetba.rayplus.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.survivalVisitData.SurvivalVisitListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.LoginActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.survival_visit_activity.SurvivalVisitActivity
import com.ksballetba.rayplus.ui.adapter.SurvivalVisitAdapter
import com.ksballetba.rayplus.util.getSurvivalVisitViewModel
import com.ksballetba.rayplus.viewmodel.SurvivalVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_survival_visit.*

/**
 * A simple [Fragment] subclass.
 */
class SurvivalVisitFragment : Fragment() {

    companion object {
        const val TAG = "SurvivalVisitFragment"
        const val SURVIVAL_VISIT_BODY = "SURVIVAL_VISIT_BODY"
    }

    private lateinit var mViewModel: SurvivalVisitViewModel
    private lateinit var mAdapter: SurvivalVisitAdapter
    var mList = mutableListOf<SurvivalVisitListBean.Data>()
    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survival_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefresh()
        initData()
        initUI()
        initList()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getSurvivalVisitViewModel(this)
    }

    private fun initUI() {
        fab_add_survival_visit.setOnClickListener {
            navigateToSurvivalVisitEditPage(mSampleId, null)
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_survival_visit.layoutManager = layoutManager
        mAdapter = SurvivalVisitAdapter(R.layout.item_survival_visit, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_survival_visit)
        val view = layoutInflater.inflate(R.layout.empty, null)
        mAdapter.emptyView = view
        mAdapter.setOnItemClickListener { _, _, position ->
            val survivalVisit = mList[position]
            if (survivalVisit.isSubmit == 1) {
                ToastUtils.showShort("已提交！不能再编辑")
            } else {
                val survivalVisitBody =
                    SurvivalVisitBodyBean(
                        survivalVisit.dieReason,
                        survivalVisit.dieTime,
                        survivalVisit.hasOtherTreatment,
                        survivalVisit.interviewId,
                        survivalVisit.interviewTime,
                        survivalVisit.interviewWay,
                        survivalVisit.lastTimeSurvival,
                        survivalVisit.oSMethod,
                        survivalVisit.otherMethod,
                        survivalVisit.otherReason,
                        survivalVisit.statusConfirmTime,
                        survivalVisit.survivalStatus,
                        survivalVisit.isSubmit
                    )
                navigateToSurvivalVisitEditPage(
                    mSampleId,
                    survivalVisitBody
                )
            }
        }
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.iv_delete_item_survival_visit -> deleteListener(position)
                R.id.submit_button -> submitButtonListener(position)
            }
        }

        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_survival_visit.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_survival_visit.finishRefresh()
                }
                Status.FAILED -> {
                    ToastUtils.showShort(it.msg)
                }
            }
        })
        srl_survival_visit.autoRefresh()
        srl_survival_visit.setOnRefreshListener {
            loadData()
        }
    }

    fun loadData() {
        mViewModel.getSurvivalVisitList(mSampleId)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mAdapter.setNewData(mList)
            })
    }


    private fun navigateToSurvivalVisitEditPage(
        sampleId: Int,
        survivalVisitBodyBean: SurvivalVisitBodyBean?
    ) {
        val intent = Intent(activity, SurvivalVisitActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        if (survivalVisitBodyBean?.interviewId != null) {
            intent.putExtra(SURVIVAL_VISIT_BODY, survivalVisitBodyBean)
        }
        startActivity(intent)
    }

    private fun deleteListener(pos: Int) {
        if (mList[pos].isSubmit == 0) {    //未提交
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteSurvivalVisit(mList[pos].interviewId, pos)
            }.show()
        } else {
            ToastUtils.showShort("已提交！不能再进行修改")
        }
    }

    private fun deleteSurvivalVisit(interviewId: Int, pos: Int) {
        mViewModel.deleteSurvivalVisit(mSampleId, interviewId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("删除成功")
                        mAdapter.remove(pos)
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
    }

    private fun submitButtonListener(pos: Int) {
        val survivalVisitListBean = mList[pos]
        if (survivalVisitListBean.isSubmit == 0) {    //未提交
            XPopup.Builder(context).asConfirm("信息", "请问是否确认提交") {
                submitSurvivalVisit(survivalVisitListBean.interviewId)
            }.show()
        } else {
            ToastUtils.showShort("已提交！不能再修改")
        }
    }

    private fun submitSurvivalVisit(interviewId: Int) {
        mViewModel.submitSurvivalVisit(interviewId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("提交成功")
                        initList()
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
    }

    private fun initRefresh() {
        srl_survival_visit.setEnableRefresh(true)
        srl_survival_visit.setEnableLoadMore(false)
        srl_survival_visit.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_survival_visit.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_survival_visit.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_survival_visit.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_survival_visit.setEnableLoadMoreWhenContentNotFull(true)
    }

    private fun invalidToken() {

        XPopup.Builder(context).asLoading("操作失败！请尝试重新登录...").show()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
