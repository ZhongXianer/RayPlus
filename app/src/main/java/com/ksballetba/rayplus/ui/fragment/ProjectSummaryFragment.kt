package com.ksballetba.rayplus.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.adapter.ViewPagerAdapter
import com.ksballetba.rayplus.ui.fragment.base_fragment.VisitTimeFragment
import com.ksballetba.rayplus.ui.fragment.project_summary.AdverseEventSummaryFragment
import com.ksballetba.rayplus.ui.fragment.project_summary.ProjectSummaryDetailFragment
import kotlinx.android.synthetic.main.fragment_project_summary.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectSummaryFragment : Fragment() {

    private val mFragmentList = mutableListOf<Fragment>()
    lateinit var mVisitTimeFragment: VisitTimeFragment

    var mSampleId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragments()
    }

    private fun initFragments(){
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        val sampleIdArgs = Bundle()
        sampleIdArgs.putInt(SAMPLE_ID, mSampleId)
        val projectSummaryDetailFragment = ProjectSummaryDetailFragment()
        val adverseEventSummaryFragment = AdverseEventSummaryFragment()
        projectSummaryDetailFragment.arguments = sampleIdArgs
        adverseEventSummaryFragment.arguments = sampleIdArgs
        mFragmentList.add(projectSummaryDetailFragment)
        mFragmentList.add(adverseEventSummaryFragment)
        vp_project_summary.adapter = ViewPagerAdapter(mFragmentList,childFragmentManager)
        vp_project_summary.offscreenPageLimit = 3
        tl_project_summary.setupWithViewPager(vp_project_summary)
        tl_project_summary.getTabAt(0)?.text = "项目总结"
        tl_project_summary.getTabAt(1)?.text = "不良事件总结"
    }
}
