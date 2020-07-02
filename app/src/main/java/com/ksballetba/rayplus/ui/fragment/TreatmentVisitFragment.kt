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
import com.ksballetba.rayplus.data.bean.treatmentVisitData.NavigationBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitShowDataBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentVisitSubmitResponseBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.adapter.VisitsAdapter
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_treatment_visit.*

/**
 * A simple [Fragment] subclass.
 */
class TreatmentVisitFragment : Fragment() {

    companion object {
        const val TREATMENT_CYCLE_NUMBER_KEY = "TREATMENT_CYCLE_NUMBER_KEY"
        const val VISIT_TITLE = "VISIT_TITLE"
    }

    private lateinit var mViewModel: TreatmentVisitViewModel
    private lateinit var mVisitsAdapter: VisitsAdapter
    var mVisitList = mutableListOf<TreatmentVisitShowDataBean>()
    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefresh()
        initUI()
        initRecyclerView()
    }

    private fun initUI() {
        fab_add_treatment_visit.setOnClickListener {
            addCycle()
        }
        fab_delete_treatment_visit.setOnClickListener {
            deleteCycle()
        }
    }

    private fun initRecyclerView() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getTreatmentVisitViewModel(this)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_treatment_visit.layoutManager = layoutManager
        mVisitsAdapter = VisitsAdapter(R.layout.item_treatment_visit, mVisitList)
        mVisitsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mVisitsAdapter.bindToRecyclerView(rv_treatment_visit)
        mVisitsAdapter.setOnItemClickListener { _, _, position ->
            navigateToDetailPage(
                mVisitList[position].data.cycleNumber,
                mVisitList[position].data.title
            )
        }
        mVisitsAdapter.setOnItemChildClickListener { _, _, position ->
            if (mVisitList[position].submitStatus == 0) {
                XPopup.Builder(context).asConfirm("信息", "请问是否确定提交，确定后将不能修改") {
                    submitCycle(mVisitList[position].data.cycleNumber)
                }.show()
            } else
                ToastUtils.showShort("已提交！")

        }
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_treatment_visit.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_treatment_visit.finishRefresh()
                }
                Status.FAILED -> {
                    ToastUtils.showShort(it.msg)
                }
            }
        })
        srl_treatment_visit.autoRefresh()
        srl_treatment_visit.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        //TODO
        var dataList = mutableListOf<NavigationBean.Data>()
        var submitStatusList = mutableListOf<TreatmentVisitSubmitResponseBean.Data>()
        var map = mapOf<Int, Int>()
        mViewModel.getNavigation(mSampleId).observe(viewLifecycleOwner, Observer {
            dataList = it.toMutableList()
            mVisitList.clear()
            dataList.forEach {
                val treatmentVisitShowDataBean =
                    TreatmentVisitShowDataBean(
                        it,
                        map.get(it.cycleNumber) ?: 0
                    )
                mVisitList.add(treatmentVisitShowDataBean)
            }
            mVisitsAdapter.setNewData(mVisitList)
        })
        mViewModel.getSubmitStatus(mSampleId).observe(viewLifecycleOwner, Observer {
            submitStatusList = it.toMutableList()
            map = submitStatusList.associateBy({ it.cycleNumber }, { it.submitStatus })
        })

    }

    private fun addCycle() {
        mViewModel.addCycle(mSampleId).observe(viewLifecycleOwner, Observer {
            if (it.code == 200) {
                ToastUtils.showShort("添加治疗期随访成功")
                srl_treatment_visit.autoRefresh()
            } else {
                ToastUtils.showShort("添加治疗期随访失败")
            }
        })
    }

    private fun deleteCycle() {
        mViewModel.deleteCycle(mSampleId).observe(viewLifecycleOwner, Observer {
            if (it.code == 200) {
                ToastUtils.showShort("删除治疗期随访成功")
                srl_treatment_visit.autoRefresh()
            } else {
                ToastUtils.showShort("删除治疗期随访失败")
            }
        })
    }

    private fun submitCycle(cycleNumber: Int) {
        mViewModel.submitCycle(mSampleId, cycleNumber).observe(viewLifecycleOwner, Observer {
            if (it.code == 200) {
                srl_treatment_visit.autoRefresh()
                ToastUtils.showShort("提交治疗期随访成功")
                //TODO
            } else {
                ToastUtils.showShort("提交治疗期随访失败")
            }
        })
    }

    private fun navigateToDetailPage(cycleNumber: Int, title: String) {
        val intent = Intent(activity, TreatmentVisitDetailActivity::class.java)
        intent.putExtra(TREATMENT_CYCLE_NUMBER_KEY, cycleNumber)
        intent.putExtra(VISIT_TITLE, title)
        intent.putExtra(SAMPLE_ID, mSampleId)
        activity?.startActivity(intent)
    }

    private fun initRefresh() {
        srl_treatment_visit.setEnableRefresh(true)
        srl_treatment_visit.setEnableLoadMore(false)
        srl_treatment_visit.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_treatment_visit.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_treatment_visit.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_treatment_visit.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_treatment_visit.setEnableLoadMoreWhenContentNotFull(true)
    }
}
