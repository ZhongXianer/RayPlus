package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


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
import com.ksballetba.rayplus.data.bean.baseLineData.TreatmentHistoryListBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.TreatmentHistoryActivity
import com.ksballetba.rayplus.ui.adapter.TreatmentHistoryAdapter
import com.ksballetba.rayplus.util.getBaselineVisitViewModel
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_treatment_history.*
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class TreatmentHistoryFragment : Fragment() {

    companion object {
        const val TAG = "TreatmentHistoryFragment"
        const val TREATMENT_HISTORY_BODY = "TREATMENT_HISTORY_BODY"
    }

    private lateinit var mViewModel: BaselineVisitViewModel
    private lateinit var mAdapter: TreatmentHistoryAdapter
    var mList = mutableListOf<TreatmentHistoryListBean.Data>()
    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initUI() {
        fab_treatment_history.setOnClickListener {
            navigateToTreatmentHistoryEditPage(mSampleId, null)
        }
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_treatment_history.layoutManager = layoutManager
        mAdapter = TreatmentHistoryAdapter(R.layout.item_treatment_history, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_treatment_history)
        val view = layoutInflater.inflate(R.layout.empty, null)
        mAdapter.emptyView = view
        mAdapter.setOnItemClickListener { _, _, position ->
            val treatmentHistory = mList[position]
            navigateToTreatmentHistoryEditPage(
                mSampleId,
                treatmentHistory
            )
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteTreatmentHistory(mList[position].id, position)
            }.show()

        }
    }

    fun loadData() {
        mViewModel.getTreatmentHistoryList(mSampleId).observe(viewLifecycleOwner, Observer {
            mList = it
            mAdapter.setNewData(mList)

        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }


    private fun navigateToTreatmentHistoryEditPage(
        sampleId: Int,
        treatmentHistoryBodyBean: TreatmentHistoryListBean.Data?
    ) {
        val intent = Intent(activity, TreatmentHistoryActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        if (treatmentHistoryBodyBean != null) {
            intent.putExtra(TREATMENT_HISTORY_BODY, treatmentHistoryBodyBean)
        }
        startActivity(intent)
    }

    private fun deleteTreatmentHistory(diagnoseNumber: Int, pos: Int) {
        mViewModel.deleteTreatmentHistory(mSampleId, diagnoseNumber).observe(viewLifecycleOwner,
            Observer {
                if (it.code == 200) {
                    ToastUtils.showShort("删除成功")
                    mAdapter.remove(pos)
                } else {
                    ToastUtils.showShort(it.msg)
                }
            })
    }
}
