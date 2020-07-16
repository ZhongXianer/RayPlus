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
import com.ksballetba.rayplus.data.bean.baseLineData.PhysicalExaminationBodyBean
import com.ksballetba.rayplus.data.bean.baseLineData.PhysicalExaminationListBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.PhysicalExaminationActivity
import com.ksballetba.rayplus.ui.adapter.PhysicalExaminationAdapter
import com.ksballetba.rayplus.util.getBaselineVisitViewModel
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_physical_examination.*

/**
 * A simple [Fragment] subclass.
 */
class PhysicalExaminationFragment : Fragment() {

    companion object {
        const val TAG = "PhysicalExaminationFragment"
        const val PHYSICAL_EXAMINATION_BODY = "PHYSICAL_EXAMINATION_BODY"
        const val REPORT_ID = "REPORT_ID"
    }

    private lateinit var mViewModel: BaselineVisitViewModel
    private lateinit var mAdapter: PhysicalExaminationAdapter
    var mList = mutableListOf<PhysicalExaminationListBean.Data>()
    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_physical_examination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initUI() {
        fab_physical_examination.setOnClickListener {
            navigateToPhysicalExaminationEditPage(mSampleId, -1, null)
        }
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_physical_examination.layoutManager = layoutManager
        mAdapter = PhysicalExaminationAdapter(R.layout.item_physical_examination, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_physical_examination.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val physicalExamination = mList[position]
            val physicalExaminationBody =
                PhysicalExaminationBodyBean(
                    physicalExamination.breathFrequency,
                    physicalExamination.heartRate,
                    physicalExamination.maxpressure,
                    physicalExamination.minpressure,
                    physicalExamination.reportId,
                    physicalExamination.temperature,
                    physicalExamination.time
                )
            navigateToPhysicalExaminationEditPage(
                mSampleId,
                mList[position].reportId,
                physicalExaminationBody
            )
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deletePhysicalExamination(mList[position].reportId, position)
            }.show()

        }
    }

    fun loadData() {
        mViewModel.getPhysicalExaminationList(mSampleId).observe(viewLifecycleOwner, Observer {
            mList = it
            mAdapter.setNewData(mList)
        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun navigateToPhysicalExaminationEditPage(
        sampleId: Int,
        reportId: Int,
        physicalExaminationBodyBean: PhysicalExaminationBodyBean?
    ) {
        val intent = Intent(activity, PhysicalExaminationActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(REPORT_ID, reportId)
        if (reportId > 0) {
            intent.putExtra(PHYSICAL_EXAMINATION_BODY, physicalExaminationBodyBean)
        }
        startActivity(intent)
    }

    private fun deletePhysicalExamination(reportId: Int, pos: Int) {
        mViewModel.deletePhysicalExamination(mSampleId, reportId).observe(viewLifecycleOwner,
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
