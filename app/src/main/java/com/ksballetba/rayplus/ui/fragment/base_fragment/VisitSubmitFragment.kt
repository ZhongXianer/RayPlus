package com.ksballetba.rayplus.ui.fragment.base_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity
import com.ksballetba.rayplus.util.getBaselineVisitViewModel
import com.ksballetba.rayplus.util.getSubmitRemind
import com.ksballetba.rayplus.util.getSurvivalSubmitStatus
import com.ksballetba.rayplus.viewmodel.BaselineVisitViewModel
import kotlinx.android.synthetic.main.fragment_visit_submit.*

class VisitSubmitFragment : Fragment() {

    private lateinit var mViewModel: BaselineVisitViewModel

    var mSampleId = 0

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
        mViewModel = getBaselineVisitViewModel(this)
    }

    private fun initUI() {
        btn_baseline_submit.setOnClickListener {
            submitBaseLine()
        }
    }

    private fun getSubmitStatus() {
        mViewModel.getSubmitStatus(mSampleId).observe(viewLifecycleOwner, Observer {
            baseline_submit_status.text = getSurvivalSubmitStatus()[it[0].submitStatus]
            remind.text = getSubmitRemind()[it[0].submitStatus]
            btn_baseline_submit.isVisible = it[0].submitStatus == 0
            if (it[0].submitStatus == 0) remind_picture.setImageResource(R.drawable.remind)
            else remind_picture.setImageResource(R.drawable.success)
        })
    }

    private fun submitBaseLine() {
        mViewModel.submitBaseline(mSampleId).observe(viewLifecycleOwner, Observer {
            if (it.code == 200) {
                ToastUtils.showShort("提交基线资料成功")
                baseline_submit_status.text = getSurvivalSubmitStatus()[1]
                remind.text = getSubmitRemind()[1]
                remind_picture.setImageResource(R.drawable.success)
                btn_baseline_submit.isVisible = false
            } else {
                ToastUtils.showShort("提交基线资料失败")
            }
        })
    }
}