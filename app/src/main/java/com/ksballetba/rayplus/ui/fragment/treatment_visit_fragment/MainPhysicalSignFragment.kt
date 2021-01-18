package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


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
import com.ksballetba.rayplus.data.bean.ECOGBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.MainPhysicalSignBodyBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.MainPhysicalSignListBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.MainPhysicalSignActivity
import com.ksballetba.rayplus.ui.adapter.MainPhysicalSignAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_main_physical_sign.*

/**
 * A simple [Fragment] subclass.
 */
class MainPhysicalSignFragment : Fragment() {

    companion object {
        const val TAG = "MainPhysicalSignFragment"
        const val MAIN_PHYSICAL_SIGN_BODY = "MAIN_PHYSICAL_SIGN_BODY"
    }

    private lateinit var mViewModel: TreatmentVisitViewModel
    private lateinit var mAdapter: MainPhysicalSignAdapter
    var mList = mutableListOf<MainPhysicalSignListBean.Data>()
    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_physical_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getTreatmentVisitViewModel(this)
    }

    private fun initUI() {
        fab_main_physical_sign.setOnClickListener {
            navigateToMainPhysicalSignEditPage(mSampleId, mCycleNumber, null)
        }
        cl_ECOG_score.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("ECOG评分", "请输入ECOG评分") {
                tv_ECOG_score.text = it
            }.show()
        }
        tv_save_ECOG_score.setOnClickListener {
            val ecogScore = parseDefaultContent(tv_ECOG_score.text.toString())
            val eCOG = ECOGBean.Data(ecogScore)
            mViewModel.editECOGScore(mSampleId, mCycleNumber, eCOG).observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("ECOG评分保存成功")
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_main_physical_sign.layoutManager = layoutManager
        mAdapter = MainPhysicalSignAdapter(R.layout.item_main_physical_sign, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_main_physical_sign)
        mAdapter.setOnItemClickListener { _, _, position ->
            val mainPhysicalSign = mList[position]
            val mainPhysicalSignBody =
                MainPhysicalSignBodyBean(
                    mainPhysicalSign.endTime,
                    mainPhysicalSign.existence,
                    mainPhysicalSign.isUsingMedicine,
                    mainPhysicalSign.mainSymptomId,
                    mainPhysicalSign.measure,
                    mainPhysicalSign.medicineRelation,
                    mainPhysicalSign.startTime,
                    mainPhysicalSign.symptomDescription,
                    mainPhysicalSign.symptomDescriptionOther,
                    mainPhysicalSign.toxicityClassification
                )
            navigateToMainPhysicalSignEditPage(
                mSampleId,
                mCycleNumber,
                mainPhysicalSignBody
            )
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteMainPhysicalSign(mList[position].mainSymptomId, position)
            }.show()

        }
    }

    fun loadData() {
        mViewModel.getMainPhysicalSignList(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mAdapter.setNewData(mList)
            })
        mViewModel.getECOGScore(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                tv_ECOG_score.text = it.data?.eCOG
            })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if (it.status == Status.FAILED) {
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun navigateToMainPhysicalSignEditPage(
        sampleId: Int,
        cycleNumber: Int,
        mainPhysicalSignBodyBean: MainPhysicalSignBodyBean?
    ) {
        val intent = Intent(activity, MainPhysicalSignActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(CYCLE_NUMBER_KEY, cycleNumber)
        if (mainPhysicalSignBodyBean?.mainSymptomId != null) {
            intent.putExtra(MAIN_PHYSICAL_SIGN_BODY, mainPhysicalSignBodyBean)
        }
        startActivity(intent)
    }

    private fun deleteMainPhysicalSign(mainSymptomId: Int, pos: Int) {
        mViewModel.deleteMainPhysicalSign(mSampleId, mCycleNumber, mainSymptomId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("删除成功")
                        mAdapter.remove(pos)
                    } else {
                        ToastUtils.showShort("删除失败")
                    }
                })
    }
}
