package com.ksballetba.rayplus.ui.fragment.project_summary


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.AdverseEventBodyBean
import com.ksballetba.rayplus.data.bean.AdverseEventListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.AdverseEventActivity
import com.ksballetba.rayplus.ui.adapter.AdverseEventAdapter
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.AdverseEventFragment.Companion.ADVERSE_EVENT_BODY
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.AdverseEventFragment.Companion.FRAGMENT_FLAG
import com.ksballetba.rayplus.util.getAdverseEventMeasure
import com.ksballetba.rayplus.util.getAdverseEventMedicineRelation
import com.ksballetba.rayplus.util.getAdverseEventServer
import com.ksballetba.rayplus.util.getProjectSummaryViewModel
import com.ksballetba.rayplus.viewmodel.ProjectSummaryViewModel
import kotlinx.android.synthetic.main.fragment_adverse_event_summary.*

/**
 * A simple [Fragment] subclass.
 */
class AdverseEventSummaryFragment : Fragment() {

    companion object {
        const val ADVERSE_EVENT_SUMMARY_FRAGMENT = "ADVERSE_EVENT_SUMMARY_FRAGMENT"
    }

    private lateinit var mViewModel: ProjectSummaryViewModel
    private lateinit var mAdapter: AdverseEventAdapter
    var mList = mutableListOf<AdverseEventListBean.Data?>()
    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adverse_event_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefresh()
        initData()
        initList()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getProjectSummaryViewModel(this)
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_adverse_event_summary.layoutManager = layoutManager
        mAdapter = AdverseEventAdapter(R.layout.item_adverse_event, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_adverse_event_summary)
        val view = layoutInflater.inflate(R.layout.empty, null)
        mAdapter.emptyView = view
        mAdapter.setOnItemClickListener { _, _, position ->
            val adverseEvent = mList[position]
            val adverseEventBody = AdverseEventBodyBean(
                adverseEvent?.adverseEventId,
                adverseEvent?.adverseEventName,
                adverseEvent?.dieTime,
                getAdverseEventServer().indexOf(adverseEvent?.isServerEvent),
                adverseEvent?.isUsingMedicine,
                getAdverseEventMeasure().indexOf(adverseEvent?.measure),
                adverseEvent?.medicineMeasure,
                getAdverseEventMedicineRelation().indexOf(adverseEvent?.medicineRelation),
                adverseEvent?.otherSAEState,
                adverseEvent?.recover,
                adverseEvent?.recoverTime,
                adverseEvent?.reportTime,
                adverseEvent?.reportType,
                adverseEvent?.sAEDiagnose,
                adverseEvent?.sAERecover,
                adverseEvent?.sAERelations,
                adverseEvent?.sAEStartTime,
                adverseEvent?.sAEState,
                adverseEvent?.startTime,
                adverseEvent?.toxicityClassification
            )
            navigateToAdverseEventEditPage(adverseEventBody)
        }
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_adverse_event_summary.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_adverse_event_summary.finishRefresh()
                }
                Status.FAILED -> {
                    ToastUtils.showShort(it.msg)
                }
            }
        })
        srl_adverse_event_summary.autoRefresh()
        srl_adverse_event_summary.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        mViewModel.getAllAdverseEventList(mSampleId)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mList.forEach { item ->
                    item?.needDeleted = false
                }
                mAdapter.setNewData(mList)
            })
    }

    private fun navigateToAdverseEventEditPage(adverseEventBody: AdverseEventBodyBean?) {
        val intent = Intent(activity, AdverseEventActivity::class.java)
        if (adverseEventBody?.adverseEventId != null) {
            intent.putExtra(ADVERSE_EVENT_BODY, adverseEventBody)
        }
        intent.putExtra(FRAGMENT_FLAG, ADVERSE_EVENT_SUMMARY_FRAGMENT)
        startActivity(intent)
    }

    private fun initRefresh() {
        srl_adverse_event_summary.setEnableRefresh(true)
        srl_adverse_event_summary.setEnableLoadMore(false)
        srl_adverse_event_summary.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_adverse_event_summary.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_adverse_event_summary.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_adverse_event_summary.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_adverse_event_summary.setEnableLoadMoreWhenContentNotFull(true)
    }

}
