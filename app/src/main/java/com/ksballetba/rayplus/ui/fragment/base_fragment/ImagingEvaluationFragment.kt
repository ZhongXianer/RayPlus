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
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationBodyBean
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationListBean
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.ImagingEvaluationActivity
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.ImagingEvaluationFileActivity
import com.ksballetba.rayplus.ui.adapter.ImagingEvaluationAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
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
        const val REFRESH_PAGE = "REFRESH_PAGE"
        const val CRF_PAGE = "CRF_PAGE"
        const val TREATMENT_VISIT_DETAIL_PAGE = "TREATMENT_VISIT_DETAIL_PAGE"
        const val EVALUATE_ID = "EVALUATE_ID"
    }

    private lateinit var mViewModel: BaseVisitViewModel
    private lateinit var mAdapter: ImagingEvaluationAdapter
    private var mList = mutableListOf<ImagingEvaluationListBean.Data>()
    var mSampleId = 0
    private var mCycleNumber = 0
    private var mCurrentPage = ""

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
            navigateToImagingEvaluationEditPage(mSampleId, mCycleNumber, mCurrentPage, null)
        }
        judge_evaluation_switch.setOnCheckedChangeListener { _, isChecked ->
            rv_imaging_evaluation.visibility = if (isChecked) View.VISIBLE else View.GONE
            fab_imaging_evaluation.visibility = if (isChecked) View.VISIBLE else View.GONE
            val isPhotoEvaluate: Int = if (judge_evaluation_switch.isChecked) 1 else 0
            if (isPhotoEvaluate == 1)
                editIsImagingEvaluate(isPhotoEvaluate)
        }
        judge_evaluation_save_button.setOnClickListener {
            val isPhotoEvaluate: Int = if (judge_evaluation_switch.isChecked) 1 else 0
            editIsImagingEvaluate(isPhotoEvaluate)
        }
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
        when (activity) {
            is CRFActivity -> {
                mCurrentPage = CRF_PAGE
            }
            is TreatmentVisitDetailActivity -> {
                mCurrentPage = TREATMENT_VISIT_DETAIL_PAGE
            }
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_imaging_evaluation.layoutManager = layoutManager
        mAdapter = ImagingEvaluationAdapter(R.layout.item_imaging_evaluation, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_imaging_evaluation)
        val view = layoutInflater.inflate(R.layout.empty, null)
        mAdapter.emptyView = view
        mAdapter.setOnItemClickListener { _, _, position ->
            val imagingEvaluation = mList[position]
            val imagingEvaluationBody =
                ImagingEvaluationBodyBean(
                    imagingEvaluation.evaluateId,
                    imagingEvaluation.method,
                    imagingEvaluation.method,
                    imagingEvaluation.part,
                    imagingEvaluation.time,
                    imagingEvaluation.tumorDesc,
                    imagingEvaluation.tumorLong?.toString(),
                    imagingEvaluation.tumorShort?.toString()
                )
            navigateToImagingEvaluationEditPage(
                mSampleId,
                mCycleNumber,
                mCurrentPage,
                imagingEvaluationBody
            )
        }
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.iv_delete_item_imaging_evaluation -> {
                    XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                        deleteImagingEvaluation(mList[position].evaluateId, position)
                    }.show()
                }
                R.id.file_btn -> {
                    navigateToFilePage(
                        mSampleId,
                        mCycleNumber,
                        mList[position].evaluateId,
                        mCurrentPage
                    )
                }
            }

        }
    }

    fun loadData() {
        mViewModel.getIsImagingEvaluate(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                if (it.data.is_photo_evaluate == 0) {
                    judge_evaluation_switch.isChecked = false
                    rv_imaging_evaluation.visibility = View.GONE
                    fab_imaging_evaluation.visibility = View.GONE
                } else judge_evaluation_switch.isChecked = true
            })
        mViewModel.getImagingEvaluationList(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                mList = it
                mAdapter.setNewData(mList)
            })
    }

    private fun navigateToImagingEvaluationEditPage(
        sampleId: Int,
        cycleNumber: Int,
        refreshPage: String,
        imagingEvaluationBodyBean: ImagingEvaluationBodyBean?
    ) {
        val intent = Intent(activity, ImagingEvaluationActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(CYCLE_NUMBER_KEY, cycleNumber)
        intent.putExtra(REFRESH_PAGE, refreshPage)
        if (imagingEvaluationBodyBean?.evaluateId != null) {
            intent.putExtra(IMAGING_EVALUATION_BODY, imagingEvaluationBodyBean)
        }
        startActivity(intent)
    }

    private fun navigateToFilePage(
        sampleId: Int,
        cycleNumber: Int,
        evaluateId: Int,
        refreshPage: String
    ) {
        val intent = Intent(activity, ImagingEvaluationFileActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(CYCLE_NUMBER_KEY, cycleNumber)
        intent.putExtra(EVALUATE_ID, evaluateId)
        intent.putExtra(REFRESH_PAGE, refreshPage)
        startActivity(intent)
    }

    private fun editIsImagingEvaluate(isPhotoEvaluate: Int) {
        mViewModel.editIsImagingEvaluate(mSampleId, mCycleNumber, isPhotoEvaluate)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("保存是否影像学检查成功！")
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
    }

    private fun deleteImagingEvaluation(evaluateId: Int, pos: Int) {
        mViewModel.deleteImagingEvaluation(mSampleId, mCycleNumber, evaluateId)
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

}
