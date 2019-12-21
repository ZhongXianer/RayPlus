package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.ImagingEvaluationBodyBean
import com.ksballetba.rayplus.data.bean.ImagingEvaluationListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.ImagingEvaluationActivity
import com.ksballetba.rayplus.ui.adapter.ImagingEvaluationAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.BASELINE_CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_imaging_evaluation.*

/**
 * A simple [Fragment] subclass.
 */
class ImagingEvaluationFragment : Fragment() {

    companion object {
        const val TAG = "ImagingEvaluationFragment"
        const val IMAGING_EVALUATION_BODY = "IMAGING_EVALUATION_BODY"
    }

    private lateinit var mViewModel: BaseVisitViewModel
    private lateinit var mAdapter: ImagingEvaluationAdapter
    var mList = mutableListOf<ImagingEvaluationListBean.Data>()
    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imaging_evaluation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initUI() {
        fab_imaging_evaluation.setOnClickListener {
            navigateToImagingEvaluationEditPage(mSampleId, mCycleNumber, null)
        }
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(BASELINE_CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_imaging_evaluation.layoutManager = layoutManager
        mAdapter = ImagingEvaluationAdapter(R.layout.item_imaging_evaluation, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_imaging_evaluation)
        mAdapter.setOnItemClickListener { _, _, position ->
            val imagingEvaluation = mList[position]
            val imagingEvaluationBody = ImagingEvaluationBodyBean(
                imagingEvaluation.evaluateId,
                imagingEvaluation.method,
                imagingEvaluation.method,
                imagingEvaluation.part,
                imagingEvaluation.time,
                imagingEvaluation.tumorLong,
                imagingEvaluation.tumorShort
            )
            navigateToImagingEvaluationEditPage(
                mSampleId,
                mCycleNumber,
                imagingEvaluationBody
            )
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteImagingEvaluation(mList[position].evaluateId, position)
            }.show()

        }
    }

    fun loadData() {
        mViewModel.getImagingEvaluationList(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                mList = it
                mAdapter.setNewData(mList)
            })
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                ToastUtils.showShort(it.msg)
            }
        })
    }

    private fun navigateToImagingEvaluationEditPage(
        sampleId: Int,
        cycleNumber: Int,
        imagingEvaluationBodyBean: ImagingEvaluationBodyBean?
    ) {
        val intent = Intent(activity, ImagingEvaluationActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(BASELINE_CYCLE_NUMBER_KEY, cycleNumber)
        if (imagingEvaluationBodyBean?.evaluateId != null) {
            intent.putExtra(IMAGING_EVALUATION_BODY, imagingEvaluationBodyBean)
        }
        startActivity(intent)
    }

    private fun deleteImagingEvaluation(evaluateId: Int, pos: Int) {
        mViewModel.deleteImagingEvaluation(mSampleId, mCycleNumber, evaluateId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("删除成功")
                        mAdapter.remove(pos)
                        LogUtils.tag(TAG).d(mList)
                    } else {
                        ToastUtils.showShort("删除失败")
                    }
                })
    }

}
