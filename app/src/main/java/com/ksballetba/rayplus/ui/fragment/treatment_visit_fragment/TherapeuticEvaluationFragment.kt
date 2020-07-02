package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.TherapeuticEvaluationBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getTherapeuticEvaluationList
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_therapeutic_evaluation.*

/**
 * A simple [Fragment] subclass.
 */
class TherapeuticEvaluationFragment : Fragment() {

    private lateinit var mViewModel: TreatmentVisitViewModel
    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_therapeutic_evaluation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        initUI()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getTreatmentVisitViewModel(this)
    }

    private fun initUI() {
        cl_therapeutic_evaluation.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "疗效评价", getTherapeuticEvaluationList()

            ) { pos, text ->
                tv_therapeutic_evaluation.text = text
            }.show()
        }
        fab_save_therapeutic_evaluation.setOnClickListener {
            saveData()
        }
    }

    private fun loadData() {
        mViewModel.getTherapeuticEvaluation(mSampleId, mCycleNumber).observe(viewLifecycleOwner,
            Observer {
                if (it.data.evaluation != null) {
                    tv_therapeutic_evaluation.text =
                        getTherapeuticEvaluationList()[it.data.evaluation]
                }
            })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun saveData() {
        val evaluation = getTherapeuticEvaluationList().indexOf(tv_therapeutic_evaluation.text)
        val therapeuticEvaluation =
            TherapeuticEvaluationBean.Data(if (evaluation > 0) evaluation.toInt() else 4)
        mViewModel.editTherapeuticEvaluation(mSampleId, mCycleNumber, therapeuticEvaluation)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("疗效评价修改成功")
                    } else {
                        ToastUtils.showShort("疗效评价修改失败")
                    }
                })
    }
}
