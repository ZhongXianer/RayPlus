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
import com.ksballetba.rayplus.data.bean.SurvivalVisitBodyBean
import com.ksballetba.rayplus.data.bean.SurvivalVisitListBean
import com.ksballetba.rayplus.network.Status
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
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getSurvivalVisitViewModel(this)
    }

    private fun initUI(){
        fab_add_survival_visit.setOnClickListener {
            navigateToSurvivalVisitEditPage(mSampleId,null)
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_survival_visit.layoutManager = layoutManager
        mAdapter = SurvivalVisitAdapter(R.layout.item_survival_visit, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_survival_visit)
        mAdapter.setOnItemClickListener { _, _, position ->
            val survivalVisit = mList[position]
            val survivalVisitBody = SurvivalVisitBodyBean(
                survivalVisit.dieReason,
                survivalVisit.dieTime,
                if(survivalVisit.hasOtherTreatment) 1 else 0,
                survivalVisit.interviewId,
                survivalVisit.interviewTime,
                survivalVisit.interviewWay,
                survivalVisit.lastTimeSurvival,
                survivalVisit.oSMethod,
                survivalVisit.otherMethod,
                survivalVisit.otherReason,
                survivalVisit.statusConfirmTime,
                survivalVisit.survivalStatus
            )
            navigateToSurvivalVisitEditPage(
                mSampleId,
                survivalVisitBody
            )
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteSurvivalVisit(mList[position].interviewId, position)
            }.show()
        }
    }

    fun loadData() {
        mViewModel.getSurvivalVisitList(mSampleId)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mAdapter.setNewData(mList)
            })
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                ToastUtils.showShort(it.msg)
            }
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

    private fun deleteSurvivalVisit(interviewId: Int, pos: Int) {
        mViewModel.deleteSurvivalVisit(mSampleId, interviewId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("删除成功")
                        mAdapter.remove(pos)
                    } else {
                        ToastUtils.showShort("删除失败")
                    }
                })
    }
}
