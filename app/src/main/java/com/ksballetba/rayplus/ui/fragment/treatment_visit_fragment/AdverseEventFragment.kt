package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


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
import com.ksballetba.rayplus.data.bean.AdverseEventBodyBean
import com.ksballetba.rayplus.data.bean.AdverseEventListBean
import com.ksballetba.rayplus.data.bean.TreatmentRecordListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.AdverseEventActivity
import com.ksballetba.rayplus.ui.adapter.AdverseEventAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getAdverseEventMeasure
import com.ksballetba.rayplus.util.getAdverseEventMedicineRelation
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_adverse_event.*

/**
 * A simple [Fragment] subclass.
 */
class AdverseEventFragment : Fragment() {

    companion object {
        const val TAG = "AdverseEventFragment"
        const val ADVERSE_EVENT_BODY = "ADVERSE_EVENT_BODY"
    }

    private lateinit var mViewModel: TreatmentVisitViewModel
    private lateinit var mAdapter: AdverseEventAdapter
    var mList = mutableListOf<AdverseEventListBean.Data>()
    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adverse_event, container, false)
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
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getTreatmentVisitViewModel(this)
    }

    private fun initUI() {
        fab_add_adverse_event.setOnClickListener {
            navigateToAdverseEventEditPage(mSampleId, mCycleNumber, null)
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_adverse_event.layoutManager = layoutManager
        mAdapter = AdverseEventAdapter(R.layout.item_adverse_event, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_adverse_event)
        mAdapter.setOnItemClickListener { _, _, position ->
            val adverseEvent = mList[position]
            val adverseEventBody = AdverseEventBodyBean(
                adverseEvent.adverseEventId,
                adverseEvent.adverseEventName,
                adverseEvent.dieTime,
                if(adverseEvent.isServerEvent=="严重不良事件") 1 else 0,
                if(adverseEvent.isUsingMedicine) 1 else 0,
                getAdverseEventMeasure().indexOf(adverseEvent.measure),
                adverseEvent.medicineMeasure,
                getAdverseEventMedicineRelation().indexOf(adverseEvent.medicineRelation),
                adverseEvent.otherSAEState,
                adverseEvent.recover,
                adverseEvent.recoverTime,
                adverseEvent.reportTime,
                adverseEvent.reportType,
                adverseEvent.sAEDiagnose,
                adverseEvent.sAERecover,
                adverseEvent.sAERelations,
                adverseEvent.sAEStartTime,
                adverseEvent.sAEState,
                adverseEvent.startTime,
                adverseEvent.toxicityClassification
            )
            navigateToAdverseEventEditPage(
                mSampleId,
                mCycleNumber,
                adverseEventBody
            )
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteAdverseEvent(mList[position].adverseEventId, position)
            }.show()
        }
    }

    fun loadData() {
        mViewModel.getAdverseEventList(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mList.forEach {item->
                    item.needDeleted = true
                }
                mAdapter.setNewData(mList)
            })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if (it.status == Status.FAILED) {
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun navigateToAdverseEventEditPage(
        sampleId: Int,
        cycleNumber: Int,
        adverseEventBody: AdverseEventBodyBean?
    ) {
        val intent = Intent(activity, AdverseEventActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(CYCLE_NUMBER_KEY, cycleNumber)
        if (adverseEventBody?.adverseEventId != null) {
            intent.putExtra(ADVERSE_EVENT_BODY, adverseEventBody)
        }
        startActivity(intent)
    }

    private fun deleteAdverseEvent(adverseEventId: Int, pos: Int) {
        mViewModel.deleteAdverseEvent(mSampleId, mCycleNumber, adverseEventId)
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
