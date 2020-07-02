package com.ksballetba.rayplus.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.adapter.ViewPagerAdapter
import com.ksballetba.rayplus.ui.fragment.base_fragment.*
import com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment.*
import kotlinx.android.synthetic.main.fragment_baseline_visit.*

/**
 * A simple [Fragment] subclass.
 */
class BaselineVisitFragment : Fragment() {

    companion object {
        const val BASELINE_CYCLE_NUMBER = 1
        const val CYCLE_NUMBER_KEY = "CYCLE_NUMBER_KEY"
    }

    lateinit var mViewPagerAdapter: ViewPagerAdapter

    private val mFragmentList = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baseline_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragments()
    }

    private fun initFragments() {
        val sampleId = if (arguments != null) (arguments as Bundle).getInt(SAMPLE_ID) else 0
        val baselineArgs = Bundle()
        baselineArgs.putInt(CYCLE_NUMBER_KEY, BASELINE_CYCLE_NUMBER)
        baselineArgs.putInt(SAMPLE_ID, sampleId)
        val visitTimeFragment = VisitTimeFragment()
        val demographicsFragment = DemographicsFragment()
        val physicalExaminationFragment = PhysicalExaminationFragment()
        val previousHistoryFragment = PreviousHistoryFragment()
        val firstVisitProcessFragment = FirstVisitProcessFragment()
        val treatmentHistoryFragment = TreatmentHistoryFragment()
        val labInspectionFragment = LabInspectionFragment()
        val imagingEvaluationFragment = ImagingEvaluationFragment()
        val visitSubmitFragment = VisitSubmitFragment()
        visitTimeFragment.arguments = baselineArgs
        demographicsFragment.arguments = baselineArgs
        physicalExaminationFragment.arguments = baselineArgs
        previousHistoryFragment.arguments = baselineArgs
        firstVisitProcessFragment.arguments = baselineArgs
        treatmentHistoryFragment.arguments = baselineArgs
        labInspectionFragment.arguments = baselineArgs
        imagingEvaluationFragment.arguments = baselineArgs
        visitSubmitFragment.arguments = baselineArgs
        mFragmentList.add(visitTimeFragment)
        mFragmentList.add(demographicsFragment)
        mFragmentList.add(physicalExaminationFragment)
        mFragmentList.add(previousHistoryFragment)
        mFragmentList.add(firstVisitProcessFragment)
        mFragmentList.add(treatmentHistoryFragment)
        mFragmentList.add(labInspectionFragment)
        mFragmentList.add(imagingEvaluationFragment)
        mFragmentList.add(InvestigatorSignatureFragment())
        mFragmentList.add(visitSubmitFragment)
        mViewPagerAdapter = ViewPagerAdapter(mFragmentList, childFragmentManager)
        vp_baseline_visit.adapter = mViewPagerAdapter
        vp_baseline_visit.offscreenPageLimit = 3
        tl_baseline_visit.setupWithViewPager(vp_baseline_visit)
        tl_baseline_visit.getTabAt(0)?.text = "访视时间"
        tl_baseline_visit.getTabAt(1)?.text = "人口统计学"
        tl_baseline_visit.getTabAt(2)?.text = "体格检查"
        tl_baseline_visit.getTabAt(3)?.text = "既往史"
        tl_baseline_visit.getTabAt(4)?.text = "初诊过程"
        tl_baseline_visit.getTabAt(5)?.text = "治疗史"
        tl_baseline_visit.getTabAt(6)?.text = "实验室检查"
        tl_baseline_visit.getTabAt(7)?.text = "影像学评估"
        tl_baseline_visit.getTabAt(8)?.text = "研究者签字"
        tl_baseline_visit.getTabAt(9)?.text = "访视提交"
    }

}
