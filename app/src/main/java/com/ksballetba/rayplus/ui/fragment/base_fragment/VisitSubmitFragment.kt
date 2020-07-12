package com.ksballetba.rayplus.ui.fragment.base_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.getSubmitRemind
import com.ksballetba.rayplus.util.getSurvivalSubmitStatus
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import kotlinx.android.synthetic.main.fragment_visit_submit.*

class VisitSubmitFragment : Fragment() {

    private lateinit var mViewModel: BaseVisitViewModel

    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visit_submit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        getSubmitStatus()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SampleActivity.SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        Log.d("hello", mCycleNumber.toString())
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun initUI() {
        btn_baseline_submit.setOnClickListener {
            submitCycle()
        }
    }

    private fun getSubmitStatus() {
        mViewModel.getSubmitStatus(mSampleId).observe(viewLifecycleOwner, Observer { submitStatus ->
            val submitStatusList = submitStatus.toMutableList()
            val submitMap = submitStatusList.associateBy({ it.cycleNumber }, { it })
            val status = submitMap[mCycleNumber]?.submitStatus ?: 0
            baseline_submit_status.text = getSurvivalSubmitStatus()[status]
            remind.text = getSubmitRemind()[status]
            btn_baseline_submit.isVisible = status == 0
            if (status == 0) remind_picture.setImageResource(R.drawable.remind)
            else remind_picture.setImageResource(R.drawable.success)
        })
    }

    private fun submitCycle() {
        mViewModel.submitBaseline(mSampleId, mCycleNumber).observe(viewLifecycleOwner, Observer {
            if (it.code == 200) {
                ToastUtils.showShort("提交成功")
                baseline_submit_status.text = getSurvivalSubmitStatus()[1]
                remind.text = getSubmitRemind()[1]
                remind_picture.setImageResource(R.drawable.success)
                btn_baseline_submit.isVisible = false
            } else {
                ToastUtils.showShort(it.msg)
            }
        })
    }
}