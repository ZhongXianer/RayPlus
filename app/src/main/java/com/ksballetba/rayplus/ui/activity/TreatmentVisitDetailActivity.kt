package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.ImagingEvaluationActivity.Companion.REFRESH_IMAGING_EVALUATION_PAGE
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.AdverseEventActivity.Companion.REFRESH_ADVERSE_EVENT_PAGE
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.MainPhysicalSignActivity.Companion.REFRESH_MAIN_PHYSICAL_SIGN_PAGE
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.TreatmentRecordActivity.Companion.REFRESH_TREATMENT_RECORD_PAGE
import com.ksballetba.rayplus.ui.adapter.ViewPagerAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.TreatmentVisitFragment.Companion.TREATMENT_CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.TreatmentVisitFragment.Companion.VISIT_TITLE
import com.ksballetba.rayplus.ui.fragment.base_fragment.*
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.AdverseEventFragment
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.MainPhysicalSignFragment
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.TherapeuticEvaluationFragment
import com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment.TreatmentRecordFragment
import kotlinx.android.synthetic.main.activity_treatment_visit_detail.*

class TreatmentVisitDetailActivity : AppCompatActivity() {

    lateinit var mViewPagerAdapter: ViewPagerAdapter
    private val mFragmentList = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_visit_detail)
        initUI()
        initFragments()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        when (intent?.action) {
            REFRESH_MAIN_PHYSICAL_SIGN_PAGE -> {
                val mpsFragment = mViewPagerAdapter.getFragmentByIdx(1) as MainPhysicalSignFragment
                mpsFragment.loadData()
            }
            REFRESH_TREATMENT_RECORD_PAGE -> {
                val trFragment = mViewPagerAdapter.getFragmentByIdx(5) as TreatmentRecordFragment
                trFragment.loadData()
            }
            REFRESH_ADVERSE_EVENT_PAGE -> {
                val aeFragment = mViewPagerAdapter.getFragmentByIdx(6) as AdverseEventFragment
                aeFragment.loadData()
            }
            REFRESH_IMAGING_EVALUATION_PAGE -> {
                val ieFragment = mViewPagerAdapter.getFragmentByIdx(2) as ImagingEvaluationFragment
                ieFragment.loadData()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        setSupportActionBar(tb_treatment_visit)
        val title = intent?.getStringExtra(VISIT_TITLE)
        supportActionBar?.title = title
    }

    private fun initFragments() {
        val sampleId = intent.getIntExtra(SAMPLE_ID, 0)
        val cycleNumber = intent.getIntExtra(TREATMENT_CYCLE_NUMBER_KEY, 0)
        val treatmentArgs = Bundle()
        treatmentArgs.putInt(CYCLE_NUMBER_KEY, cycleNumber)
        treatmentArgs.putInt(SAMPLE_ID, sampleId)
        val visitTimeFragment = VisitTimeFragment()
        val mainPhysicalSignFragment = MainPhysicalSignFragment()
        val imagingEvaluationFragment = ImagingEvaluationFragment()
        val therapeuticEvaluationFragment = TherapeuticEvaluationFragment()
        val labInspectionFragment = LabInspectionFragment()
        val treatmentRecordFragment = TreatmentRecordFragment()
        val adverseEventFragment = AdverseEventFragment()
        val investigatorSignatureFragment = InvestigatorSignatureFragment()
        val visitSubmitFragment = VisitSubmitFragment()
        visitTimeFragment.arguments = treatmentArgs
        mainPhysicalSignFragment.arguments = treatmentArgs
        imagingEvaluationFragment.arguments = treatmentArgs
        therapeuticEvaluationFragment.arguments = treatmentArgs
        labInspectionFragment.arguments = treatmentArgs
        treatmentRecordFragment.arguments = treatmentArgs
        adverseEventFragment.arguments = treatmentArgs
        investigatorSignatureFragment.arguments = treatmentArgs
        visitSubmitFragment.arguments = treatmentArgs
        mFragmentList.add(visitTimeFragment)
        mFragmentList.add(mainPhysicalSignFragment)
        mFragmentList.add(imagingEvaluationFragment)
        mFragmentList.add(therapeuticEvaluationFragment)
        mFragmentList.add(labInspectionFragment)
        mFragmentList.add(treatmentRecordFragment)
        mFragmentList.add(adverseEventFragment)
        mFragmentList.add(investigatorSignatureFragment)
        mFragmentList.add(visitSubmitFragment)
        mViewPagerAdapter = ViewPagerAdapter(mFragmentList, supportFragmentManager)
        vp_treatment_visit.adapter = mViewPagerAdapter
        vp_treatment_visit.offscreenPageLimit = 3
        tl_treatment_visit.setupWithViewPager(vp_treatment_visit)
        tl_treatment_visit.getTabAt(0)?.text = "访视时间"
        tl_treatment_visit.getTabAt(1)?.text = "主要症状体征"
        tl_treatment_visit.getTabAt(2)?.text = "影像学评估"
        tl_treatment_visit.getTabAt(3)?.text = "疗效评价"
        tl_treatment_visit.getTabAt(4)?.text = "实验室检查"
        tl_treatment_visit.getTabAt(5)?.text = "治疗记录单"
        tl_treatment_visit.getTabAt(6)?.text = "不良事件"
        tl_treatment_visit.getTabAt(7)?.text = "研究者签字"
        tl_treatment_visit.getTabAt(8)?.text = "访视提交"
    }
}
